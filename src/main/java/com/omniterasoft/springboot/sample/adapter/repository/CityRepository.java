package com.omniterasoft.springboot.sample.adapter.repository;

import com.omniterasoft.springboot.sample.domain.model.City;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City, String> {

  boolean existsByCode(final String code);

  Optional<City> findByCode(final String code);

  List<City> findAllByStateId(final String stateId);
}
