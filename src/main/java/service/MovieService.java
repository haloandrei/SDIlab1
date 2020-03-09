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

    public Set<Movie> findFilteredRatingMovies(int rating){
        return StreamSupport.stream(repository.findAll().spliterator(), false).filter(x->x.getRating()>rating).collect(Collectors.toSet());
    }
    public Set<Movie> getAllMovies(){
        Iterable<Movie> movies = repository.findAll();
        return StreamSupport.stream(movies.spliterator(),false).collect(Collectors.toSet());
    }
}
