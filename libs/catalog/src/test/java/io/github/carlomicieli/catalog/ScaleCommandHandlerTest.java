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

import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.TestConstants;
import io.github.carlomicieli.slug.Slug;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("ScaleCommandHandler")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScaleCommandHandlerTest {
  private final ScaleRepository scaleRepository = ScaleRepository.INSTANCE;
  private final ScaleCommandHandler scaleCommandHandler =
      new ScaleCommandHandler(scaleRepository, TestConstants.TEST_CLOCK);

  @Test
  void it_should_create_new_scales() {
    ScaleCommand.CreateScale createScale = new ScaleCommand.CreateScale("H0e", 87f, "NARROW");
    ScaleId scaleId = scaleCommandHandler.handle(createScale);
    assertThat(scaleId).isEqualTo(ScaleId.fromName("H0e"));

    Optional<Scale> scale = scaleRepository.findById(scaleId);
    assertThat(scale).isPresent();
    assertThat(scale.get().id()).isEqualTo(ScaleId.fromName("H0e"));
    assertThat(scale.get().name()).isEqualTo("H0e");
    assertThat(scale.get().slug()).isEqualTo(Slug.of("H0e"));
    assertThat(scale.get().ratio()).isEqualTo(BigDecimal.valueOf(87f));
    assertThat(scale.get().trackGauge()).isEqualTo(TrackGauge.NARROW);
    assertThat(scale.get().metadata()).isEqualTo(Metadata.createdAt(TestConstants.DATE_TIME_NOW));
  }

  @Test
  void it_should_find_scale_by_id() {
    ScaleCommand.FindScaleById findScaleById =
        new ScaleCommand.FindScaleById(ScaleId.fromName("1"));
    Optional<Scale> scale = scaleCommandHandler.handle(findScaleById);
    assertThat(scale).isPresent();
    assertThat(scale.get().id()).isEqualTo(ScaleId.fromName("1"));
  }

  @Test
  void it_should_find_scales() {
    ScaleCommand.FindAllScales findAllScales = new ScaleCommand.FindAllScales();
    List<Scale> scale = scaleCommandHandler.handle(findAllScales);
    assertThat(scale).isNotNull().hasSize(5);
  }
}
