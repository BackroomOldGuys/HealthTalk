package com.brold.healthTalk.user.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfoDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
