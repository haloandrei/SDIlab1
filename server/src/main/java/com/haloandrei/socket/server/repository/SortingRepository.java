package com.haloandrei.socket.server.repository;



import com.haloandrei.socket.common.domain.BaseEntity;
import com.haloandrei.socket.server.repository.Implementation.Sort;

import java.io.Serializable;

public interface SortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends CrudRepository<ID, T> {

    Iterable<T> findAll(Sort sort);

    //TODO: insert sorting-related code here
}
