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
package io.github.carlomicieli.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Currency;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Epoch")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EpochTest {
  @Test
  void it_should_produce_a_list_of_all_epochs() {
    Currency.getAvailableCurrencies();
    var epochs = Epoch.ALL;
    assertThat(epochs)
        .hasSize(15)
        .containsExactly(
            Epoch.I,
            Epoch.II,
            Epoch.IIa,
            Epoch.IIb,
            Epoch.III,
            Epoch.IIIa,
            Epoch.IIIb,
            Epoch.IV,
            Epoch.IVa,
            Epoch.IVb,
            Epoch.V,
            Epoch.Va,
            Epoch.Vb,
            Epoch.Vm,
            Epoch.VI);
  }

  @ParameterizedTest
  @MethodSource("parseSource")
  void it_should_parse_string_values(String value, Epoch expected) {
    var epoch = Epoch.parse(value);
    assertThat(epoch).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("parseSource")
  void it_should_produce_string_representation(String value, Epoch epoch) {
    assertThat(epoch.toString()).isEqualTo(value);
  }

  private static Stream<Arguments> parseSource() {
    return Stream.of(
        Arguments.of("I", Epoch.I),
        Arguments.of("II", Epoch.II),
        Arguments.of("IIa", Epoch.IIa),
        Arguments.of("IIb", Epoch.IIb),
        Arguments.of("III", Epoch.III),
        Arguments.of("IIIa", Epoch.IIIa),
        Arguments.of("IIIb", Epoch.IIIb),
        Arguments.of("IV", Epoch.IV),
        Arguments.of("IVa", Epoch.IVa),
        Arguments.of("IVb", Epoch.IVb),
        Arguments.of("IV/V", new Epoch.Multiple(Epoch.IV, Epoch.V)),
        Arguments.of("V", Epoch.V),
        Arguments.of("Va", Epoch.Va),
        Arguments.of("Vb", Epoch.Vb),
        Arguments.of("Vm", Epoch.Vm),
        Arguments.of("V/VI", new Epoch.Multiple(Epoch.V, Epoch.VI)),
        Arguments.of("VI", Epoch.VI));
  }
}
