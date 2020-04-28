package com.haloandrei.socket.client.ui;


import com.haloandrei.socket.common.ServiceMovieInterface;

import java.util.Scanner;

public class ExitCommand implements Command {
    public ExitCommand() {
    }

    @Override
    public void execute(Scanner scanner, ServiceMovieInterface serviceMovie) {
        System.exit(0);
    }
}