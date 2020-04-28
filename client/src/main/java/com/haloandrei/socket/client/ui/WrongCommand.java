package com.haloandrei.socket.client.ui;


import com.haloandrei.socket.common.ServiceMovieInterface;

import java.util.Scanner;

public class WrongCommand implements Command {

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie) {
        System.out.println("Wrong command");
    }
}