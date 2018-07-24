package com.forthelight.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <Entity extends Serializable, Id extends Object> {
    Entity findById(Id id);
    List<Entity> findAll();

    int insert(Entity entity);
    int update(Entity entity);
    int delete(Entity entity);
    int delete(Id id);

}
