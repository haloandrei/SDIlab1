package com.haloandrei.socket.server;



import com.haloandrei.service.AcquisitionService;
import com.haloandrei.service.ClientService;
import com.haloandrei.socket.common.HelloService;
import com.haloandrei.socket.common.Message;
import com.haloandrei.socket.common.domain.Acquisition;
import com.haloandrei.socket.common.domain.Client;
import com.haloandrei.socket.common.domain.Movie;
import com.haloandrei.socket.common.domain.Pair;
import com.haloandrei.socket.common.domain.validators.AcquisitionValidator;
import com.haloandrei.socket.common.domain.validators.ClientValidator;
import com.haloandrei.socket.common.domain.validators.MovieValidator;
import com.haloandrei.socket.common.domain.validators.Validator;
import com.haloandrei.socket.server.repository.AcquisitionFileRepository;
import com.haloandrei.socket.server.repository.ClientFileRepository;
import com.haloandrei.socket.server.repository.MovieFileRepository;
import com.haloandrei.socket.server.repository.Repository;
import com.haloandrei.socket.server.service.HelloServiceImpl;
import com.haloandrei.socket.server.service.ServiceMovieImpl;
import com.haloandrei.socket.server.tcp.TcpServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ServerApp {

    public static void main(String[] args) {
        try {
            Validator<Client> clientValidator = new ClientValidator();
            Validator<Movie> movieValidator = new MovieValidator();
            Validator<Acquisition> acquisitionValidator = new AcquisitionValidator();
            Repository<Long, Movie> movieRepository = new MovieFileRepository(movieValidator, "Movies.txt");
            Repository<Long, Client> clientRepository = new ClientFileRepository(clientValidator, "Clients.txt");
            Repository<Pair<Long, Long>, Acquisition> AcquisitionRepository = new AcquisitionFileRepository(acquisitionValidator, "Acquisitions.txt");
            ServiceMovieImpl movieService = new ServiceMovieImpl(movieRepository);
            //ClientService clientService= new ClientService(clientRepository);
            //AcquisitionService acquisitionService= new AcquisitionService(AcquisitionRepository);
            final String URL = "jdbc:postgresql://localhost:5432/catalog";
            final String USER = "postgres";
            final String PASSWORD = "password";
            System.out.println("server started");
            TcpServer tcpServer = new TcpServer(movieService);

//            tcpServer.addHandler(HelloService.SAY_HELLO, (request) -> {
//                String name = request.getBody();
//                Future<String> future = helloService.sayHello(name);
//                try {
//                    String result = future.get();
//                    return new Message("ok", result); //fixme: hardcoded str
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                    return new Message("error", e.getMessage());//fixme: hardcoded str
//                }
//
//            });


            tcpServer.startServer();

        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
