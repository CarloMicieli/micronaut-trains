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

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.slug.Slug;
import jakarta.inject.Singleton;
import java.util.Objects;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Singleton
public final class RailwayCommandHandler {
  private final RailwayRepository railwayRepository;

  public RailwayCommandHandler(RailwayRepository railwayRepository) {
    this.railwayRepository =
        Objects.requireNonNull(railwayRepository, "The railway repository cannot be null");
  }

  @SuppressWarnings("unchecked")
  @CheckReturnValue
  public <R> @NotNull R handle(@NotNull RailwayCommand<R> command) {
    return switch (command) {
      case RailwayCommand.CreateRailway createRailway -> {
        var railway =
            RailwayBuilder.builder()
                .id("7")
                .name(createRailway.name())
                .slug(Slug.of(createRailway.name()))
                .abbreviation(createRailway.abbreviation())
                .country(CountryCode.getByCodeIgnoreCase(createRailway.country()))
                .status(toRailwayStatus(createRailway.status()))
                .build();
        yield (R) railwayRepository.save(railway);
      }

      case RailwayCommand.FindRailwayById findRailwayById ->
          (R) railwayRepository.findById(findRailwayById.id());
      case RailwayCommand.FindAllRailways ignored -> (R) railwayRepository.findAll();
    };
  }

  private RailwayStatus toRailwayStatus(@Nullable String status) {
    return switch (status) {
      case "active", "ACTIVE" -> RailwayStatus.ACTIVE;
      case "inactive", "INACTIVE" -> RailwayStatus.INACTIVE;
      case null, default -> null;
    };
  }
}
