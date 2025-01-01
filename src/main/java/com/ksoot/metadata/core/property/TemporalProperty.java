package com.ksoot.metadata.core.property;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;

public class TemporalProperty<T extends Temporal> extends SimpleProperty<T> {

  private static final long serialVersionUID = 1L;

  TemporalProperty(final String name, final T value, final Class<T> type) {
    super(name, value, type);
  }

  public static TemporalProperty<LocalDate> localDate(final String name, final LocalDate value) {
    return new TemporalProperty<>(name, value, LocalDate.class);
  }

  public static TemporalProperty<LocalTime> localTime(final String name, final LocalTime value) {
    return new TemporalProperty<>(name, value, LocalTime.class);
  }

  public static TemporalProperty<LocalDateTime> localDateTime(
      final String name, final LocalDateTime value) {
    return new TemporalProperty<>(name, value, LocalDateTime.class);
  }

  public static TemporalProperty<OffsetDateTime> offsetDateTime(
      final String name, final OffsetDateTime value) {
    return new TemporalProperty<>(name, value, OffsetDateTime.class);
  }
}
