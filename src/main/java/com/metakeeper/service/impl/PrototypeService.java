package com.metakeeper.service.impl;

import com.metakeeper.model.Prototype;
import com.metakeeper.repository.Repository;
import com.metakeeper.service.BaseService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Description
 *
 * @author Gromov Vitaly.
 * Created 05.11.2020
 */

@Singleton
public class PrototypeService extends BaseService<Prototype> {

    @Inject
    public PrototypeService(Repository<Prototype> repository) {
        super(repository);
    }


}
