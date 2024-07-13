package com.omniterasoft.springboot.sample.adapter.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ksoot.problem.core.Problems;
import com.omniterasoft.springboot.sample.common.CommonErrorKeys;
import com.omniterasoft.springboot.sample.common.spring.rest.response.APIResponse;
import com.omniterasoft.springboot.sample.common.spring.util.GeneralMessageResolver;
import com.omniterasoft.springboot.sample.domain.mapper.MasterMappers;
import com.omniterasoft.springboot.sample.domain.model.City;
import com.omniterasoft.springboot.sample.domain.model.State;
import com.omniterasoft.springboot.sample.domain.model.dto.*;
import com.omniterasoft.springboot.sample.domain.service.StateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AddressController implements AddressApi {

  private final StateService stateService;

  // -------- States ----------
  @Override
  public ResponseEntity<Boolean> doesStateExists(final String code) {
    return ResponseEntity.ok(this.stateService.doesStateExists(code));
  }

  @Override
  public ResponseEntity<APIResponse<?>> createState(final StateCreationRQ request) {
    final State state = this.stateService.createState(request);
    return ResponseEntity.created(
            linkTo(methodOn(AddressController.class).getStateById(state.getId(), null))
                .withSelfRel()
                .toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_CREATED));
  }

  @Override
  public ResponseEntity<StateVM> getStateById(final String id, final String expand) {
    return ResponseEntity.ok(
        MasterMappers.INSTANCE.toStateViewModel(this.stateService.getStateById(id), expand));
  }

  @Override
  public ResponseEntity<StateVM> getStateByCode(final String code, final String expand) {
    return ResponseEntity.ok(
        MasterMappers.INSTANCE.toStateViewModel(this.stateService.getStateByCode(code), expand));
  }

  @Override
  public ResponseEntity<List<StateVM>> getAllStates(final String expand) {
    return ResponseEntity.ok(
        this.stateService.getAllStates().stream()
            .map(state -> MasterMappers.INSTANCE.toStateViewModel(state, expand))
            .toList());
  }

  @Override
  public ResponseEntity<List<Pair<String, String>>> getAllStateListItems() {
    return ResponseEntity.ok(
        this.stateService.getAllStates().stream().map(State::listItem).toList());
  }

  @Override
  public ResponseEntity<APIResponse<?>> updateState(
      final String id, final StateUpdationRQ request) {
    if (request.isEmpty()) {
      throw Problems.newInstance(CommonErrorKeys.EMPTY_UPDATE_REQUEST)
          .throwAble(HttpStatus.BAD_REQUEST);
    }
    this.stateService.updateState(id, request);
    return ResponseEntity.ok()
        .location(
            linkTo(methodOn(AddressController.class).getStateById(id, null)).withSelfRel().toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_UPDATED));
  }

  @Override
  public ResponseEntity<APIResponse<?>> deleteState(final String id) {
    this.stateService.deleteState(id);
    return ResponseEntity.ok(
        APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_DELETED));
  }

  // -------- Cities ----------
  @Override
  public ResponseEntity<Boolean> doesCityExists(final String code) {
    return ResponseEntity.ok(this.stateService.doesCityExists(code));
  }

  @Override
  public ResponseEntity<APIResponse<?>> createCity(
      final String stateId, final CityCreationRQ request) {
    final City city = this.stateService.createCity(stateId, request);
    return ResponseEntity.created(
            linkTo(methodOn(AddressController.class).getCityById(city.getId(), null))
                .withSelfRel()
                .toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_CREATED));
  }

  @Override
  public ResponseEntity<CityVM> getCityById(final String id, final List<String> expand) {
    return ResponseEntity.ok(
        MasterMappers.INSTANCE.toCityViewModel(this.stateService.getCityById(id), expand));
  }

  @Override
  public ResponseEntity<CityVM> getCityByCode(final String code, final List<String> expand) {
    return ResponseEntity.ok(
        MasterMappers.INSTANCE.toCityViewModel(this.stateService.getCityByCode(code), expand));
  }

  @Override
  public ResponseEntity<List<CityVM>> getAllCitiesByStateId(
      final String stateId, final List<String> expand) {
    return ResponseEntity.ok(
        this.stateService.findAllCitiesByStateId(stateId).stream()
            .map(city -> MasterMappers.INSTANCE.toCityViewModel(city, expand))
            .toList());
  }

  @Override
  public ResponseEntity<List<CityVM>> getAllCities(final List<String> expand) {
    return ResponseEntity.ok(
        this.stateService.getAllCities().stream()
            .map(city -> MasterMappers.INSTANCE.toCityViewModel(city, expand))
            .toList());
  }

  @Override
  public ResponseEntity<List<Pair<String, String>>> getAllCitiesListItems() {
    return ResponseEntity.ok(
        this.stateService.getAllCities().stream().map(City::listItem).toList());
  }

  @Override
  public ResponseEntity<APIResponse<?>> updateCity(final String id, final CityUpdationRQ request) {
    if (request.isEmpty()) {
      throw Problems.newInstance(CommonErrorKeys.EMPTY_UPDATE_REQUEST)
          .throwAble(HttpStatus.BAD_REQUEST);
    }
    this.stateService.updateCity(id, request);
    return ResponseEntity.ok()
        .location(
            linkTo(methodOn(AddressController.class).getCityById(id, null)).withSelfRel().toUri())
        .body(APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_UPDATED));
  }

  @Override
  public ResponseEntity<APIResponse<?>> deleteCity(final String id) {
    this.stateService.deleteCity(id);
    return ResponseEntity.ok(
        APIResponse.newInstance().addSuccess(GeneralMessageResolver.RECORD_DELETED));
  }
}
