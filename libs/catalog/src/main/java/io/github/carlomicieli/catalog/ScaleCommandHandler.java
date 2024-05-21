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

import io.github.carlomicieli.slug.Slug;
import jakarta.inject.Singleton;
import java.math.BigDecimal;
import java.util.Objects;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ScaleCommandHandler {
  private final ScaleRepository scaleRepository;

  public ScaleCommandHandler(final ScaleRepository scaleRepository) {
    this.scaleRepository =
        Objects.requireNonNull(scaleRepository, "scaleRepository must not be null");
  }

  @CheckReturnValue
  @SuppressWarnings("unchecked")
  public <R> @NotNull R handle(@NotNull final ScaleCommand<R> command) {
    switch (command) {
      case ScaleCommand.CreateScale createScale -> {
        Scale scale =
            ScaleBuilder.builder()
                .id("7")
                .name(createScale.name())
                .slug(Slug.of(createScale.name()))
                .ratio(BigDecimal.valueOf(createScale.ratio()))
                .trackGauge(TrackGauge.valueOf(createScale.trackGauge()))
                .build();
        return (R) scaleRepository.save(scale);
      }
      case ScaleCommand.FindScaleById findScaleById -> {
        return (R) scaleRepository.findById(findScaleById.id());
      }
      case ScaleCommand.FindAllScales ignored -> {
        return (R) scaleRepository.findAll();
      }
      case null, default -> throw new IllegalArgumentException("Unknown command: " + command);
    }
  }
}
