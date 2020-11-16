package com.metakeeper.controller.impl;

import com.metakeeper.model.dao.BaseModel;
import com.metakeeper.repository.Repository;
import com.metakeeper.repository.RepositoryTest;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.runtime.server.EmbeddedServer;

import javax.annotation.PostConstruct;
import java.net.http.HttpClient;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 10.11.2020
 */
public abstract class ControllerTest<T extends BaseModel> extends RepositoryTest<T> {

    private final EmbeddedServer server;
    private final ApplicationContext context;

    private RxHttpClient rxHttpClient;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public ControllerTest(Repository<T> repository, ApplicationContext context) {
        super(repository);
        this.server = context.getBean(EmbeddedServer.class);
        this.context = context;
    }

    @PostConstruct
    protected void setupClient() {
        this.rxHttpClient = context.createBean(RxHttpClient.class, server.getURL());
    }

    protected RxHttpClient getHttpClient() {
        return rxHttpClient;
    }
}
