package com.practicemovieapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@Getter
public class MovieDto implements Serializable {

    public MovieDto(){

    }

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("movieId")
    private Integer movieId;

    @JsonProperty("title")
    @NotBlank(message = "title cannot be blank")
    private String title;

    @JsonProperty("studio")
    @NotBlank(message = "studio cannot be blank")
    private String studio;

    @JsonProperty("director")
    @NotBlank(message = "director cannot be blank")
    private String director;

    @JsonProperty("movieCast")
    private Set<String> movieCast;

    @JsonProperty("releaseYear")
    @NotNull(message = "release year cannot be blank")
    private Integer releaseYear;


    @JsonProperty("image")
    @NotBlank(message = "image cannot be blank")
    private String image;

    @JsonProperty("imageUrl")
    @NotNull(message = "image url cannot be empty")
    private String imageurl;



    public void setImage(String image) {
        this.image = image;
    }
}
