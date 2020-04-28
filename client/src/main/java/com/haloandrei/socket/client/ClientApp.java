package com.haloandrei.socket.client;


import com.haloandrei.socket.common.ServiceMovieInterface;
import com.haloandrei.socket.client.ui.Console;

public class ClientApp {

    public static void main(String[] args) {
        System.out.println("Client");

        ServiceMovieInterface serviceMovie = new ServiceMovie();
        Console ui = new Console(serviceMovie);
        ui.runConsole();
    }
}
