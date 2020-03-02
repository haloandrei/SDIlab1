package service;

import domain.Movie;
import repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService {
    private Repository<Long, Movie> repository;

    public MovieService(Repository repoInit){
        repository = repoInit;
    }

    public void addMovie(Movie newMovie)
    {
        repository.save(newMovie);
    }

    public Set<Movie> getAllMovies(){
        Iterable<Movie> movies = repository.findAll();
        return StreamSupport.stream(movies.spliterator(),false).collect(Collectors.toSet());
    }
}
