package com.haloandrei.socket.client.ui;

import com.haloandrei.socket.common.ServiceMovieInterface;

import java.util.Scanner;

public interface Command {
    void execute(Scanner scanner, ServiceMovieInterface serviceMovie);
}
