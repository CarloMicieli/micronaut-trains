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

import java.time.LocalDate;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface RailwayPeriodOfActivity {
  @CheckReturnValue
  @NotNull RailwayStatus status();

  @CheckReturnValue
  @NotNull LocalDate operatingSince();

  @CheckReturnValue
  @Nullable LocalDate operatingUntil();

  /**
   * Creates the period of activity for an active railway
   *
   * @param operatingSince the date when the railway started its operation
   * @return a railway period of activity
   */
  @CheckReturnValue
  static @NotNull RailwayPeriodOfActivity activeRailway(@NotNull final LocalDate operatingSince) {
    return new ActiveRailway(operatingSince);
  }

  /**
   * Creates the period of activity for an inactive railway
   *
   * @param operatingSince the date when the railway started its operation
   * @param operatingUntil the date when the railway ended its operation, if not active anymore
   * @return a railway period of activity
   */
  @CheckReturnValue
  static @NotNull RailwayPeriodOfActivity inactiveRailway(
      @NotNull final LocalDate operatingSince, @NotNull final LocalDate operatingUntil) {
    return new InactiveRailway(operatingSince, operatingUntil);
  }

  record ActiveRailway(@NotNull LocalDate operatingSince) implements RailwayPeriodOfActivity {
    @Override
    public @NotNull RailwayStatus status() {
      return RailwayStatus.ACTIVE;
    }

    @Override
    public @Nullable LocalDate operatingUntil() {
      return null;
    }
  }

  record InactiveRailway(@NotNull LocalDate operatingSince, @NotNull LocalDate operatingUntil)
      implements RailwayPeriodOfActivity {
    @Override
    public @NotNull RailwayStatus status() {
      return RailwayStatus.INACTIVE;
    }
  }
}
