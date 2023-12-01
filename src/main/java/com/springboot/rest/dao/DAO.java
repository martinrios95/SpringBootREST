package com.springboot.rest.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<Entity, Key> {
    Optional<Entity> find(Key k);
    List<Entity> findAll();
    boolean delete(Key k);
}
