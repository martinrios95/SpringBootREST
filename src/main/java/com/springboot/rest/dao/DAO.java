package com.springboot.rest.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<Entity, Key> {
    Optional<Entity> find(Key k);
    List<Entity> findAll();
    boolean add(Entity e);
    boolean update(Key k, Entity e);
    boolean delete(Key k);
}
