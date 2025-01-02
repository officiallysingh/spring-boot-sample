package com.ksoot.adapter.controller;

import com.google.common.collect.Lists;
import com.ksoot.adapter.repository.MetaObjectRepository;
import com.ksoot.metadata.core.property.CollectionProperty;
import com.ksoot.metadata.core.property.CompositeProperty;
import com.ksoot.metadata.core.property.MapProperty;
import com.ksoot.metadata.core.property.NumericProperty;
import com.ksoot.metadata.core.property.Property;
import com.ksoot.metadata.core.property.StringProperty;
import com.ksoot.metadata.core.validator.Validators;
import com.ksoot.metadata.domain.model.MetaObject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MetaObjectController {

  private final MetaObjectRepository metaObjectRepository;

  @GetMapping(path = "/testNumericProperty")
  ResponseEntity<NumericProperty<BigDecimal>> testNumericProperty() {
    NumericProperty<BigDecimal> prop =
        NumericProperty.bigDecimalType("test-decimal", BigDecimal.valueOf(2.345));
    prop.addValidator(Validators.notNull())
        .addValidator(Validators.isNull())
        .addValidator(Validators.greaterThan(BigDecimal.valueOf(1.5)));
    return ResponseEntity.ok(prop);
  }

  @GetMapping(path = "/testListProperty")
  ResponseEntity<CollectionProperty<String, List<String>>> testListProperty() {
    List<String> list = Lists.newArrayList("One", "Two", "Three");
    CollectionProperty<String, List<String>> listProperty =
        CollectionProperty.create("sample-list", list, ArrayList.class, String.class)
        //				.addValidator(Validators.notEmpty())
        ;
    return ResponseEntity.ok(listProperty);
  }

  @GetMapping(path = "/testMapProperty")
  ResponseEntity<MapProperty<String, String, Map<String, String>>> testMapProperty() {
    Map<String, String> map = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
    MapProperty<String, String, Map<String, String>> mapProperty =
        MapProperty.create("sample-map", map, HashMap.class, String.class, String.class)
        //				.addValidator(Validators.notEmpty())
        ;
    return ResponseEntity.ok(mapProperty);
  }

  @GetMapping(path = "/testCompositeProperty")
  ResponseEntity<CompositeProperty<Property<?>>> testCompositeProperty() {
    CompositeProperty<Property<?>> compositeProperty =
        CompositeProperty.root(StringProperty.create("root", "root-value"));
    CompositeProperty<Property<?>> numericNode =
        CompositeProperty.root(
            NumericProperty.bigDecimalType("child-1", BigDecimal.valueOf(123.45)));
    //		compositeProperty.addChild();
    Map<String, String> map = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
    MapProperty<String, String, Map<String, String>> mapProperty =
        MapProperty.create("sample-map", map, HashMap.class, String.class, String.class)
        //				.addValidator(Validators.notEmpty())
        ;
    CompositeProperty<Property<?>> mapNode = CompositeProperty.root(mapProperty);
    //		List<CompositeProperty<Property<?>>> children = List.of(numericNode, mapNode);
    compositeProperty.setChildren("child-node", List.of(numericNode, mapNode));

    return ResponseEntity.ok(compositeProperty);
  }

  @Transactional
  @GetMapping(path = "/testBeanWithCompositeProperty")
  ResponseEntity<MetaObject> testBeanWithCompositeProperty() {
    CompositeProperty<Property<?>> compositeProperty =
        CompositeProperty.root(StringProperty.create("root", "root-value"));
    CompositeProperty<Property<?>> numericNode =
        CompositeProperty.root(
            NumericProperty.bigDecimalType("child-1", BigDecimal.valueOf(123.45)));
    //		compositeProperty.addChild();
    Map<String, String> map = Map.of("k1", "v1", "k2", "v2", "k3", "v3");
    MapProperty<String, String, Map<String, String>> mapProperty =
        MapProperty.create("sample-map", map, HashMap.class, String.class, String.class)
        //				.addValidator(Validators.notEmpty())
        ;
    CompositeProperty<Property<?>> mapNode = CompositeProperty.root(mapProperty);
    //		List<CompositeProperty<Property<?>>> children = List.of(numericNode, mapNode);
    compositeProperty.setChildren("child-node", List.of(numericNode, mapNode));

    MetaObject employee = MetaObject.of("Rajveer Singh", LocalDate.now(), compositeProperty);

    return ResponseEntity.ok(this.metaObjectRepository.save(employee));
  }
}
