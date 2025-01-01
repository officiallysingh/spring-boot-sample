package com.ksoot.metadata.core.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;

public interface TreeNode<T> extends Serializable {

  @JsonInclude(value = Include.NON_EMPTY)
  String getChildNodeName();

  void setChildren(String childNodeName, List<TreeNode<T>> nodes);

  @JsonInclude(value = Include.NON_EMPTY)
  List<TreeNode<T>> getChildren();

  T getNode();

  void setParent(TreeNode<T> parentNode);

  @JsonIgnore
  Optional<TreeNode<T>> getParent();

  @JsonIgnore
  default boolean isRoot() {
    return this.getParent().isEmpty();
  }

  @JsonIgnore
  default boolean isLeaf() {
    return CollectionUtils.isEmpty(getChildren());
  }

  // Path getPath();

  //	void foreach();

  //	void iterator();
}
