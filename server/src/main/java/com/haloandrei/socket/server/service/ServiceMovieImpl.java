package com.haloandrei.socket.server.service;

import com.haloandrei.socket.common.domain.Acquisition;
import com.haloandrei.socket.common.domain.Pair;
import com.haloandrei.socket.common.domain.validators.ValidatorException;
import com.haloandrei.socket.server.repository.Repository;
import com.haloandrei.socket.common.ServiceMovieInterface;
import com.haloandrei.socket.common.domain.Movie;
import com.haloandrei.socket.common.domain.validators.MovieRentalException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceMovieImpl implements ServiceMovieInterface {
    private Repository<Long, Movie> repositoryMovie;
    private Repository<Pair<Long,Long>, Acquisition> repositoryAcquisition;


    public ServiceMovieImpl(Repository repositoryMovie) {
        this.repositoryMovie = repositoryMovie;}



    public CompletableFuture<ArrayList<Movie>> getSortedMovies() {
        return null;
    }

    public void addMovie(Movie movie) throws MovieRentalException {
        CompletableFuture.supplyAsync(() -> {
            Movie result = repositoryMovie.save(movie).orElse(movie);
            Optional<Boolean> movieValidator = Optional.of(result.equals(movie));
            movieValidator.filter(v -> v).orElseThrow(() -> new MovieRentalException("id is used"));
            return null;
        });
    }


    public void deleteMovie(Long id) throws MovieRentalException {
        CompletableFuture.supplyAsync(() -> {
            try {

                this.repositoryMovie.delete(id).orElseThrow(() -> new ValidatorException("id not valid"));

            } catch (Exception e) {
                throw new MovieRentalException(e.getMessage(), e);
            }
            return null;
        });
    }

    public void updateMovie(Movie movie) throws MovieRentalException {
        CompletableFuture.supplyAsync(() -> {
            AtomicReference<Optional<Movie>> p = new AtomicReference<>(Optional.empty());
            repositoryMovie.update(movie).ifPresentOrElse((v) -> {
            }, () -> {
                p.set(Optional.of(movie));
            });
            p.get().orElseThrow(() -> new MovieRentalException("id not existing"));
            return null;
        });
    }

    public CompletableFuture<ArrayList<Movie>> getAllMovies() {
        return CompletableFuture.supplyAsync(() -> {

            Iterable<Movie> movies = repositoryMovie.findAll();
            return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toCollection(ArrayList::new));
        });
    }
}
