package com.metakeeper.repository;

import com.metakeeper.TestRunner;
import com.metakeeper.model.dao.BaseModel;
import io.testcontainers.arangodb.containers.ArangoContainer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 04.11.2020
 */
@Testcontainers
public class RepositoryTest<T extends BaseModel> extends TestRunner {

    protected final Repository<T> repository;

    @Container
    private static final ArangoContainer container = getContainer();

    public RepositoryTest(Repository<T> repository) {
        this.repository = repository;
    }

    @BeforeEach
    void initCollection() {
        repository.deleteAll();
    }

    protected T createEntity(T prototypeEntity) {
        Optional<T> created = repository.create(prototypeEntity).join();
        assertTrue(created.isPresent());
        assertEquals(prototypeEntity.getName(), created.get().getName());
        assertNotNull(created.get().getId());
        return created.get();
    }

    protected T updateEntity(T toUpdate) {
        Optional<T> updated = repository.update(toUpdate).join();
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getName(), toUpdate.getName());
        return updated.get();
    }

    @NotNull
    protected T findEntity(T created) {
        Optional<T> found = repository.findById(created.getKey()).join();
        assertTrue(found.isPresent());
        assertEquals(created.getName(), found.get().getName());
        assertEquals(created.getId(), found.get().getId());
        return found.get();
    }

}
