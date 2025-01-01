package com.ksoot.metadata.core.property;

import java.util.List;
import org.springframework.data.annotation.PersistenceCreator;

// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property =
// "metadataId", resolver = SimpleObjectIdResolver.class)
public class CompositeProperty<T extends Property<?>> extends AbstractTree<T> {

  private static final long serialVersionUID = 1L;

  CompositeProperty(T value) {
    super(value);
  }

  @PersistenceCreator
  CompositeProperty(final T value, String childNodeName, final List<TreeNode<T>> children) {
    super(value, childNodeName, children);
  }

  public static <T extends Property<?>> CompositeProperty<T> root(T value) {
    return new CompositeProperty<>(value);
  }

  public static <T extends Property<?>> CompositeProperty<T> create(
      final T value, String childNodeName, final List<TreeNode<T>> children) {
    return new CompositeProperty<>(value, childNodeName, children);
  }
}
