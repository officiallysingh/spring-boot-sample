package com.ksoot.metadata.core.property;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import org.springframework.data.annotation.Transient;

public abstract class AbstractTree<T> implements TreeNode<T> {

  private static final long serialVersionUID = 1L;

  protected T value;

  protected String childNodeName;

  protected List<TreeNode<T>> children;

  @Transient protected TreeNode<T> parent;

  protected AbstractTree(T value) {
    this.value = value;
  }

  protected AbstractTree(final T value, String childNodeName, final List<TreeNode<T>> children) {
    this.value = value;
    this.childNodeName = childNodeName;
    this.children = children;
  }

  @Override
  public T getNode() {
    return this.value;
  }

  @Override
  public String getChildNodeName() {
    return this.childNodeName;
  }

  @Override
  public List<TreeNode<T>> getChildren() {
    return this.children;
  }

  @Override
  public void setChildren(String childNodeName, List<TreeNode<T>> nodes) {
    this.childNodeName = childNodeName;
    this.children = Lists.newLinkedList();
    nodes.forEach(
        node -> {
          node.setParent(this);
          this.children.add(node);
        });
  }

  @Override
  public Optional<TreeNode<T>> getParent() {
    return Optional.ofNullable(this.parent);
  }

  @Override
  public void setParent(TreeNode<T> parentNode) {
    this.parent = parentNode;
  }
}
