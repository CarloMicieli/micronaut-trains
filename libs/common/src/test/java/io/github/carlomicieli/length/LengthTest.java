/*
 *   Copyright (c) 2024 (C) Carlo Micieli
 *
 *    Licensed to the Apache Software Foundation (ASF) under one
 *    or more contributor license agreements.  See the NOTICE file
 *    distributed with this work for additional information
 *    regarding copyright ownership.  The ASF licenses this file
 *    to you under the Apache License, Version 2.0 (the
 *    "License"); you may not use this file except in compliance
 *    with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing,
 *    software distributed under the License is distributed on an
 *    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *    KIND, either express or implied.  See the License for the
 *    specific language governing permissions and limitations
 *    under the License.
 */
package io.github.carlomicieli.length;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("Length")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LengthTest {
  @ParameterizedTest
  @EnumSource(MeasureUnit.class)
  void shouldCreateLengthValues(MeasureUnit measureUnit) {
    BigDecimal value = BigDecimal.valueOf(42);
    Length length = Length.of(value, measureUnit);
    assertThat(length).isNotNull();
    assertThat(length.measureUnit()).isEqualTo(measureUnit);
    assertThat(length.value()).isEqualTo(value);
  }

  @ParameterizedTest
  @EnumSource(MeasureUnit.class)
  void shouldThrowExceptionForNegativeValues(MeasureUnit measureUnit) {
    BigDecimal negativeValue = BigDecimal.valueOf(-42);
    assertThatThrownBy(() -> Length.of(negativeValue, measureUnit))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
