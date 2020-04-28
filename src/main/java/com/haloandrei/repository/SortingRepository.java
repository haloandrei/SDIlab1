package com.haloandrei.repository;

import com.haloandrei.domain.BaseEntity;
import com.haloandrei.repository.Implementation.Sort;

import java.io.Serializable;

public interface SortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends CrudRepository<ID, T> {

    Iterable<T> findAll(Sort sort);

    //TODO: insert sorting-related code here
}
