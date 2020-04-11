package service;

import domain.Acquisition;
import domain.Client;
import domain.Pair;
import repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AcquisitionService {
    private Repository<Pair<Long,Long>, Acquisition> repository;

    public AcquisitionService(Repository repoInit){ repository = repoInit; }

    public void addClient(Acquisition newAcquisition) { repository.save(newAcquisition); }

    //TODO - filter by Client, by movie
    public Set<Acquisition> getAllAcquisition(){
        Iterable<Acquisition> Acquisitions = repository.findAll();
        return StreamSupport.stream(Acquisitions.spliterator(),false).collect(Collectors.toSet());
    }
}
