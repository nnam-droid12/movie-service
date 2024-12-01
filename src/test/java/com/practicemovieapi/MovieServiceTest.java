package com.practicemovieapi.service;

import com.practicemovieapi.dto.MovieDto;
import com.practicemovieapi.entities.Movie;
import com.practicemovieapi.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    private MovieRepository movieRepository;
    private FileService fileService;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository = mock(MovieRepository.class);
        fileService = mock(FileService.class);
        movieService = new MovieServiceImpl(movieRepository, fileService);
    }

    @Test
    void testAddMovie() throws Exception {

        MultipartFile mockFile = mock(MultipartFile.class);
        MovieDto movieDto = new MovieDto(null, "Inception", "Warner Bros", "Nolan", null, null, null, null);


        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie(1, "Inception", "Warner Bros", "Nolan", null, null, "test.jpg"));


        MovieDto response = movieService.addMovie(movieDto, mockFile);


        assertEquals("Inception", response.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testGetMovie() {
        Movie mockMovie = new Movie(1, "Inception", "Warner Bros", "Nolan", null, null, "test.jpg");
        when(movieRepository.findById(eq(1))).thenReturn(Optional.of(mockMovie));


        MovieDto response = movieService.getMovie(1);


        assertEquals("Inception", response.getTitle());
    }
}
