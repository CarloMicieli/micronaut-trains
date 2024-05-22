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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("BrandId")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BrandIdTest {
  @Test
  void it_should_not_allow_null_values() {
    assertThatThrownBy(() -> new BrandId(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("The brand ID value cannot be null");
  }

  @Test
  void it_should_be_a_valid_TRN() {
    assertThatThrownBy(() -> new BrandId("123"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid brand ID value: 123");
  }

  @Test
  void it_should_be_a_valid_TRN_with_the_correct_namespace_identifier() {
    assertThatThrownBy(() -> new BrandId("trn:something-else:name"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid brand ID value: trn:something-else:name");
  }

  @Test
  void it_should_produce_String_representation() {
    BrandId brandId = new BrandId("trn:brand:123");
    assertThat(brandId.toString()).isEqualTo("trn:brand:123");
  }
}
