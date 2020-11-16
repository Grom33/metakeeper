package com.metakeeper.repository;

import com.metakeeper.model.Page;
import com.metakeeper.model.Pageable;
import com.metakeeper.model.dao.BaseModel;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static io.micronaut.core.util.StringUtils.isEmpty;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 01.11.2020
 */

public interface Repository<T extends BaseModel> {

    CompletableFuture<Optional<T>> create(T t);

    CompletableFuture<Optional<T>> update(T t);

    default CompletableFuture<Optional<T>> save(T t) {
        if (isEmpty(t.getKey()))
            return create(t);
        return findById(t.getKey())
                .thenCompose(found -> found.isPresent() ? update(t) : create(t));
    }

    CompletableFuture<Optional<T>> findById(String id);

    CompletableFuture<Boolean> delete(String id);

    CompletableFuture<Long> count();

    CompletableFuture<Boolean> deleteAll();

    CompletableFuture<Page<T>> find(Pageable pageable);
}
