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
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Introspected
@RecordBuilder
@Serdeable
public record RailwayPeriodOfActivityRequest(
    @NotBlank String status, LocalDate operatingSince, LocalDate operatingUntil) {

  private static final Logger LOG = LoggerFactory.getLogger(RailwayPeriodOfActivityRequest.class);

  @CheckReturnValue
  public RailwayPeriodOfActivity toRailwayPeriodOfActivity() {
    RailwayStatus status = statusFromString(this.status);
    if (RailwayStatus.ACTIVE.equals(status)) {
      return RailwayPeriodOfActivity.activeRailway(operatingSince);
    } else {
      return RailwayPeriodOfActivity.inactiveRailway(operatingSince, operatingUntil);
    }
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
