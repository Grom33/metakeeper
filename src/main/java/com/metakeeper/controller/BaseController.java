package com.metakeeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metakeeper.model.dao.BaseModel;
import com.metakeeper.service.Service;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 05.11.2020
 */
public class BaseController<T extends BaseModel> {

    protected final Service<T> service;
    protected final ObjectMapper mapper;

    public BaseController(Service<T> service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
}
