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
import io.github.carlomicieli.ContactInfo;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface BrandCommand<R> {
  /**
   * The command to create a new brand.
   *
   * @param name the brand name
   * @param kind the brand kind
   * @param status the brand status
   * @param address the brand address
   * @param organizationEntityType the organization entity type
   * @param contactInfo the contact information
   */
  record CreateBrand(
      @NotNull String name,
      @Nullable String kind,
      @Nullable String status,
      @Nullable Address address,
      @Nullable String organizationEntityType,
      @Nullable ContactInfo contactInfo)
      implements BrandCommand<BrandId> {}

  /**
   * The command to find an existing brand by its id.
   *
   * @param brandId the brand id
   */
  record FindBrandById(@NotNull BrandId brandId) implements BrandCommand<Optional<Brand>> {}

  /** The command to find all brands. */
  record FindAllBrands() implements BrandCommand<List<Brand>> {}
}
