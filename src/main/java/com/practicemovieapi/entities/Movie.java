package com.practicemovieapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;


    @Column(nullable = false)
    @NotBlank(message = "title cannot be blank")
    private String title;


    @Column(nullable = false)
    @NotBlank(message = "studio cannot be blank")
    private String studio;


    @Column(nullable = false)
    @NotBlank(message = "director cannot be blank")
    private String director;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;


    @Column(nullable = false)
    @NotNull(message = "release year cannot be blank")
    private Integer releaseYear;



    @Column(nullable = false)
    @NotBlank(message = "image cannot be blank")
    private String image;
}
