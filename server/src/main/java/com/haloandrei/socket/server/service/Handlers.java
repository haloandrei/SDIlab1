package com.haloandrei.socket.server.service;



import com.haloandrei.socket.common.Message;
import com.haloandrei.socket.common.ServiceMovieInterface;
import com.haloandrei.socket.common.domain.Movie;

import java.util.*;
import java.util.function.UnaryOperator;

public class Handlers {
    private Map<String, UnaryOperator<Message>> methodHandlers;
    private ServiceMovieInterface serviceMovie;


    public Handlers(ServiceMovieInterface serviceMovie) {
        this.methodHandlers = new HashMap<>();
        this.serviceMovie = serviceMovie;
        addHandlers();
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public Map<String, UnaryOperator<Message>> getMethodHandlers() {
        return methodHandlers;
    }

    public void setMethodHandlers(Map<String, UnaryOperator<Message>> methodHandlers) {
        this.methodHandlers = methodHandlers;
    }

    public void addHandlers() {
        addHandler("addMovie", this::addMovie);
        addHandler("deleteMovie", this::deleteMovie);
        addHandler("updateMovie", this::updateMovie);
        addHandler("getSortedMovies", this::sortMovies);

    }

    public Message<Movie> addMovie(Message<Movie> message) {
        Message<Movie> response = new Message<>("addMovie", null);
        try {
            serviceMovie.addMovie(message.getBody());
        } catch (Exception e) {
            response.setHeader("error");
            response.setError(e.toString());
        }
        return response;
    }

    public Message<Movie> updateMovie(Message<Movie> message) {
        Message<Movie> response = new Message<>("updateMovie", null);
        try {
            serviceMovie.updateMovie(message.getBody());
        } catch (Exception e) {
            response.setHeader("error");
            response.setError(e.toString());
        }
        return response;
    }

    public Message<Long> deleteMovie(Message<Long> message) {
        Message<Long> response = new Message<>("deleteMovie", null);
        try {
            serviceMovie.deleteMovie(message.getBody());
        } catch (Exception e) {
            response.setHeader("error");
            response.setError(e.toString());
        }
        return response;
    }

    public Message<ArrayList<Movie>> sortMovies(Message<ArrayList<Movie>> message) {
        Message<ArrayList<Movie>> response = new Message<>("sortMovies", null);
        try {
            ArrayList<Movie> movies = serviceMovie.getSortedMovies().get();
            response.setBody(movies);
        } catch (Exception e) {
            response.setHeader("error");
            response.setError(e.toString());
        }
        return response;
    }

}
