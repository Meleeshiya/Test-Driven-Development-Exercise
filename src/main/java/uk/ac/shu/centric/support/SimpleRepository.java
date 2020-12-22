package uk.ac.shu.centric.support;

import java.util.*;


/**
 * For simplicity we are not using a real underlying database.
 */
public abstract class SimpleRepository<T extends SimpleEntity> {

    private final Map<String, T> entities = new HashMap<>();

    public T findById(String id) {
        return entities.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    public T save(T entity) {
        // Ensure has ID.
        if (entity.getId() == null)
            entity.setId(UUID.randomUUID().toString());

        // Save or update the entire officer (for simplicity).
        this.entities.put(entity.getId(), entity);

        return entity;
    }

}
