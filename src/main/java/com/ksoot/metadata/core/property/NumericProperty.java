package com.ksoot.metadata.core.property;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.springframework.data.annotation.PersistenceCreator;

public class NumericProperty<N extends Number> extends SimpleProperty<N> {

  private static final long serialVersionUID = 1L;

  @PersistenceCreator
  public static <N extends Number> NumericProperty<N> newInstance(
      final String name, final N value, final Class<N> type) {
    return new NumericProperty<>(name, value, type);
  }

  NumericProperty(final String name, final N value, final Class<N> type) {
    super(name, value, type);
  }

  public static NumericProperty<Integer> integerType(String name, final Integer value) {
    return new NumericProperty<>(name, value, Integer.class);
  }

  public static NumericProperty<Long> longType(String name, final Long value) {
    return new NumericProperty<>(name, value, Long.class);
  }

  public static NumericProperty<Double> doubleType(String name, final Double value) {
    return new NumericProperty<>(name, value, Double.class);
  }

  public static NumericProperty<Float> floatType(String name, final Float value) {
    return new NumericProperty<>(name, value, Float.class);
  }

  public static NumericProperty<Short> shortType(String name, final Short value) {
    return new NumericProperty<>(name, value, Short.class);
  }

  public static NumericProperty<Byte> byteType(String name, final Byte value) {
    return new NumericProperty<>(name, value, Byte.class);
  }

  public static NumericProperty<BigDecimal> bigDecimalType(String name, final BigDecimal value) {
    return new NumericProperty<>(name, value, BigDecimal.class);
  }

  public static NumericProperty<BigInteger> bigIntegerType(String name, final BigInteger value) {
    return new NumericProperty<>(name, value, BigInteger.class);
  }
}
