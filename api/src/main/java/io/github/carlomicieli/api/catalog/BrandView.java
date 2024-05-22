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

import io.github.carlomicieli.catalog.Brand;
import io.github.carlomicieli.catalog.BrandKind;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RecordBuilder
@Serdeable
public record BrandView(
    @NotNull String id,
    @NotNull String name,
    @NotNull String slug,
    @Nullable String kind,
    @Nullable String status,
    @NotNull MetadataView metadata) {
  public static @NotNull BrandView fromBrand(@NotNull final Brand brand) {
    var kind = Optional.ofNullable(brand.kind()).orElse(BrandKind.INDUSTRIAL).name();
    var status = Optional.ofNullable(brand.status()).map(Enum::name).orElse(null);
    return BrandViewBuilder.builder()
        .id(brand.id().value())
        .name(brand.name())
        .slug(brand.slug().toString())
        .status(status)
        .kind(kind)
        .metadata(MetadataView.fromMetadata(brand.metadata()))
        .build();
  }
}
