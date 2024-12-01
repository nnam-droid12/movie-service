package com.practicemovieapi.auth.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RegisterRequest {

    private String name;

    private String username;

    private String email;

    private String password;
}
