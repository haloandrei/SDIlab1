package ui;

import domain.Movie;
import domain.validators.ValidatorException;
import service.MovieService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class Console {
    private MovieService movieService;

    public Console(MovieService studentService) {
        this.movieService = studentService;
    }

    public void runConsole() {
        addMovies();
        printAllMovies();
        //filterStudents();
    }

//    private void filterStudents() {
//        System.out.println("filtered students (name containing 's2'):");
//        Set<Movie> Movies = movieService.filterMoviesByName("s2");
//        Movies.stream().forEach(System.out::println);
//    }

    private void printAllMovies() {
        Set<Movie> Movies = movieService.getAllMovies();
        Movies.stream().forEach(System.out::println);
    }

    private void addMovies() {
        int count = 2;
        while (count > 0) {
            count--;
            Movie Movie = readMovie();
            if (Movie == null || Movie.getId() < 0) {
                break;
            }
            try {
                movieService.addMovie(Movie);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private Movie readMovie() {
        System.out.println("Read Movie {id,type, name, rating}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String type = bufferRead.readLine();
            String name = bufferRead.readLine();
            int rating = Integer.parseInt(bufferRead.readLine());// ...

            Movie Movie = new Movie(type, name, rating);
            Movie.setId(id);

            return Movie;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
