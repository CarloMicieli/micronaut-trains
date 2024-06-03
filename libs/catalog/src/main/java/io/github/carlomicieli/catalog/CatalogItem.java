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

import io.github.carlomicieli.Metadata;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an item in the catalog.
 *
 * @param id the item identifier
 * @param brandId the brand name
 * @param scaleId the scale identifier
 * @param itemNumber the item number
 * @param epoch the epoch
 * @param category the item category
 * @param powerMethod the power method
 * @param count the item count
 * @param metadata the item metadata
 */
@RecordBuilder
public record CatalogItem(
    @NotNull CatalogItemId id,
    @NotNull BrandId brandId,
    @NotNull ScaleId scaleId,
    @NotNull ItemNumber itemNumber,
    @NotNull Epoch epoch,
    @NotNull CatalogItemCategory category,
    @NotNull PowerMethod powerMethod,
    @Nullable Integer count,
    @NotNull Metadata metadata)
    implements CatalogItemBuilder.With {
  public CatalogItem {
    Objects.requireNonNull(id, "Catalog item id cannot be null");
    Objects.requireNonNull(brandId, "Brand id cannot be null");
    Objects.requireNonNull(scaleId, "Scale id cannot be null");
    Objects.requireNonNull(itemNumber, "Item number cannot be null");
    Objects.requireNonNull(epoch, "Epoch cannot be null");
    Objects.requireNonNull(category, "Category cannot be null");
    Objects.requireNonNull(powerMethod, "Power method cannot be null");
    Objects.requireNonNull(metadata, "Metadata cannot be null");
  }
}
