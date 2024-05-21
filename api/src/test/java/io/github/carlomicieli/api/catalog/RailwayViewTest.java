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

import io.github.carlomicieli.catalog.Railway;
import io.github.carlomicieli.slug.Slug;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("RailwayView")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayViewTest {
  @Test
  void it_should_create_a_RailwayView_from_a_Railway() {
    Railway railway = new Railway("1", "FS", Slug.of("FS"), "fs", "Italy");
    RailwayView view = RailwayView.fromRailway(railway);
    assertThat(view).isNotNull();
    assertThat(view.id()).isEqualTo("1");
    assertThat(view.name()).isEqualTo("FS");
    assertThat(view.slug()).isEqualTo("fs");
    assertThat(view.abbreviation()).isEqualTo("fs");
    assertThat(view.country()).isEqualTo("Italy");
  }
}
