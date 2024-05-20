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
package io.github.carlomicieli.util;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("BigDecimals")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BigDecimalsTest {
  @Test
  void it_should_not_throw_when_input_is_positive() {
    assertThatCode(() -> BigDecimals.requirePositive(BigDecimal.ONE)).doesNotThrowAnyException();
  }

  @Test
  void it_should_throw_IllegalArgumentException_when_the_input_is_zero() {
    assertThatThrownBy(() -> BigDecimals.requirePositive(BigDecimal.ZERO))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("input must be positive");
  }

  @Test
  void it_should_throw_IllegalArgumentException_when_the_input_is_negative() {
    assertThatThrownBy(() -> BigDecimals.requirePositive(BigDecimal.valueOf(-42)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("input must be positive");
  }
}
