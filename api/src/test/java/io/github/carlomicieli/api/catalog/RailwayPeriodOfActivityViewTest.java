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

import static org.assertj.core.api.Assertions.assertThat;

import io.github.carlomicieli.catalog.RailwayPeriodOfActivity;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("RailwayPeriodOfActivityView")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayPeriodOfActivityViewTest {
  @Test
  void it_should_create_a_view_from_an_active_period_of_activity() {
    RailwayPeriodOfActivity periodOfActivity =
        RailwayPeriodOfActivity.activeRailway(LocalDate.of(1907, 7, 1));
    RailwayPeriodOfActivityView view =
        RailwayPeriodOfActivityView.fromRailwayPeriodOfActivity(periodOfActivity);
    assertThat(view).isNotNull();
    assertThat(view.status()).isEqualTo("ACTIVE");
    assertThat(view.operatingSince()).isEqualTo(LocalDate.of(1907, 7, 1));
    assertThat(view.operatingUntil()).isNull();
  }

  @Test
  void it_should_create_a_view_from_an_inactive_period_of_activity() {
    RailwayPeriodOfActivity periodOfActivity =
        RailwayPeriodOfActivity.inactiveRailway(
            LocalDate.of(1907, 7, 1), LocalDate.of(2001, 4, 11));
    RailwayPeriodOfActivityView view =
        RailwayPeriodOfActivityView.fromRailwayPeriodOfActivity(periodOfActivity);
    assertThat(view).isNotNull();
    assertThat(view.status()).isEqualTo("INACTIVE");
    assertThat(view.operatingSince()).isEqualTo(LocalDate.of(1907, 7, 1));
    assertThat(view.operatingUntil()).isEqualTo(LocalDate.of(2001, 4, 11));
  }
}
