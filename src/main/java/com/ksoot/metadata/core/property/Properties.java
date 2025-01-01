package com.ksoot.metadata.core.property;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ksoot.metadata.core.validator.Validator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;

public class Properties {

  public static AttributeBuilder<Boolean> newBoolean(final String name, final Boolean value) {
    return new PropertyBuilder<>(name, value, Boolean.class);
  }

  public static AttributeBuilder<Integer> newInteger(String name, final Integer value) {
    return new PropertyBuilder<>(name, value, Integer.class);
  }

  public static AttributeBuilder<Long> newLong(String name, final Long value) {
    return new PropertyBuilder<>(name, value, Long.class);
  }

  public static AttributeBuilder<Double> newDouble(String name, final Double value) {
    return new PropertyBuilder<>(name, value, Double.class);
  }

  public static AttributeBuilder<Float> newFloat(String name, final Float value) {
    return new PropertyBuilder<>(name, value, Float.class);
  }

  public static AttributeBuilder<Short> newShort(String name, final Short value) {
    return new PropertyBuilder<>(name, value, Short.class);
  }

  public static AttributeBuilder<Byte> newByte(String name, final Byte value) {
    return new PropertyBuilder<>(name, value, Byte.class);
  }

  public static AttributeBuilder<BigDecimal> newBigDecimal(String name, final BigDecimal value) {
    return new PropertyBuilder<>(name, value, BigDecimal.class);
  }

  public static AttributeBuilder<BigInteger> newBigInteger(String name, final BigInteger value) {
    return new PropertyBuilder<>(name, value, BigInteger.class);
  }

  public interface AttributeBuilder<T> extends ValidatorBuilder<T> {

    public ValidatorBuilder<T> attribute(final String name, final String value);

    public Builder<SimpleProperty<T>> attributes(final Map<String, Object> attributes);
  }

  public interface ValidatorBuilder<T> extends Builder<SimpleProperty<T>> {

    public ValidatorBuilder<T> validator(final Validator<T> validator);

    public Builder<SimpleProperty<T>> validators(final List<Validator<T>> validators);
  }

  public static class PropertyBuilder<T> implements AttributeBuilder<T> {

    private String name;

    private T value;

    private Class<? extends T> type;

    private List<Validator<T>> validators = Lists.newArrayList();

    private Map<String, Object> attributes = Maps.newLinkedHashMap();

    PropertyBuilder(final String name, final T value, final Class<? extends T> type) {
      this.name = name;
      this.value = value;
      this.type = type;
    }

    @Override
    public ValidatorBuilder<T> attribute(String name, String value) {
      this.attributes.put(name, value);
      return this;
    }

    @Override
    public Builder<SimpleProperty<T>> attributes(Map<String, Object> attributes) {
      this.attributes.putAll(attributes);
      return this;
    }

    @Override
    public ValidatorBuilder<T> validator(Validator<T> validator) {
      this.validators.add(validator);
      return this;
    }

    @Override
    public Builder<SimpleProperty<T>> validators(List<Validator<T>> validators) {
      this.validators.addAll(validators);
      return this;
    }

    @Override
    public SimpleProperty<T> build() {
      SimpleProperty<T> property = new SimpleProperty<>(name, value, type);
      //			if(MapUtils.isNotEmpty(this.attributes)) {
      //				property.addAttributes(attributes);
      //			}
      if (CollectionUtils.isNotEmpty(this.validators)) {
        property.addValidators(validators);
      }
      return property;
    }
  }

  private Properties() {
    throw new IllegalStateException("Properties factory class, not supposed to be instantiated");
  }
}
