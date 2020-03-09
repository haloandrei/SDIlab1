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
        //boolean running = true;
        //while(running){
        //    addMovies();
            printAllMovies();
    }

    private void printAllMovies() {
        Set<Movie> Movies = movieService.getAllMovies();
        Movies.stream().forEach(System.out::println);
    }

    private void addMovies() {
        int count = 1;
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
        System.out.println("Read Movie {id,serialNumber, name, group}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String serialNumber = bufferRead.readLine();
            String name = bufferRead.readLine();
            int group = Integer.parseInt(bufferRead.readLine());// ...

            Movie Movie = new Movie(serialNumber, name, group);
            Movie.setId(id);

            return Movie;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
