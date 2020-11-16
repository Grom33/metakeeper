package com.metakeeper.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metakeeper.controller.BaseController;
import com.metakeeper.model.Page;
import com.metakeeper.model.Pageable;
import com.metakeeper.model.Prototype;
import com.metakeeper.service.Service;
import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.metakeeper.controller.Mapping.PROTOTYPES;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 05.11.2020
 */
@Slf4j
@Controller(PROTOTYPES)
public class PrototypeController extends BaseController<Prototype> {

    @Inject
    public PrototypeController(Service<Prototype> service, ObjectMapper mapper) {
        super(service, mapper);
    }

    @Get("/count")
    CompletableFuture<Long> count() {
        log.debug("count() >> ...");
        return service.count();
    }

    @Post
        //todo Make error interceptor
    CompletableFuture<Prototype> create(@Body String prototype) throws JsonProcessingException {
        log.debug("create() >> body: {}", prototype);
        Prototype parsed = mapper.readValue(prototype, Prototype.class);
        return service.save(parsed).thenApply(Optional::get);
    }

    @Put
        //todo Make error interceptor
    CompletableFuture<Prototype> update(@Body String prototype) throws JsonProcessingException {
        log.debug("update() >> body: {}", prototype);
        Prototype parsed = mapper.readValue(prototype, Prototype.class);
        return service.save(parsed).thenApply(Optional::get);
    }

    @Delete("/{id}")
    CompletableFuture<Boolean> delete(@PathVariable String id) {
        log.debug("delete() >> id: {}", id);
        return service.delete(id);
    }

    @Get("/{id}")
    CompletableFuture<Prototype> findById(@PathVariable String id) {
        log.debug("findById() >> id: {}", id);
        return service.findById(id)
                .thenApply(mayBeProto -> mayBeProto
                        .orElseThrow(() -> new RuntimeException("ProtoType not found!")));
    }

    @Get
    CompletableFuture<Page<Prototype>> find(@RequestBean Pageable pageable) {
        log.debug("find() >> pageable: {}", pageable);
        return CompletableFuture.supplyAsync(Page::new);
        //return service.find(pageable);
    }

}
