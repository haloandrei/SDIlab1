package com.haloandrei.socket.common;


import com.haloandrei.socket.common.domain.Movie;
import com.haloandrei.socket.common.domain.validators.MovieRentalException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public interface ServiceMovieInterface {

    CompletableFuture<ArrayList<Movie>> getSortedMovies();

    void addMovie(Movie movie) throws MovieRentalException;

    void deleteMovie(Long id) throws MovieRentalException;

    void updateMovie(Movie movie) throws MovieRentalException;

    CompletableFuture<ArrayList<Movie>> getAllMovies();

}
