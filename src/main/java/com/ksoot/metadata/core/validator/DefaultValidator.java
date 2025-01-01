/*
 * Copyright 2016-2017 Axioma srl.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ksoot.metadata.core.validator;

import java.util.function.Predicate;
import org.springframework.context.MessageSourceResolvable;

/**
 * Default {@link Validator} implementation which uses a {@link Predicate} as validation operation.
 *
 * @param <T> Supported value type
 * @since 5.0.0
 */
public class DefaultValidator<T> implements Validator<T> {

  private static final long serialVersionUID = -9123172419588603273L;

  private final Predicate<T> predicate;

  private final MessageSourceResolvable message;

  public DefaultValidator(Predicate<T> predicate, MessageSourceResolvable message) {
    super();
    this.predicate = predicate;
    this.message = message;
  }

  /*
   * (non-Javadoc)
   * @see com.holonplatform.core.Validator#validate(java.lang.Object)
   */
  @Override
  public void validate(T value) throws ValidationException {
    if (!predicate.test(value)) {
      throw new ValidationException(message);
    }
  }
}
