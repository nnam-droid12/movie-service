package com.practicemovieapi.controller;

import com.practicemovieapi.dto.MovieDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNewMovieNotification(MovieDto movieDto) {
        String message = "A new movie was added: " + movieDto.getTitle();
        messagingTemplate.convertAndSend("/topic/newMovie", message);
    }
}

