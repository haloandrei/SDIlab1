package com.haloandrei.socket.client;

import com.haloandrei.socket.common.*;
import com.haloandrei.socket.common.domain.Movie;
import com.haloandrei.socket.common.domain.validators.MovieRentalException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceMovie implements ServiceMovieInterface {

    private ExecutorService executorService;

    public ServiceMovie() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }


    public Message<?> sentRequest(Message<?> request) throws MovieRentalException {
        try (var socket = new Socket(Message.HOST, Message.PORT);
             var socketInputStream = socket.getInputStream();
             var socketOutputStream = socket.getOutputStream();
             var outputStream = new ObjectOutputStream(socketOutputStream);
             var inputStream = new ObjectInputStream(socketInputStream);
        ) {
            outputStream.writeObject(request);
            Message<?> response = (Message<?>) inputStream.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            throw new MovieRentalException("error connection to server " + e.getMessage(), e);
        }
    }


    @Override
    public void addMovie(Movie movie) throws MovieRentalException {

        Message<Movie> request = new Message<Movie>("addMovie", movie);
        CompletableFuture.supplyAsync(() -> {
                    Message<Movie> response = (Message<Movie>) this.sentRequest(request);
                    if (response.getHeader().equals("error"))
                        throw new MovieRentalException("error" + response.getError());
                    return null;
                }
        );
    }

    @Override
    public CompletableFuture<ArrayList<Movie>> getAllMovies() {
        System.out.println("aici");
        Message<ArrayList<Movie>> request = new Message<ArrayList<Movie>>("getAllMovies", null);
        return CompletableFuture.supplyAsync(() ->
                {
                    Message<ArrayList<Movie>> response = (Message<ArrayList<Movie>>) this.sentRequest(request);
                    if (response.getHeader().equals("error"))
                    {
                        throw new MovieRentalException("error" + response.getError());
                    }
                    return response.getBody();
                }
        );
    }
}
