package com.practicemovieapi.auth.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginRequest {

    private String email;

    private String password;
}
