package com.metakeeper.repository.impl;

import com.metakeeper.model.Prototype;
import com.metakeeper.repository.RepositoryTest;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.metakeeper.TestDataGenerator.getPrototypeEntity;
import static com.metakeeper.TestDataGenerator.getRandomNumber;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 01.11.2020
 */

@MicronautTest
@SuppressWarnings("OptionalGetWithoutIsPresent")
class PrototypeRepositoryTest extends RepositoryTest<Prototype> {

    @Inject
    public PrototypeRepositoryTest(PrototypeRepository repository) {
        super(repository);
    }

    @DisplayName("Test for creating entity")
    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    void createEntity() {
        Prototype prototypeEntity = getPrototypeEntity();
        createEntity(prototypeEntity);
    }

    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Test for update entity")
    void updateEntity() {
        Prototype prototypeEntity = getPrototypeEntity();
        Prototype created = createEntity(prototypeEntity);
        Prototype found = findEntity(created);
        found.setName("New name");
        found.getAttributes().put("new", "new Attribute");

        updateEntity(found);

        Prototype foundAfterUpdate = findEntity(found);
        assertEquals(foundAfterUpdate.getAttributes(), found.getAttributes());
    }

    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Test for delete entity")
    void deleteEntity() {
        Prototype prototypeEntity = getPrototypeEntity();
        Prototype created = createEntity(prototypeEntity);

        findEntity(created);

        Boolean deleteResult = repository.delete(created.getKey()).join();
        assertTrue(deleteResult);

        Optional<Prototype> found = repository.findById(created.getKey()).join();
        assertFalse(found.isPresent());
    }

    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Test for save entity")
    void saveEntity() {

        Prototype prototypeEntityToCreate = getPrototypeEntity();
        prototypeEntityToCreate.setName("toCreate");
        Prototype prototypeEntityToUpdate = getPrototypeEntity();
        prototypeEntityToUpdate.setName("toUpdate");
        Prototype toUpdate = createEntity(prototypeEntityToUpdate);

        Optional<Prototype> created = repository.save(prototypeEntityToCreate).join();
        assertNotNull(created.get().getId());
        assertEquals(created.get().getCreated(), created.get().getModified());

        Optional<Prototype> updated = repository.save(toUpdate).join();
        assertNotNull(updated.get().getId());
        assertEquals(toUpdate.getId(), updated.get().getId());
        assertNotEquals(updated.get().getCreated(), updated.get().getModified());
    }

    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Count entities of collections")
    void countTest() {
        Prototype entity = getPrototypeEntity();
        final int n = getRandomNumber(2, 10);
        IntStream.range(0, n).forEach(i -> createEntity(entity));
        assertEquals(n, repository.count().join());
    }


}