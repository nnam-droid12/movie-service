package com.practicemovieapi.auth.utils;

import lombok.Builder;

@Builder
public record ChangePassword(String password, String repeatedPassword) {

}
