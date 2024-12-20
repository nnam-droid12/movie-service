package com.practicemovieapi.auth.utils;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {

}
