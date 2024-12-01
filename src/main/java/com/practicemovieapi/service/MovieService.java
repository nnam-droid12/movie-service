package com.practicemovieapi.service;

import com.practicemovieapi.dto.MovieDto;
import com.practicemovieapi.dto.MoviePaginationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDto addMovie(MovieDto moviedto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movieId);

    List<MovieDto> getAllMovies();

    MovieDto updateMovie(Integer movieId, MovieDto moviedto, MultipartFile file) throws IOException;

    String deleteMovie(Integer movieId) throws IOException;

    MoviePaginationResponse getAllMoviespagination(Integer pageNum, Integer pageSize);

    MoviePaginationResponse getAllMoviespaginationSorting(Integer pageNum, Integer pageSize,
                                                          String sortBy, String dir);
}
