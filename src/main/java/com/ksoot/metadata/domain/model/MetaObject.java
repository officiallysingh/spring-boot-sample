package com.ksoot.metadata.domain.model;

import com.ksoot.metadata.config.CompositePropertyValueConverter;
import com.ksoot.metadata.core.jackson.PropertySerialization;
import com.ksoot.metadata.core.jackson.PropertySerializationMode;
import com.ksoot.metadata.core.property.CompositeProperty;
import com.ksoot.metadata.core.property.Property;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.convert.ValueConverter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
// @AllArgsConstructor(staticName = "of")
@Document(collection = "meta_objects")
public class MetaObject {

  @Id @NotNull protected UUID id;

  @Version protected Long version;

  private String metadataId;

  private String name;

  private LocalDate dob;

  //	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property =
  // "metadataId")
  @PropertySerialization(mode = PropertySerializationMode.ALL)
  //	@Unwrapped(onEmpty = OnEmpty.USE_NULL)
  //	@JsonUnwrapped
  @ValueConverter(CompositePropertyValueConverter.class)
  private CompositeProperty<Property<?>> compositeProperty;

  @PersistenceCreator
  private MetaObject(
      UUID id,
      String metadataId,
      String name,
      LocalDate dob,
      CompositeProperty<Property<?>> compositeProperty) {
    this.id = id;
    this.metadataId = metadataId;
    this.name = name;
    this.dob = dob;
    this.compositeProperty = compositeProperty;
  }

  public static MetaObject of(
      String name, LocalDate dob, CompositeProperty<Property<?>> compositeProperty) {
    return new MetaObject(UUID.randomUUID(), "123", name, dob, compositeProperty);
  }
}
