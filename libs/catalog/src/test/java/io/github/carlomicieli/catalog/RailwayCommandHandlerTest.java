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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("RailwayCommandHandler")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayCommandHandlerTest {
  private final RailwayRepository railwayRepository = RailwayRepository.INSTANCE;
  private final RailwayCommandHandler railwayCommandHandler =
      new RailwayCommandHandler(railwayRepository);

  @Test
  void it_should_find_railway_by_id_when_it_exists() {
    RailwayCommand.FindRailwayById findRailwayById = new RailwayCommand.FindRailwayById("1");
    Optional<Railway> railway = railwayCommandHandler.handle(findRailwayById);
    assertThat(railway).isPresent();
    assertThat(railway.get().id()).isEqualTo("1");
    assertThat(railway.get().name()).isEqualTo("FS");
    assertThat(railway.get().abbreviation()).isEqualTo("fs");
    assertThat(railway.get().country()).isEqualTo("Italy");
    assertThat(railway.get().slug().value()).isEqualTo("fs");
  }

  @Test
  void it_should_find_railway_by_id() {
    RailwayCommand.FindRailwayById findRailwayById =
        new RailwayCommand.FindRailwayById("not-found");
    Optional<Railway> railway = railwayCommandHandler.handle(findRailwayById);
    assertThat(railway).isEmpty();
  }

  @Test
  void it_should_find_all_railways() {
    RailwayCommand.FindAllRailways findAllRailways = new RailwayCommand.FindAllRailways();
    var railways = railwayCommandHandler.handle(findAllRailways);
    assertThat(railways).isNotNull().hasSize(6);
  }

  @Test
  void it_should_create_a_new_railway() {
    RailwayCommand.CreateRailway createRailway =
        new RailwayCommand.CreateRailway("Trenitalia", "FS", "Italy");
    String id = railwayCommandHandler.handle(createRailway);
    assertThat(id).isNotNull().isEqualTo("7");
  }
}
