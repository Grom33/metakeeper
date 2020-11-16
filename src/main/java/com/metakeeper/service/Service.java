package com.metakeeper.service;

import com.metakeeper.model.Page;
import com.metakeeper.model.Pageable;
import com.metakeeper.model.dao.BaseModel;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 05.11.2020
 */
public interface Service<T extends BaseModel> {

    CompletableFuture<Optional<T>> save(T t);

    CompletableFuture<Optional<T>> findById(String id);

    CompletableFuture<Page<T>> find(Pageable pageable);

    CompletableFuture<Boolean> delete(String id);

    CompletableFuture<Long> count();

    CompletableFuture<Boolean> deleteAll();
}
