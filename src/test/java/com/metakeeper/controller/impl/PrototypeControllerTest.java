package com.metakeeper.controller.impl;

import com.metakeeper.controller.Mapping;
import com.metakeeper.model.Page;
import com.metakeeper.model.Prototype;
import com.metakeeper.repository.Repository;
import com.metakeeper.repository.RepositoryTest;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.testcontainers.arangodb.containers.ArangoContainer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.testcontainers.junit.jupiter.Container;

import javax.inject.Inject;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 09.11.2020
 */
@MicronautTest
class PrototypeControllerTest extends RepositoryTest<Prototype> {

    @Inject
    @Client("/")
    RxHttpClient client;

    @Container
    private static final ArangoContainer container = getContainer();

    @Inject
    public PrototypeControllerTest(Repository<Prototype> repository) {
        super(repository);
    }

    @Disabled
    @DisplayName("Test inject pageable into controller methods")
    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    void getCount() {
        Long count = client.toBlocking().retrieve(HttpRequest.GET("/count"), Long.class);
        System.out.println(count);
    }

    @DisplayName("Test inject pageable into controller methods")
    @RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    void findSomeOne() {
        Page page = client.toBlocking().retrieve(HttpRequest.GET(Mapping.PROTOTYPES +"/?page=1&size=10&sort=asc&query=test"), Page.class);
        System.out.println(page);
    }
}