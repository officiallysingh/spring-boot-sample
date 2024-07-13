package com.omniterasoft.springboot.sample.domain.mapper;

import com.omniterasoft.springboot.sample.domain.model.City;
import com.omniterasoft.springboot.sample.domain.model.Employee;
import com.omniterasoft.springboot.sample.domain.model.State;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeVM;
import java.util.Comparator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;

@Mapper
public interface SampleMappers {

  public static final Sort SORT_BY_NAME = Sort.by(Sort.Order.asc("name").ignoreCase());

  public static final Comparator<State> STATE_BY_NAME_COMPARATOR =
      Comparator.comparing(state -> state.getName().toLowerCase());

  public static final Comparator<City> CITY_BY_NAME_COMPARATOR =
      Comparator.comparing(city -> city.getName().toLowerCase());

  SampleMappers INSTANCE = Mappers.getMapper(SampleMappers.class);

  EmployeeVM toEmployeeVM(final Employee employee);
}
