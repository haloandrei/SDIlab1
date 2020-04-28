package com.haloandrei.socket.server.tcp;


import com.haloandrei.socket.common.HelloServiceException;
import com.haloandrei.socket.common.Message;
import com.haloandrei.socket.common.ServiceMovieInterface;
import com.haloandrei.socket.server.service.Handlers;
import com.haloandrei.socket.server.service.ServiceMovieImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

/**
 * Created by radu.
 */
public class TcpServer {
    private ExecutorService executorService;
    private Map<String, UnaryOperator<Message>> methodHandlers;
    private ServiceMovieInterface serviceMovie;
    private Handlers handle;

    public TcpServer(ServiceMovieInterface serviceMovie) {
        this.serviceMovie = serviceMovie;
        this.methodHandlers = new HashMap<>();
        this.handle = new Handlers(serviceMovie);
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() throws Exception {

        try (var serverSocket = new ServerSocket(Message.PORT)) {
            IntStream.iterate(1, i -> i + 1).forEach(val -> {
                Socket client = null;
                try {
                    client = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                executorService.submit(new ClientHandler(client));
            });
        } catch (IOException e) {
            throw new Exception("error connecting clients", e);
        }
        executorService.shutdown();
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket client) {
            this.socket = client;
        }

        @Override
        public void run() {
            CompletableFuture.supplyAsync(()->{
                try (var iss = socket.getInputStream();
                     var oss = socket.getOutputStream();
                     var os = new ObjectOutputStream(oss);
                     var is = new ObjectInputStream(iss);) {
                    Message request = (Message) is.readObject();

                    Message response = handle.getMethodHandlers().get(request.getHeader()).apply(request);

                    os.writeObject(response);

                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("error processing client" + e.getMessage());
                } return null;});
        }
    }
}
