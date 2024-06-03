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

import io.github.carlomicieli.catalog.RailwayPeriodOfActivity;
import io.github.carlomicieli.catalog.RailwayStatus;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Nullable;

@RecordBuilder
@Serdeable
public record RailwayPeriodOfActivityView(
    @NotNull String status,
    @Nullable LocalDate operatingSince,
    @Nullable LocalDate operatingUntil) {

  @CheckReturnValue
  public static @NotNull RailwayPeriodOfActivityView fromRailwayPeriodOfActivity(
      @NotNull RailwayPeriodOfActivity periodOfActivity) {
    var operatingSince = periodOfActivity.operatingSince();
    var operatingUntil = periodOfActivity.operatingUntil();
    return RailwayPeriodOfActivityViewBuilder.builder()
        .status(toRailwayStatus(periodOfActivity.status()))
        .operatingSince(operatingSince)
        .operatingUntil(operatingUntil)
        .build();
  }

  private static @Nullable String toRailwayStatus(@Nullable final RailwayStatus status) {
    return status != null ? status.name() : null;
  }
}