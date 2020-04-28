package com.haloandrei.service;

import com.haloandrei.domain.Client;
import com.haloandrei.repository.Repository;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private Repository<Long, Client> repository;

    public ClientService(Repository repoInit){ repository = repoInit; }

    public void addClient(Client newClient) { repository.save(newClient); }

    public Set<Client> findFilteredMoneySpentClients(int money_spent){
        return StreamSupport.stream(repository.findAll().spliterator(), false).filter(x->x.getMoneySpent()>money_spent).collect(Collectors.toSet());
    }
    public Set<Client> getAllClients(){
        Iterable<Client> Clients = repository.findAll();
        return StreamSupport.stream(Clients.spliterator(),false).collect(Collectors.toSet());
    }
}
