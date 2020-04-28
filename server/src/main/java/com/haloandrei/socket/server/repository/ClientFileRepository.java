package com.haloandrei.socket.server.repository;



import com.haloandrei.socket.common.domain.Client;
import com.haloandrei.socket.common.domain.validators.Validator;
import com.haloandrei.socket.common.domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClientFileRepository extends InMemoryRepository<Long, Client> {
    private String fileName;

    public ClientFileRepository(Validator<Client> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String name = items.get(1);
                String rented_movies_string = items.get((2));
                List<String> rented_movies = Arrays.asList(rented_movies_string.split(";"));
                int moneySpent = Integer.parseInt(items.get(3));
                Client Client = new Client(name, moneySpent);
                Client.setId(id);

                try {
                    super.save(Client);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getName() + "," + entity.getMoneySpent());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
