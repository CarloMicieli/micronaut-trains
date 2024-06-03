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
import io.github.carlomicieli.catalog.RailwayStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("RailwayPeriodOfActivityRequest")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayPeriodOfActivityRequestTest {
  @Test
  void it_should_create_the_period_of_activity_for_an_active_railway() {
    RailwayPeriodOfActivityRequest request =
        new RailwayPeriodOfActivityRequest("active", LocalDate.of(1907, 7, 1), null);
    RailwayPeriodOfActivity periodOfActivity = request.toRailwayPeriodOfActivity();
    assertThat(periodOfActivity).isNotNull();
    assertThat(periodOfActivity.status()).isEqualTo(RailwayStatus.ACTIVE);
    assertThat(periodOfActivity.operatingSince()).isEqualTo(LocalDate.of(1907, 7, 1));
  }

  @Test
  void it_should_create_the_period_of_activity_for_an_inactive_railway() {
    RailwayPeriodOfActivityRequest request =
        new RailwayPeriodOfActivityRequest(
            "inactive", LocalDate.of(1907, 7, 1), LocalDate.of(1990, 12, 31));
    RailwayPeriodOfActivity periodOfActivity = request.toRailwayPeriodOfActivity();
    assertThat(periodOfActivity).isNotNull();
    assertThat(periodOfActivity.status()).isEqualTo(RailwayStatus.INACTIVE);
    assertThat(periodOfActivity.operatingSince()).isEqualTo(LocalDate.of(1907, 7, 1));
    assertThat(periodOfActivity.operatingUntil()).isEqualTo(LocalDate.of(1990, 12, 31));
  }
}
