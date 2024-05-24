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

import io.github.carlomicieli.Address;
import io.github.carlomicieli.catalog.Railway;
import io.github.carlomicieli.catalog.RailwayStatus;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serdeable
@RecordBuilder
public record RailwayView(
    @NotNull String id,
    @NotNull String name,
    @NotNull String slug,
    @NotNull String abbreviation,
    @NotNull String country,
    @Nullable String status,
    @Nullable AddressView address,
    @Nullable String organizationEntityType,
    @NotNull MetadataView metadata) {
  @CheckReturnValue
  public static @NotNull RailwayView fromRailway(@NotNull final Railway railway) {
    var organizationEntityType =
        railway.organizationEntityType() != null ? railway.organizationEntityType().name() : null;
    return RailwayViewBuilder.builder()
        .id(railway.id().value())
        .name(railway.name())
        .slug(railway.slug().value())
        .abbreviation(railway.abbreviation())
        .country(railway.country().getAlpha2())
        .status(toRailwayStatus(railway.status()))
        .organizationEntityType(organizationEntityType)
        .address(toAddress(railway.address()))
        .metadata(MetadataView.fromMetadata(railway.metadata()))
        .build();
  }

  private static @Nullable String toRailwayStatus(@Nullable final RailwayStatus status) {
    return status != null ? status.name() : null;
  }

  private static @Nullable AddressView toAddress(@Nullable final Address address) {
    return address != null ? AddressView.fromAddress(address) : null;
  }
}
