package com.ksoot.adapter.repository;

import com.ksoot.metadata.domain.model.MetaObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetaObjectRepository extends MongoRepository<MetaObject, String> {}
