package io.github.danielreker.onlinelearningserver.model.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String role;
    private long expiresIn;
}