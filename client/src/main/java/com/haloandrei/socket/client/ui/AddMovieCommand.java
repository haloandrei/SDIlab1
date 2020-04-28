package com.haloandrei.socket.client.ui;

import com.haloandrei.socket.common.domain.validators.MovieRentalException;
import com.haloandrei.socket.common.ServiceMovieInterface;
import com.haloandrei.socket.common.domain.Movie;

import java.util.Scanner;

public class AddMovieCommand implements Command {
    public AddMovieCommand() {
    }

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie) {
        System.out.println("Id: ");
        Long id = scanner.nextLong();
        System.out.println("Type: ");
        String type = scanner.next();
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("Rating: ");
        int rating = scanner.nextInt();
        System.out.println("Price: ");
        int price = scanner.nextInt();
        try {
            Movie movie= new Movie(type,name,rating,price);
            movie.setId(id);
            serviceMovie.addMovie(movie);
        } catch (MovieRentalException e) {
            System.out.println(e.toString());
        }
    }
}
