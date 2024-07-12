package com.omniterasoft.springboot.sample.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ClassUtils {

  public static boolean doesClassExist(final String className) {
    try {
      org.apache.commons.lang3.ClassUtils.getClass(className, false);
      return true;
    } catch (final ClassNotFoundException e) {
      return false;
    }
  }
}
