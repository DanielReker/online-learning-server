package io.github.danielreker.onlinelearningserver.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRefreshRequestDto {
    @NotBlank(message = "Refresh token cannot be blank")
    private String refreshToken;
}