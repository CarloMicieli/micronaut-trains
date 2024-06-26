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

import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.slug.Slug;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Scale")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScaleTest {
  private static final BigDecimal EIGHTY_SEVEN = BigDecimal.valueOf(87);
  private static final ZonedDateTime NOW = ZonedDateTime.now();

  @Test
  void it_should_create_new_scales() {
    Scale scale =
        ScaleBuilder.builder()
            .id(ScaleId.fromName("H0"))
            .name("H0")
            .ratio(EIGHTY_SEVEN)
            .slug(Slug.of("H0"))
            .trackGauge(TrackGauge.STANDARD)
            .metadata(Metadata.createdAt(NOW))
            .build();
    assertThat(scale).isNotNull();
    assertThat(scale.id()).isEqualTo(ScaleId.fromName("H0"));
    assertThat(scale.ratio()).isEqualTo(EIGHTY_SEVEN);
    assertThat(scale.slug()).isEqualTo(Slug.of("H0"));
    assertThat(scale.name()).isEqualTo("H0");
    assertThat(scale.trackGauge()).isEqualTo(TrackGauge.STANDARD);
    assertThat(scale.metadata()).isNotNull();
    assertThat(scale.metadata().createdAt()).isEqualTo(NOW);
    assertThat(scale.metadata().lastModifiedAt()).isEqualTo(NOW);
    assertThat(scale.metadata().version()).isEqualTo(0);
  }

  @Test
  void it_should_require_a_name() {
    assertThatThrownBy(
            () ->
                ScaleBuilder.builder()
                    .id(ScaleId.fromName("H0"))
                    .ratio(EIGHTY_SEVEN)
                    .slug(Slug.of("H0"))
                    .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("Scale name cannot be null");
  }

  @Test
  void it_should_require_a_ratio() {
    assertThatThrownBy(
            () ->
                ScaleBuilder.builder()
                    .id(ScaleId.fromName("H0"))
                    .name("H0")
                    .slug(Slug.of("H0"))
                    .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("Scale ratio cannot be null");
  }

  @Test
  void it_should_require_metadata() {
    assertThatThrownBy(
            () ->
                ScaleBuilder.builder()
                    .id(ScaleId.fromName("H0"))
                    .name("H0")
                    .slug(Slug.of("H0"))
                    .ratio(EIGHTY_SEVEN)
                    .trackGauge(TrackGauge.STANDARD)
                    .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("Scale metadata cannot be null");
  }

  @Test
  void it_should_require_an_id() {
    assertThatThrownBy(
            () -> ScaleBuilder.builder().ratio(EIGHTY_SEVEN).name("H0").slug(Slug.of("H0")).build())
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("Scale id cannot be null");
  }

  @Test
  void it_should_produce_string_representations() {
    Scale halfZero =
        ScaleBuilder.builder()
            .id(ScaleId.fromName("H0"))
            .name("H0")
            .ratio(EIGHTY_SEVEN)
            .slug(Slug.of("H0"))
            .trackGauge(TrackGauge.STANDARD)
            .metadata(Metadata.createdAt(ZonedDateTime.now()))
            .build();
    assertThat(halfZero.toString()).isEqualTo("H0 (1:87)");

    Scale zero =
        ScaleBuilder.builder()
            .id(ScaleId.fromName("H0"))
            .name("0")
            .ratio(BigDecimal.valueOf(43.5))
            .slug(Slug.of("0"))
            .trackGauge(TrackGauge.STANDARD)
            .metadata(Metadata.createdAt(ZonedDateTime.now()))
            .build();
    assertThat(zero.toString()).isEqualTo("0 (1:43.5)");
  }
}
