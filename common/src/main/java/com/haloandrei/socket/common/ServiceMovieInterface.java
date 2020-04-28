package com.haloandrei.socket.common;


import com.haloandrei.socket.common.domain.Movie;
import com.haloandrei.socket.common.domain.validators.MovieRentalException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public interface ServiceMovieInterface {

    void addMovie(Movie movie) throws MovieRentalException;

    CompletableFuture<ArrayList<Movie>> getAllMovies();

}
