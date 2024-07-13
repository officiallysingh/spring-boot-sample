package com.omniterasoft.springboot.sample.adapter.repository;

import com.omniterasoft.springboot.sample.domain.model.State;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StateRepository extends MongoRepository<State, String> {

  boolean existsByCode(final String code);

  Optional<State> findByCode(final String code);
}
