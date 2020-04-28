package com.haloandrei.socket.server.service;

import com.haloandrei.socket.common.HelloService;
import com.haloandrei.socket.common.domain.Client;
import com.haloandrei.socket.common.domain.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public class HelloServiceImpl implements HelloService {
    private ExecutorService executorService;

    public HelloServiceImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<String> sayHello(String name) {
        return executorService.submit(() -> "Hello " + name);
    }


//    @Override
//    public Future<String> sayBye(String name) {
//        return executorService.submit(() -> "Bye " + name);
//    }
}
