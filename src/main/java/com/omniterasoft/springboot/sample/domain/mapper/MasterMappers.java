package com.omniterasoft.springboot.sample.domain.mapper;

import com.ksoot.problem.core.Problems;
import com.omniterasoft.springboot.sample.domain.MasterErrorTypes;
import com.omniterasoft.springboot.sample.domain.model.City;
import com.omniterasoft.springboot.sample.domain.model.State;
import com.omniterasoft.springboot.sample.domain.model.dto.CityVM;
import com.omniterasoft.springboot.sample.domain.model.dto.StateVM;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MasterMappers {

  MasterMappers INSTANCE = Mappers.getMapper(MasterMappers.class);

  // ---------- State, City and Area ----------
  default StateVM toStateViewModel(final State state, final String expand) {
    if (StringUtils.isEmpty(expand)) {
      return toStateSummaryViewModel(state);
    } else if (Objects.equals(expand, "cities")) {
      return stateViewModel(state);
    } else {
      throw Problems.newInstance(MasterErrorTypes.INVALID_STATE_EXPAND_HEADER)
          .detailArgs(expand)
          .throwAble();
    }
  }

  @Named("stateSummaryViewModel")
  @Mapping(target = "cities", ignore = true)
  StateVM toStateSummaryViewModel(final State state);

  @Mapping(target = "cities", qualifiedByName = "citySummaryViewModel")
  StateVM stateViewModel(final State state);

  default CityVM toCityViewModel(final City city, final List<String> expand) {
    if (CollectionUtils.isEmpty(expand)) {
      return citySummaryViewModel(city);
    }
    if (expand.contains("state") && expand.contains("areas")) {
      return cityViewModel(city);
    } else if (expand.contains("state")) {
      return cityWithStateViewModel(city);
    } else if (expand.contains("areas")) {
      return cityWithAreasViewModel(city);
    } else {
      throw Problems.newInstance(MasterErrorTypes.INVALID_CITY_EXPAND_HEADER)
          .detailArgs(expand)
          .throwAble();
    }
  }

  @Named("citySummaryViewModel")
  @Mapping(target = "state", ignore = true)
  CityVM citySummaryViewModel(final City city);

  @Named("cityWithStateViewModel")
  @Mapping(source = "state", target = "state", qualifiedByName = "stateSummaryViewModel")
  CityVM cityWithStateViewModel(final City city);

  @Named("cityWithAreasViewModel")
  @Mapping(source = "state", target = "state", ignore = true)
  CityVM cityWithAreasViewModel(final City city);

  @Mapping(source = "state", target = "state", qualifiedByName = "stateSummaryViewModel")
  CityVM cityViewModel(final City city);
}
