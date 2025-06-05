package io.github.danielreker.onlinelearningserver.controller;

import io.github.danielreker.onlinelearningserver.model.User;
import io.github.danielreker.onlinelearningserver.model.dto.auth.LoginRequestDto;
import io.github.danielreker.onlinelearningserver.model.dto.auth.RegistrationRequestDto;
import io.github.danielreker.onlinelearningserver.model.dto.auth.TokenRefreshRequestDto;
import io.github.danielreker.onlinelearningserver.model.dto.auth.TokenResponseDto;
import io.github.danielreker.onlinelearningserver.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequestDto registrationRequest) {
        authService.registerUser(registrationRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        TokenResponseDto tokenResponse = authService.loginUser(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refreshToken(@Valid @RequestBody TokenRefreshRequestDto refreshRequest) {
        TokenResponseDto tokenResponse = authService.refreshToken(refreshRequest.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody TokenRefreshRequestDto refreshRequestDto) {
        authService.invalidateUserRefreshToken(refreshRequestDto.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout-everywhere")
    public ResponseEntity<?> logoutUserEverywhere(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        authService.logoutUser(userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}