package com.practicemovieapi.auth.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AuthResponse {

    private String accessToken;

    private String refreshToken;
}
