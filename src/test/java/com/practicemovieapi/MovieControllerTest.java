package com.practicemovieapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practicemovieapi.controller.MovieController;
import com.practicemovieapi.dto.MovieDto;
import com.practicemovieapi.dto.MoviePaginationResponse;
import com.practicemovieapi.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MovieControllerTest {

    private MovieService movieService;
    private MovieController movieController;

    @BeforeEach
    void setUp() {
        movieService = mock(MovieService.class); // Mock the service
        movieController = new MovieController(movieService); // Inject mock service
    }

    @Test
    void testAddMovieHandler() throws Exception {

        String movieJson = "{\"title\":\"Inception\",\"studio\":\"Warner Bros\",\"director\":\"Nolan\"}";
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, "image data".getBytes());

        MovieDto mockMovieDto = new MovieDto(null, "Inception", "Warner Bros", "Nolan", null, null, "test.png", null);
        when(movieService.addMovie(any(MovieDto.class), any(MockMultipartFile.class))).thenReturn(mockMovieDto);


        ResponseEntity<MovieDto> response = movieController.addMovieHandler(movieJson, mockFile);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Inception", response.getBody().getTitle());
    }

    @Test
    void testGetAllMovieHandler() {

        List<MovieDto> mockMovies = Arrays.asList(
                new MovieDto(1, "Inception", "Warner Bros", "Nolan", null, null, null, null),
                new MovieDto(2, "Interstellar", "Paramount", "Nolan", null, null, null, null)
        );
        when(movieService.getAllMovies()).thenReturn(mockMovies);


        ResponseEntity<List<MovieDto>> response = movieController.getAllMovieHandler();


        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetMovieHandler() {

        MovieDto mockMovie = new MovieDto(1, "Inception", "Warner Bros", "Nolan", null, null, null, null);
        when(movieService.getMovie(eq(1))).thenReturn(mockMovie);


        ResponseEntity<MovieDto> response = movieController.getMovieHandler(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Inception", response.getBody().getTitle());
    }
}

