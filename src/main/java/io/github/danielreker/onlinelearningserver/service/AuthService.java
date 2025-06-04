package io.github.danielreker.onlinelearningserver.service;

import io.github.danielreker.onlinelearningserver.exception.AppException;
import io.github.danielreker.onlinelearningserver.exception.TokenRefreshException;
import io.github.danielreker.onlinelearningserver.mapper.UserMapper;
import io.github.danielreker.onlinelearningserver.model.RefreshToken;
import io.github.danielreker.onlinelearningserver.model.User;
import io.github.danielreker.onlinelearningserver.model.dto.auth.LoginRequestDto;
import io.github.danielreker.onlinelearningserver.model.dto.auth.RegistrationRequestDto;
import io.github.danielreker.onlinelearningserver.model.dto.auth.TokenResponseDto;
import io.github.danielreker.onlinelearningserver.model.enums.Role;
import io.github.danielreker.onlinelearningserver.repository.RefreshTokenRepository;
import io.github.danielreker.onlinelearningserver.repository.UserRepository;
import io.github.danielreker.onlinelearningserver.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Value("${application.security.jwt.refresh-token-expiration-ms}")
    private long refreshTokenDurationMs;


    @Transactional
    public void registerUser(RegistrationRequestDto registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new AppException("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new AppException("Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.toUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);
    }

    @Transactional
    public TokenResponseDto loginUser(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        User userDetails = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = createRefreshToken(userDetails.getId());

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .username(userDetails.getUsername())
                .role(userDetails.getRole().name())
                .expiresIn(jwtService.extractClaim(accessToken, claims -> claims.getExpiration().getTime()))
                .build();
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found with id: " + userId, HttpStatus.NOT_FOUND));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .revoked(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public TokenResponseDto refreshToken(String requestRefreshToken) {
        RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token not found"));

        if (oldRefreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new TokenRefreshException(requestRefreshToken, "Refresh token is expired!!");
        }

        User user = oldRefreshToken.getUser();
        refreshTokenRepository.delete(oldRefreshToken);

        RefreshToken newRefreshToken = this.createRefreshToken(user.getId());
        String newAccessToken = jwtService.generateAccessToken(user);

        return TokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .username(user.getUsername())
                .role(user.getRole().name())
                .expiresIn(jwtService.extractClaim(newAccessToken, claims -> claims.getExpiration().getTime()))
                .build();
    }

    @Transactional
    public void invalidateUserRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresentOrElse(refreshTokenRepository::delete, () -> {
                    throw new AppException("Token not found: " + token, HttpStatus.NOT_FOUND);
                });
    }

    @Transactional
    public void logoutUser(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}