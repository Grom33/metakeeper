package com.metakeeper.repository;

import com.arangodb.ArangoDBException;
import com.arangodb.async.ArangoCollectionAsync;
import com.arangodb.entity.CollectionPropertiesEntity;
import com.arangodb.entity.DocumentField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metakeeper.exception.RepositoryException;
import com.metakeeper.model.Page;
import com.metakeeper.model.Pageable;
import com.metakeeper.model.dao.BaseModel;
import io.micronaut.configuration.arango.ArangoClientAsync;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static io.micronaut.core.util.StringUtils.isEmpty;
import static io.micronaut.core.util.StringUtils.isNotEmpty;

/**
 * Base repository for collections
 *
 * @author Gromov Vitaly.
 * Created 31.10.2020
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
@Singleton
public abstract class BaseRepository<T extends BaseModel> implements Repository<T> {
    protected final ObjectMapper mapper;
    protected final Executor executor;

    private final String collectionName;
    private final Class<T> type;

    protected final ArangoClientAsync client;
    protected final ArangoCollectionAsync collection;

    protected BaseRepository(ObjectMapper mapper, String collectionName, ArangoClientAsync client, Executor executor, Class<T> type) {
        this.mapper = mapper;
        this.collectionName = collectionName;
        this.client = client;
        this.type = type;
        this.collection = getCollection(collectionName.toLowerCase(), client);
        this.executor = executor;
        initCollection();
    }

    private ArangoCollectionAsync getCollection(String collectionName, ArangoClientAsync client) {
        return client.db().collection(collectionName);
    }

    protected void initCollection() {
        if (!this.collection.exists().join()) {
            log.debug("Repository: '{}' >> initCollection() >> Collection doesn't exist, CREATE collection...", collectionName);
            if (isNotEmpty(this.collection.create().join().getId())) {
                log.debug("Repository: '{}' >> initCollection() >> Collection was CREATED successfully...", collectionName);
            } else {
                log.debug("Repository: '{}' >> initCollection() >> Could not create collection...", collectionName);
                throw new IllegalArgumentException("Could not CREATE collection with name: " + collectionName);
            }
        } else {
            log.debug("Repository: '{}' >> initCollection() >> Collection exist...", collectionName);
        }
    }

    @Override
    public CompletableFuture<Optional<T>> create(@NotNull final T t) {
        t.setKey(UUID.randomUUID().toString());
        final long birthTime = System.currentTimeMillis();

        t.setCreated(birthTime);
        t.setModified(birthTime);

        log.debug("Repository: '{}' >> create() >> Create entity: \n {}", collectionName, t.toString());
        return collection.insertDocument(t)
                .thenApplyAsync(created -> {
                    if (created.getOld() != null)
                        log.debug("Repository: '{}' >> create() >> CREATE OLD entity  with ID '{}'", collectionName, t.getId());
                    else if (created.getNew() != null)
                        log.debug("Repository: '{}' >> create() >> CREATED new entity with ID '{}'", collectionName, t.getId());
                    else if (isEmpty(created.getKey()))
                        log.warn(" Repository: '{}' >> create() >> NOT CREATED entity with ID '{}'", collectionName, t.getId());
                    return Optional.of(t).filter(r -> isNotEmpty(created.getKey()));
                }, executor)
                .exceptionally(this::throwRepositoryException);

    }

    @Override
    public CompletableFuture<Optional<T>> update(@NotNull final T t) {
        log.debug("Repository: '{}' >> update() >> UPDATE entity with ID {} ....", collectionName, t.getId());
        t.setModified(System.currentTimeMillis());
        return collection.updateDocument(t.getKey(), t)
                .thenApplyAsync(updated -> {
                    if (updated.getOld() != null)
                        log.debug("Repository: '{}' >> update() >> UPDATE OLD entity with ID '{}'", collectionName, t.getId());
                    else if (updated.getNew() != null)
                        log.debug("Repository: '{}' >> update() >> UPDATE NEW entity with ID '{}'", collectionName, t.getId());
                    else if (isEmpty(updated.getKey()))
                        log.warn("Repository: '{}' >> update() >> NOT UPDATED entity with ID '{}'", collectionName, t.getId());
                    return Optional.of(t).filter(v -> isNotEmpty(updated.getKey()));
                }, executor)
                .exceptionally(this::throwRepositoryException);
    }

    @Override
    public CompletableFuture<Optional<T>> findById(@NotNull final String id) {
        log.debug("Repository: '{}' >> find() >> FIND entity by ID {}", collectionName, id);

        if (isEmpty(id)) {
            log.warn("Repository: '{}' >> find() >> Empty ID was provided for FIND operation", collectionName);
            return CompletableFuture.completedFuture(Optional.empty());
        }

        return collection.getDocument(id, Map.class)
                .thenApplyAsync(this::transformEntity, executor)
                .thenApplyAsync(rawEntity -> {
                    if (rawEntity == null)
                        log.debug("Repository: '{}' >> find() >> NOT FOUND with ID '{}'", collectionName, id);
                    else
                        log.debug("Repository: '{}' >> find() >> FOUND with ID '{}'", collectionName, id);
                    return Optional.ofNullable(deserialize(rawEntity));
                }, executor)
                .exceptionally(this::throwRepositoryException);
    }

    @Override
    public CompletableFuture<Page<T>> find(Pageable pageable) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> delete(@NotNull final String id) {
        log.debug("Repository: '{}' >> delete() >> DELETE entity with id {}", collectionName, id);
        if (isEmpty(id)) {
            log.debug("Repository: '{}' >> delete() >> Nullable ID was given for DELETE operation", collectionName);
            return CompletableFuture.completedFuture(true);
        }

        return collection.deleteDocument(id)
                .thenApplyAsync(r -> isNotEmpty(r.getId()), executor)
                .exceptionally(this::throwRepositoryException);
    }

    @Override
    public CompletableFuture<Long> count() {
        log.debug("Repository: '{}' >> count() >> Get collection COUNT....", collectionName);
        return collection.count()
                .thenApplyAsync(CollectionPropertiesEntity::getCount, executor);
    }

    @Override
    public CompletableFuture<Boolean> deleteAll() {
        log.debug("Repository: '{}' >> deleteAll() >> Truncate collection....", collectionName);
        return collection.truncate()
                .thenApplyAsync(collectionEntity -> true);
    }

    private Map transformEntity(@Nullable final Map entityAsMap) {
        if (entityAsMap == null) {
            log.debug("Repository: '{}' >> transformEntity() >> Can't TRANSFORM null entity", collectionName);
            return null;
        }
        log.debug("Repository: '{}' >> transformEntity() >> TRANSFORM entity as Map with id: {}", collectionName,
                entityAsMap.get(DocumentField.Type.ID.getSerializeName()));

        entityAsMap.put("id", entityAsMap.remove(DocumentField.Type.ID.getSerializeName()));
        entityAsMap.put("key", entityAsMap.remove(DocumentField.Type.KEY.getSerializeName()));
        entityAsMap.put("revision", entityAsMap.remove(DocumentField.Type.REV.getSerializeName()));
        return entityAsMap;
    }

    protected T deserialize(final Map document) {
        try {
            return mapper.convertValue(document, type);
        } catch (Exception e) {
            log.warn("Repository: '{}' >> Failed deserialize document with error: {}", collectionName, e.getMessage());
            return null;
        }
    }

    protected final <O> O throwRepositoryException(Throwable e) {
        final Throwable root = getRootException(e);

        if (root instanceof RepositoryException)
            throw ((RepositoryException) root);

        if (root instanceof ArangoDBException)
            throw new RepositoryException((ArangoDBException) root);

        throw new RepositoryException(root.getMessage());
    }

    private Throwable getRootException(Throwable e) {
        if (e.getCause() == null || e instanceof ArangoDBException)
            return e;

        return getRootException(e.getCause());
    }

    @PreDestroy
    public void shutdown() {
        client.close();
    }
}
