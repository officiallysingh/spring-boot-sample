package com.omniterasoft.springboot.sample.domain.mapper;

import com.omniterasoft.springboot.sample.domain.model.Employee;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeVM;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SampleMappers {

  SampleMappers INSTANCE = Mappers.getMapper(SampleMappers.class);

  EmployeeVM toEmployeeVM(final Employee employee);
}
