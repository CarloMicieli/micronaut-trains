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
package io.github.carlomicieli.api.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.carlomicieli.catalog.Scale;
import io.github.carlomicieli.catalog.ScaleBuilder;
import io.github.carlomicieli.catalog.TrackGauge;
import io.github.carlomicieli.slug.Slug;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("ScaleView")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScaleViewTest {
  @Test
  void it_should_create_a_new_scale_view() {
    Scale scale =
        ScaleBuilder.builder()
            .id("1")
            .name("H0")
            .slug(Slug.of("H0"))
            .ratio(BigDecimal.valueOf(87))
            .trackGauge(TrackGauge.STANDARD)
            .build();
    ScaleView scaleView = ScaleView.fromScale(scale);
    assertThat(scaleView).isNotNull();
    assertThat(scaleView.id()).isEqualTo("1");
    assertThat(scaleView.name()).isEqualTo("H0");
    assertThat(scaleView.slug()).isEqualTo("h0");
    assertThat(scaleView.ratio()).isEqualTo("1:87");
    assertThat(scaleView.trackGauge()).isEqualTo("STANDARD");
  }
}
