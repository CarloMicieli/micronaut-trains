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
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class ScaleInMemoryRepository implements ScaleRepository {
  @Override
  public @NotNull String save(@NotNull final Scale scale) {
    return scale.id();
  }

  @Override
  public @NotNull Optional<Scale> findById(@NotNull String id) {
    return scales().filter(s -> s.id().equals(id)).findFirst();
  }

  private Stream<Scale> scales() {
    return Stream.of(
        ScaleBuilder.builder()
            .id("1")
            .name("1")
            .slug(Slug.of("1"))
            .ratio(BigDecimal.valueOf(32))
            .build(),
        ScaleBuilder.builder()
            .id("0")
            .name("0")
            .slug(Slug.of("0"))
            .ratio(BigDecimal.valueOf(43.5))
            .build(),
        ScaleBuilder.builder()
            .id("H0")
            .name("H0")
            .slug(Slug.of("H0"))
            .ratio(BigDecimal.valueOf(87))
            .build(),
        ScaleBuilder.builder()
            .id("N")
            .name("N")
            .slug(Slug.of("N"))
            .ratio(BigDecimal.valueOf(160))
            .build());
  }
}
