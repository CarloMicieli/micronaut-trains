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

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("MeasureUnit")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MeasureUnitTest {
  @Test
  void it_should_return_the_symbol_for_the_measure_unit() {
    MeasureUnit unit = MeasureUnit.MILES;

    String symbol = unit.symbol();

    assertThat(symbol).isEqualTo("mi");
  }

  @ParameterizedTest
  @MethodSource("provideSymbols")
  void it_should_return_the_measure_unit_for_the_given_symbol(String symbol, MeasureUnit expected) {
    MeasureUnit unit = MeasureUnit.fromSymbol(symbol);

    assertThat(unit).isEqualTo(expected);
  }

  private static Stream<Arguments> provideSymbols() {
    return Stream.of(
        Arguments.of("mi", MeasureUnit.MILES),
        Arguments.of("MI", MeasureUnit.MILES),
        Arguments.of("mm", MeasureUnit.MILLIMETERS),
        Arguments.of("m", MeasureUnit.METERS),
        Arguments.of("M", MeasureUnit.METERS),
        Arguments.of("in", MeasureUnit.INCHES),
        Arguments.of("IN", MeasureUnit.INCHES),
        Arguments.of("km", MeasureUnit.KILOMETERS),
        Arguments.of("Km", MeasureUnit.KILOMETERS));
  }
}
