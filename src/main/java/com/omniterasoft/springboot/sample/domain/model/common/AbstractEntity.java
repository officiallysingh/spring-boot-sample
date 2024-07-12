package com.omniterasoft.springboot.sample.domain.model.common;

import static jakarta.persistence.GenerationType.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.UUID;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public class AbstractEntity<V extends Comparable<V> & Serializable> {

  @Id
  @GeneratedValue(strategy = UUID)
  protected UUID id;

  public UUID getId() {
    return id;
  }

  @Version
  @Column(name = "version", nullable = false)
  protected V version;

  public V getVersion() {
    return version;
  }
}
