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

import io.github.carlomicieli.Address;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface RailwayCommand<R> {
  /**
   * Creates a new railway company.
   *
   * @param name the name of the railway company
   * @param abbreviation the abbreviation of the railway company
   * @param country the country where the railway company operates
   * @param status the activity status of the railway company
   * @param address the address of the railway company
   * @param organizationEntityType the organization entity type
   */
  record CreateRailway(
      @NotNull String name,
      @NotNull String abbreviation,
      @NotNull String country,
      @Nullable String status,
      @Nullable Address address,
      @Nullable String organizationEntityType)
      implements RailwayCommand<RailwayId> {}

  /**
   * The command to find a railway company by its unique identifier.
   *
   * @param id the unique identifier
   */
  record FindRailwayById(@NotNull RailwayId id) implements RailwayCommand<Optional<Railway>> {}

  /** The command to find all the railway companies. */
  record FindAllRailways() implements RailwayCommand<List<Railway>> {}
}
