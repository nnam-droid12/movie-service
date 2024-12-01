package com.practicemovieapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practicemovieapi.Exception.FileIsEmptyException;
import com.practicemovieapi.dto.MovieDto;
import com.practicemovieapi.dto.MoviePaginationResponse;
import com.practicemovieapi.service.MovieService;
import com.practicemovieapi.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart String moviedto,
                                                    @RequestPart MultipartFile file) throws IOException {
       if(file.isEmpty()){
           throw new FileIsEmptyException("please select file");
       }
        MovieDto dto = convertDtoObjectToJson(moviedto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    private MovieDto convertDtoObjectToJson(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }

    @GetMapping("/get-movie/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping("/get-all-movie")
    public ResponseEntity<List<MovieDto>> getAllMovieHandler(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/update-movie/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId,
                                                       @RequestPart String moviedto,
                                                       @RequestPart MultipartFile file) throws IOException {

        if(file.isEmpty()){
            throw new FileIsEmptyException("file not selected");
        }
        MovieDto dto = convertDtoObjectToJson(moviedto);
        return ResponseEntity.ok(movieService.updateMovie(movieId, dto, file));
    }

    @DeleteMapping("/delete-movie/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    @GetMapping("/getAllMoviesWithPagination")
        public ResponseEntity<MoviePaginationResponse> getAllMoviesWithPaginationHandler(
          @RequestParam(defaultValue = AppConstant.PAGE_NUM, required = false) Integer pageNum,
          @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize){

        return ResponseEntity.ok(movieService.getAllMoviespagination(pageNum,pageSize));
    }


    @GetMapping("/getAllMoviesWithPaginationSort")
    public ResponseEntity<MoviePaginationResponse> getAllMoviesWithPaginationSortingHandler(
            @RequestParam(defaultValue = AppConstant.PAGE_NUM, required = false) Integer pageNum,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstant.DIR, required = false) String dir){

        return ResponseEntity.ok(movieService.getAllMoviespaginationSorting(pageNum,pageSize,
                                                                            sortBy, dir));
    }
}
