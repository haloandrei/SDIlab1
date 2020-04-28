package com.haloandrei.socket.client.ui;

import com.haloandrei.socket.common.ServiceMovieInterface;
import com.haloandrei.socket.common.domain.Movie;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ListMoviesCommand implements Command {

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie) {
        serviceMovie.getAllMovies().thenAccept(r -> r.forEach(System.out::println));
    }
}