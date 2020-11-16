package com.metakeeper.service;

import com.metakeeper.model.Page;
import com.metakeeper.model.Pageable;
import com.metakeeper.model.dao.BaseModel;
import com.metakeeper.repository.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 05.11.2020
 */
public abstract class BaseService<T extends BaseModel> implements Service<T> {

    protected final Repository<T> repository;

    public BaseService(Repository<T> repository) {
        this.repository = repository;
    }


    @Override
    public CompletableFuture<Optional<T>> save(T t) {
        return repository.save(t);
    }

    @Override
    public CompletableFuture<Optional<T>> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public CompletableFuture<Boolean> delete(String id) {
        return repository.delete(id);
    }

    @Override
    public CompletableFuture<Long> count() {
        return repository.count();
    }

    @Override
    public CompletableFuture<Boolean> deleteAll() {
        return repository.deleteAll();
    }

    @Override
    public CompletableFuture<Page<T>> find(Pageable pageable) {
        return repository.find(pageable);
    }
}
