package com.practicemovieapi.service;

import com.practicemovieapi.Exception.FileExistException;
import com.practicemovieapi.Exception.MovieNotFoundException;
import com.practicemovieapi.dto.MovieDto;
import com.practicemovieapi.dto.MoviePaginationResponse;
import com.practicemovieapi.entities.Movie;
import com.practicemovieapi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final FileService fileService;


    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Value("${base.url}")
    private String baseUrl;

    @Value("${project.image}")
    private String path;

    @Override
    public MovieDto addMovie(MovieDto moviedto, MultipartFile file) throws IOException {
        // check if file is selected
        if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
            throw new FileExistException("file already exists, please seect another file");
        }
        // get the file name
        String uploadedFileName = fileService.uploadFile(path, file);

        // set the field image as file name
        moviedto.setImage(uploadedFileName);

        // map the movie object with the dto object
        Movie movie = new Movie(
          null,
          moviedto.getTitle(),
          moviedto.getStudio(),
          moviedto.getDirector(),
          moviedto.getMovieCast(),
          moviedto.getReleaseYear(),
          moviedto.getImage()
        );

        Movie savedMovie = movieRepository.save(movie);

        String imageUrl = baseUrl + "/file/" + uploadedFileName;

        // map dto object to movie object
        MovieDto response = new MovieDto(
                null,
                savedMovie.getTitle(),
                savedMovie.getStudio(),
                savedMovie.getDirector(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getImage(),
                imageUrl
        );
        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {

        Movie movies = movieRepository.findById(movieId).
                orElseThrow(() -> new MovieNotFoundException("movie not found"));
         String imageUrl = baseUrl + File.separator + movies.getImage();

        MovieDto response = new MovieDto(
                movies.getMovieId(),
                movies.getTitle(),
                movies.getStudio(),
                movies.getDirector(),
                movies.getMovieCast(),
                movies.getReleaseYear(),
                movies.getImage(),
                imageUrl
        );
        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {

        List<Movie> movies = movieRepository.findAll();

        List<MovieDto> allMovies = new ArrayList<>();

        for(Movie movie: movies ){
            String imageUrl = baseUrl + File.separator + movie.getImage();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getStudio(),
                    movie.getDirector(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getImage(),
                    imageUrl
            );

            allMovies.add(movieDto);
        }


        return allMovies;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto moviedto, MultipartFile file) throws IOException {
        Movie mv = movieRepository.findById(movieId).
                orElseThrow(() -> new MovieNotFoundException("movie not found"));

        // if file is nulldo nothing
        // if file is not null
        String fileName = mv.getImage();
        if(file != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }

        moviedto.setImage(fileName);

        Movie movie = new Movie(
                mv.getMovieId(),
                moviedto.getTitle(),
                moviedto.getStudio(),
                moviedto.getDirector(),
                moviedto.getMovieCast(),
                moviedto.getReleaseYear(),
                moviedto.getImage()
        );

        Movie updatedMovie = movieRepository.save(movie);

        String imageUrl = baseUrl + File.separator + fileName;

        MovieDto response = new MovieDto(
              movie.getMovieId(),
              movie.getTitle(),
              movie.getStudio(),
              movie.getDirector(),
              movie.getMovieCast(),
              movie.getReleaseYear(),
              movie.getImage(),
              imageUrl
        );

        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {

        Movie mv = movieRepository.findById(movieId).
                orElseThrow(() -> new MovieNotFoundException("movie not found"));
        Integer id = mv.getMovieId();

        Files.deleteIfExists(Paths.get(path + File.separator + mv.getImage()));
        movieRepository.delete(mv);

        return "Movie deleted with Id" + id;

    }

    @Override
    public MoviePaginationResponse getAllMoviespagination(Integer pageNum, Integer pageSize) {
        Pageable page = PageRequest.of(pageNum, pageSize);
        Page<Movie> moviePages = movieRepository.findAll(page);

        List<Movie> allMovies = moviePages.getContent();

        List<MovieDto> newAllMovies = new ArrayList<>();

        for(Movie movie: allMovies){
            String imageUrl = baseUrl + File.separator + movie.getImage();
            MovieDto movieDto = new MovieDto(
              movie.getMovieId(),
              movie.getTitle(),
              movie.getStudio(),
              movie.getDirector(),
              movie.getMovieCast(),
              movie.getReleaseYear(),
              movie.getImage(),
               imageUrl
            );
            newAllMovies.add(movieDto);
        }

        return new MoviePaginationResponse(newAllMovies, pageNum, pageSize,
                                           moviePages.getTotalElements(),
                                           moviePages.getTotalPages(),
                                           moviePages.isLast());
    }

    @Override
    public MoviePaginationResponse getAllMoviespaginationSorting(Integer pageNum, Integer pageSize,
                                                                 String sortBy, String dir) {

        Sort sort = dir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
                                                           : Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNum, pageSize, sort);
        Page<Movie> moviePages = movieRepository.findAll(page);

        List<Movie> allMovies = moviePages.getContent();

        List<MovieDto> newAllMovies = new ArrayList<>();

        for(Movie movie: allMovies){
            String imageUrl = baseUrl + File.separator + movie.getImage();
            MovieDto movieDto = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getStudio(),
                    movie.getDirector(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getImage(),
                    imageUrl
            );
            newAllMovies.add(movieDto);
        }

        return new MoviePaginationResponse(newAllMovies, pageNum, pageSize,
                moviePages.getTotalElements(),
                moviePages.getTotalPages(),
                moviePages.isLast());
    }
}
