package com.metakeeper.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metakeeper.model.Prototype;
import com.metakeeper.repository.BaseRepository;
import io.micronaut.configuration.arango.ArangoClientAsync;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Executor;

/**
 * Repository for prototype entities.
 *
 * @author Gromov Vitaly.
 * Created 01.11.2020
 * @see BaseRepository
 * @see Prototype
 */


@Singleton
public class PrototypeRepository extends BaseRepository<Prototype> {

    @Inject
    public PrototypeRepository(ObjectMapper mapper, ArangoClientAsync client, Executor executor) {
        super(mapper, "prototype", client, executor, Prototype.class);
    }
}
