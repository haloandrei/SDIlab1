package com.haloandrei.repository;

//import  com.sun.org.apache.xpath.internal.axes.AxesWalker;
import com.haloandrei.domain.Acquisition;
import com.haloandrei.domain.Pair;
import com.haloandrei.domain.validators.Validator;
import com.haloandrei.domain.validators.ValidatorException;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AcquisitionFileRepository extends InMemoryRepository<Pair<Long, Long>, Acquisition>{
    private String fileName;

    public AcquisitionFileRepository(Validator<Acquisition> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Pair<Long,Long> id =new Pair<>(Long.valueOf(items.get(0)), Long.valueOf(items.get(1)));
                int moneySpent = Integer.parseInt(items.get(2));
                String dateString = items.get(3);

                Acquisition acquisition= new Acquisition();
                try {
                    Date date = acquisition.getDateFormat().parse(dateString);
                    acquisition.setDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                acquisition.setId(id);
                acquisition.setPriceBought(moneySpent);
                try {
                    super.save(acquisition);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Acquisition> save(Acquisition entity) throws ValidatorException {
        Optional<Acquisition> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Acquisition entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getPriceBought() + "," + entity.getDateFormat().format(entity.getDate()));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
