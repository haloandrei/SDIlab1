package com.haloandrei.socket.server;


import com.haloandrei.socket.common.HelloService;
import com.haloandrei.socket.common.Message;
import com.haloandrei.socket.common.domain.Movie;
import com.haloandrei.socket.server.service.HelloServiceImpl;
import com.haloandrei.socket.server.tcp.TcpServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ServerApp {

    public static void main(String[] args) {
        try {


            System.out.println("server started");
            ExecutorService executorService = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors()
            );
            HelloService helloService = new HelloServiceImpl(executorService);

            TcpServer tcpServer = new TcpServer(executorService);

            tcpServer.addHandler(HelloService.SAY_HELLO, (request) -> {
                String name = request.getBody();
                Future<String> future = helloService.sayHello(name);
                try {
                    String result = future.get();
                    return new Message("ok", result); //fixme: hardcoded str
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());//fixme: hardcoded str
                }

            });


            tcpServer.startServer();

            executorService.shutdown();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

    }
}
