package com.practicemovieapi.repository;

import com.practicemovieapi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {


}
