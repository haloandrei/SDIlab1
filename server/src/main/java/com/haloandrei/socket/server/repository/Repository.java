package com.haloandrei.socket.server.repository;



import com.haloandrei.socket.common.domain.BaseEntity;
import com.haloandrei.socket.common.domain.validators.ValidatorException;

import java.util.Optional;

;


public interface Repository<ID, T extends BaseEntity<ID>> {

    Optional<T> findOne(ID id);


    Iterable<T> findAll();

    Optional<T> save(T entity) throws ValidatorException;

    Optional<T> delete(ID id);

    Optional<T> update(T entity) throws ValidatorException;
}
