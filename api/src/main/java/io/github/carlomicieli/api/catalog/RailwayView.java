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

import io.github.carlomicieli.catalog.Railway;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

@Serdeable
@RecordBuilder
public record RailwayView(
    @NotNull String id,
    @NotNull String name,
    @NotNull String slug,
    @NotNull String abbreviation,
    @NotNull String country) {
  @CheckReturnValue
  public static @NotNull RailwayView fromRailway(@NotNull final Railway railway) {
    return RailwayViewBuilder.builder()
        .id(railway.id())
        .name(railway.name())
        .slug(railway.slug().value())
        .abbreviation(railway.abbreviation())
        .country(railway.country())
        .build();
  }
}
