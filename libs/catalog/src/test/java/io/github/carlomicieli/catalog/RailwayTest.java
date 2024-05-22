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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.slug.Slug;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Railway")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayTest {
  @Test
  void it_should_require_an_id() {
    assertThatThrownBy(
            () ->
                RailwayBuilder.builder()
                    .name("FS")
                    .abbreviation("FS")
                    .country(CountryCode.IT)
                    .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessage("The railway id cannot be null");
  }

  @Test
  void it_should_require_a_name() {
    assertThatThrownBy(
            () ->
                RailwayBuilder.builder()
                    .id(RailwayId.fromName("FS"))
                    .abbreviation("FS")
                    .country(CountryCode.IT)
                    .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessage("The railway name cannot be null");
  }

  @Test
  void it_should_require_a_slug() {
    assertThatThrownBy(
            () ->
                RailwayBuilder.builder()
                    .id(RailwayId.fromName("FS"))
                    .name("FS")
                    .abbreviation("FS")
                    .country(CountryCode.IT)
                    .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessage("The railway slug cannot be null");
  }

  @Test
  void it_should_require_an_abbreviation() {
    assertThatThrownBy(
            () ->
                RailwayBuilder.builder()
                    .id(RailwayId.fromName("FS"))
                    .name("FS")
                    .slug(Slug.of("FS"))
                    .country(CountryCode.IT)
                    .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessage("The railway abbreviation cannot be null");
  }

  @Test
  void it_should_require_a_country() {
    assertThatThrownBy(
            () ->
                RailwayBuilder.builder()
                    .id(RailwayId.fromName("FS"))
                    .name("FS")
                    .slug(Slug.of("FS"))
                    .abbreviation("FS")
                    .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessage("The railway country cannot be null");
  }
}
