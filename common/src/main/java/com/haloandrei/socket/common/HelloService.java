package com.haloandrei.socket.common;

import com.haloandrei.socket.common.domain.Client;
import com.haloandrei.socket.common.domain.Movie;

import java.util.concurrent.Future;


/**
 * Created by radu.
 */
public interface HelloService {
    String SAY_HELLO = "sayHello";
    String SAY_BYE = "sayBye";
    String addClient = "addClient";
    String addMovie = "addMovie";

    Future<String> sayHello(String name);

//    Future<String> sayBye(String name);


}