package com.haloandrei.socket.client.ui;


import com.haloandrei.socket.common.ServiceMovieInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class Console {
    private ServiceMovieInterface serviceMovie;
    private Scanner scanner;
    private Map<Integer, Command> commands;

    public Console(ServiceMovieInterface serviceMovie) {
        this.serviceMovie = serviceMovie;
        this.scanner = new Scanner(System.in);
        addCommands();
    }

    public void runConsole() {
        printMenu();
        IntStream.iterate(1, i->i+1).forEach(val -> {
            int command = scanner.nextInt();
            commands.getOrDefault(command, new WrongCommand()).execute(scanner, serviceMovie);
            printMenu();
        });
    }

    public void printMenu() {
        System.out.println("0 - Exit");
        System.out.println("1 - Add movie");
        System.out.println("2 - List movies");
    }

    public void addCommands() {
        commands = new HashMap<Integer, Command>();
        commands.put(0, new ExitCommand());
        commands.put(1, new AddMovieCommand());
        commands.put(2, new ListMoviesCommand());
    }
}
