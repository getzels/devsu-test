package com.pichincha.pichinchaapi.service;

import com.pichincha.pichinchaapi.entity.BaseEntity;
import com.pichincha.pichinchaapi.exception.CustomAPIException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class BaseService<T extends BaseEntity, ID> {

    protected abstract JpaRepository<T, ID> repository();

    public List<T> findAll() {
        return repository().findAll();
    }

    public Optional<T> findById(ID id) {
        return repository().findById(id);
    }

    public T save(T entity) {
        return repository().save(entity);
    }

    public T update(T entity) {
        findById((ID) entity.getId()).orElseThrow(CustomAPIException::new);
        return repository().save(entity);
    }

    public List<T> saveAll(Iterable<T> entities) {
        return repository().saveAll(entities);
    }

    public void deleteById(ID id) {
        repository().deleteById(id);
    }

    public void delete(T entity) {
        repository().delete(entity);
    }

    public long count() {
        return repository().count();
    }

    public boolean existsById(ID id) {
        return repository().existsById(id);
    }
}

