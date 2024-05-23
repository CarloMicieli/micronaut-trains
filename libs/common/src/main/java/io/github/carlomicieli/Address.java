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
package io.github.carlomicieli;

import com.neovisionaries.i18n.CountryCode;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * It represents an address of a location
 *
 * @param country the ISO country code (ISO 3166-1 alpha-2)
 * @param city the city/town
 * @param streetAddress the street address
 * @param extendedAddress the (optional) extended information for the address
 * @param region the (optional) region code; for example, the state or province
 * @param postalCode the (optional) postal code (ZIP code)
 */
@RecordBuilder
public record Address(
    @NotNull CountryCode country,
    @NotNull String city,
    @NotNull String streetAddress,
    String extendedAddress,
    String region,
    String postalCode) {

  public Address {
    Objects.requireNonNull(streetAddress, "Street address cannot be null");
    Objects.requireNonNull(city, "City cannot be null");
    Objects.requireNonNull(country, "Country cannot be null");
  }
}
