package com.haloandrei.service;

import com.haloandrei.domain.Acquisition;
import com.haloandrei.domain.Pair;
import com.haloandrei.repository.Repository;

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
