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

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

/** A repository for {@link Scale} entities. */
public interface ScaleRepository {
  /**
   * Saves a new scale entity.
   *
   * @param scale the scale entity to save
   * @return the unique identifier of the saved entity
   */
  @CheckReturnValue
  @NotNull ScaleId save(@NotNull final Scale scale);

  /**
   * Finds a scale entity by its unique identifier.
   *
   * @param id the unique identifier
   * @return an {@link Optional} containing the scale entity, if found
   */
  @CheckReturnValue
  @NotNull Optional<Scale> findById(@NotNull final ScaleId id);

  /**
   * Finds all the scale entities.
   *
   * @return a list of all the scale entities
   */
  @CheckReturnValue
  @NotNull List<Scale> findAll();

  ScaleRepository INSTANCE = new ScaleInMemoryRepository();
}
