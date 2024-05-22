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
package io.github.carlomicieli.trn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("TRN")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TRNTest {
  @Test
  void it_should_create_a_new_TRN_value() {
    String value = "trn:namespace:namespace-specific-string";
    TRN trn = TRN.of(value);
    assertThat(trn).isNotNull();
    assertThat(trn.namespaceIdentifier()).isEqualTo("namespace");
    assertThat(trn.namespaceSpecificString()).isEqualTo("namespace-specific-string");
  }

  @ParameterizedTest
  @CsvSource({
    "\"\",false",
    "trn:,false",
    "trn:namespace,false",
    "trn:namespace:,false",
    "urn:namespace:namespace-specific-string,false",
    "trn:name space:namespace-specific-string,false",
    "trn:namespace:namespace specific string,false",
    "trn:namespace:namespace-specific-string,true",
  })
  void it_should_try_to_parse_TRN_values(final String value, final boolean isPresent) {
    Optional<TRN> trn = TRN.tryParse(value);
    assertThat(trn.isPresent()).isEqualTo(isPresent);
  }

  @Test
  void it_should_create_a_TRN_with_two_namespace_specific_strings() {
    TRN trn = TRN.of("trn:namespace:namespace-specific-string1:namespace-specific-string2");
    assertThat(trn).isNotNull();
    assertThat(trn.namespaceIdentifier()).isEqualTo("namespace");
    assertThat(trn.namespaceSpecificString())
        .isEqualTo("namespace-specific-string1:namespace-specific-string2");
    assertThat(trn.namespaceSpecificStrings())
        .containsExactly("namespace-specific-string1", "namespace-specific-string2");
  }

  @Test
  void it_should_produce_a_String_representation() {
    TRN trn = TRN.of("trn:namespace:namespace-specific-string");
    assertThat(trn.toString()).isEqualTo("trn:namespace:namespace-specific-string");
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "\"\"",
        "trn:",
        "trn:namespace",
        "trn:namespace:",
        "urn:namespace:namespace-specific-string",
        "trn:name space:namespace-specific-string",
        "trn:namespace:namespace specific string"
      })
  void it_should_fail_to_create_a_TRN_with_an_invalid_value(final String value) {
    assertThatThrownBy(() -> TRN.of(value))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid TRN value: " + value);
  }

  @ParameterizedTest
  @CsvSource({
    "\"\",false",
    "trn:,false",
    "trn:namespace,false",
    "trn:namespace:,false",
    "urn:namespace:namespace-specific-string,false",
    "trn:name space:namespace-specific-string,false",
    "trn:namespace:namespace specific string,false"
  })
  void it_should_check_TRN_validity(final String value, final boolean isValid) {
    assertThat(TRN.isValid(value)).isEqualTo(isValid);
  }
}
