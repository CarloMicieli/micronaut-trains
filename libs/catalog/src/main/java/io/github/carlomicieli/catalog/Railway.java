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

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.Address;
import io.github.carlomicieli.ContactInfo;
import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.OrganizationEntityType;
import io.github.carlomicieli.slug.Slug;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * It represents a railway company.
 *
 * @param id the unique identifier
 * @param name the name of the railway company
 * @param slug the slug of the railway company
 * @param abbreviation the abbreviation of the railway company
 * @param country the country where the railway company operates
 * @param periodOfActivity the railway period of activity
 * @param address the address of the railway company
 * @param organizationEntityType the organization entity type
 * @param contactInfo the contact information
 * @param metadata the metadata information
 */
@RecordBuilder
public record Railway(
    @NotNull RailwayId id,
    @NotNull String name,
    @NotNull Slug slug,
    @NotNull String abbreviation,
    @NotNull CountryCode country,
    @Nullable RailwayPeriodOfActivity periodOfActivity,
    @Nullable Address address,
    @Nullable OrganizationEntityType organizationEntityType,
    @Nullable ContactInfo contactInfo,
    @NotNull Metadata metadata) {
  public Railway {
    Objects.requireNonNull(id, "The railway id cannot be null");
    Objects.requireNonNull(name, "The railway name cannot be null");
    Objects.requireNonNull(abbreviation, "The railway abbreviation cannot be null");
    Objects.requireNonNull(country, "The railway country cannot be null");
    Objects.requireNonNull(slug, "The railway slug cannot be null");
    Objects.requireNonNull(metadata, "The railway metadata cannot be null");
  }
}
