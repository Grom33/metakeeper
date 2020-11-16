package com.metakeeper;

import io.testcontainers.arangodb.containers.ArangoContainer;
import org.junit.jupiter.api.Assertions;

import static io.micronaut.core.util.StringUtils.hasText;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 02.11.2020
 */
public abstract class TestRunner extends Assertions {
    protected static int getPort() {
        final String port = System.getenv("ARANGO_PORT");
        return hasText(port)
                ? Integer.parseInt(port)
                : 8529;
    }

    protected static ArangoContainer getContainer() {
        return new ArangoContainer()
                .withoutAuth()
                .withPort(getPort());
    }
}
