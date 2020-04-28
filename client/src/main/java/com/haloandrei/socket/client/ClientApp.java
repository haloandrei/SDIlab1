package com.haloandrei.socket.client;


import com.haloandrei.socket.client.tcp.TcpClient;
import com.haloandrei.socket.client.ui.Console;
import com.haloandrei.socket.client.service.HelloServiceClient;
import com.haloandrei.socket.common.HelloService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by radu.
 */
public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient();
        HelloService helloService = new HelloServiceClient(executorService, tcpClient);
        Console console = new Console(helloService);
        console.runConsole();

        executorService.shutdown();

        System.out.println("bye client");
    }
}
