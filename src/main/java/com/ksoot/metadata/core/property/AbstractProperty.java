package com.ksoot.metadata.core.property;

import com.google.common.collect.Lists;
import com.ksoot.metadata.core.validator.Validatable;
import com.ksoot.metadata.core.validator.ValidationException;
import com.ksoot.metadata.core.validator.Validator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractProperty<T> implements Property<T> {

  private static final long serialVersionUID = 1L;

  protected String name;

  protected T value;

  protected Class<? extends T> type;

  AbstractProperty(final String name, final T value, final Class<? extends T> type) {
    this.name = name;
    this.value = value;
    this.type = type;
  }

  AbstractProperty(
      final String name,
      final T value,
      final Class<? extends T> type,
      final List<Validator<T>> validators) {
    this(name, value, type);
    this.validators = validators;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public T getValue() {
    return this.value;
  }

  @Override
  public Class<? extends T> getType() {
    return this.type;
  }

  protected List<Validator<T>> validators = Lists.newArrayList();

  @Override
  public void validate() throws ValidationException {
    LinkedList<ValidationException> failures = Lists.newLinkedList();
    for (Validator<T> validator : getValidators()) {
      try {
        validator.validate(this.value);
      } catch (ValidationException ve) {
        failures.add(ve);
      }
    }
    if (!failures.isEmpty()) {
      throw (failures.size() == 1)
          ? failures.getFirst()
          : new ValidationException(failures.toArray(new ValidationException[failures.size()]));
    }
  }

  @Override
  public Collection<Validator<T>> getValidators() {
    return this.validators;
  }

  @Override
  public Validatable<T> addValidator(Validator<T> validator) {
    this.validators.add(validator);
    return this;
  }

  @Override
  public Validatable<T> addValidators(List<Validator<T>> validators) {
    this.validators.addAll(validators);
    return this;
  }

  /**
   * Removes given <code>validator</code>, if it was registered.
   *
   * @param validator The validator to remove
   */
  @Override
  public Validatable<T> removeValidator(Validator<T> validator) {
    this.validators.remove(validator);
    return this;
  }
}
