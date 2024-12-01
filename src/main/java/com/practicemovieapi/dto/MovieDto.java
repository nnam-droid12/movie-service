package com.practicemovieapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieDto {

    private Integer movieId;

    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotBlank(message = "studio cannot be blank")
    private String studio;

    @NotBlank(message = "director cannot be blank")
    private String director;

    private Set<String> movieCast;

    @NotNull(message = "release year cannot be blank")
    private Integer releaseYear;


    @NotBlank(message = "image cannot be blank")
    private String image;

    @NotNull(message = "image url cannot be empty")
    private String imageurl;



    public void setImage(String image) {
        this.image = image;
    }
}
