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

/** A repository for {@link Brand} entities. */
public interface BrandRepository {
  /**
   * Finds all the brands.
   *
   * @return a list of all brands
   */
  @CheckReturnValue
  @NotNull List<Brand> findAll();

  /**
   * Finds a brand by its id.
   *
   * @param brandId the brand id
   * @return an optional brand
   */
  @CheckReturnValue
  @NotNull Optional<Brand> findById(@NotNull final BrandId brandId);

  /**
   * Saves a new brand.
   *
   * @param brand the brand to save
   * @return the brand id
   */
  @CheckReturnValue
  @NotNull BrandId save(@NotNull final Brand brand);

  BrandRepository INSTANCE = new BrandInMemoryRepository();
}
