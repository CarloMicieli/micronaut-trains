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
import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.slug.Slug;
import jakarta.inject.Singleton;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public final class RailwayCommandHandler {
  private static final Logger LOG = LoggerFactory.getLogger(RailwayCommandHandler.class);
  private final RailwayRepository railwayRepository;
  private final Clock clock;

  public RailwayCommandHandler(
      @NotNull final RailwayRepository railwayRepository,
      @SuppressWarnings("MnInjectionPoints") @NotNull final Clock clock) {
    this.railwayRepository =
        Objects.requireNonNull(railwayRepository, "The railway repository cannot be null");
    this.clock = Objects.requireNonNull(clock, "The clock cannot be null");
  }

  @SuppressWarnings("unchecked")
  @CheckReturnValue
  public <R> @NotNull R handle(@NotNull RailwayCommand<R> command) {
    return switch (command) {
      case RailwayCommand.CreateRailway createRailway -> {
        var railway =
            RailwayBuilder.builder()
                .id(RailwayId.fromName(createRailway.name()))
                .name(createRailway.name())
                .slug(Slug.of(createRailway.name()))
                .abbreviation(createRailway.abbreviation())
                .country(CountryCode.getByCodeIgnoreCase(createRailway.country()))
                .status(statusFromString(createRailway.status()))
                .address(createRailway.address())
                .metadata(Metadata.createdAt(ZonedDateTime.now(clock)))
                .build();
        yield (R) railwayRepository.save(railway);
      }

      case RailwayCommand.FindRailwayById findRailwayById ->
          (R) railwayRepository.findById(findRailwayById.id());
      case RailwayCommand.FindAllRailways ignored -> (R) railwayRepository.findAll();
    };
  }

  private RailwayStatus statusFromString(@Nullable String status) {
    for (var value : RailwayStatus.values()) {
      if (value.name().equalsIgnoreCase(status)) {
        return value;
      }
    }

    LOG.warn("Unknown railway status: '{}'", status);
    return null;
  }
}
