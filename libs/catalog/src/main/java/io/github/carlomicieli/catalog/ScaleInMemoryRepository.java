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

import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.slug.Slug;
import jakarta.inject.Singleton;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class ScaleInMemoryRepository implements ScaleRepository {
  private final List<Scale> scales = scales().collect(Collectors.toCollection(ArrayList::new));

  @Override
  public @NotNull ScaleId save(@NotNull final Scale scale) {
    scales.add(scale);
    return scale.id();
  }

  @Override
  public @NotNull Optional<Scale> findById(@NotNull final ScaleId id) {
    return scales.stream().filter(s -> s.id().equals(id)).findFirst();
  }

  @Override
  public @NotNull List<Scale> findAll() {
    return List.copyOf(scales);
  }

  private Stream<Scale> scales() {
    return Stream.of(
        ScaleBuilder.builder()
            .id(ScaleId.fromName("1"))
            .name("1")
            .slug(Slug.of("1"))
            .ratio(BigDecimal.valueOf(32))
            .trackGauge(TrackGauge.STANDARD)
            .metadata(Metadata.createdAt(ZonedDateTime.parse("2024-05-22T17:20:38.935152086Z")))
            .build(),
        ScaleBuilder.builder()
            .id(ScaleId.fromName("0"))
            .name("0")
            .slug(Slug.of("0"))
            .ratio(BigDecimal.valueOf(43.5))
            .trackGauge(TrackGauge.STANDARD)
            .metadata(Metadata.createdAt(ZonedDateTime.parse("2024-05-22T17:20:38.935152086Z")))
            .build(),
        ScaleBuilder.builder()
            .id(ScaleId.fromName("H0"))
            .name("H0")
            .slug(Slug.of("H0"))
            .ratio(BigDecimal.valueOf(87))
            .trackGauge(TrackGauge.STANDARD)
            .metadata(Metadata.createdAt(ZonedDateTime.parse("2024-05-22T17:20:38.935152086Z")))
            .build(),
        ScaleBuilder.builder()
            .id(ScaleId.fromName("H0m"))
            .name("H0m")
            .slug(Slug.of("H0m"))
            .ratio(BigDecimal.valueOf(87))
            .trackGauge(TrackGauge.NARROW)
            .metadata(Metadata.createdAt(ZonedDateTime.parse("2024-05-22T17:20:38.935152086Z")))
            .build(),
        ScaleBuilder.builder()
            .id(ScaleId.fromName("N"))
            .name("N")
            .slug(Slug.of("N"))
            .ratio(BigDecimal.valueOf(160))
            .trackGauge(TrackGauge.STANDARD)
            .metadata(Metadata.createdAt(ZonedDateTime.parse("2024-05-22T17:20:38.935152086Z")))
            .build());
  }
}
