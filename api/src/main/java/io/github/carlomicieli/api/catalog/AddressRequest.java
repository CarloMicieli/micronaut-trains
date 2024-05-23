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

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.Address;
import io.github.carlomicieli.AddressBuilder;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jetbrains.annotations.CheckReturnValue;

@Serdeable(naming = SnakeCaseStrategy.class)
@Introspected
@RecordBuilder
public record AddressRequest(
    @NotNull String country,
    @NotNull String city,
    @NotBlank String streetAddress,
    String extendedAddress,
    String region,
    String postalCode) {

  /**
   * Creates a new {@link Address} instance from this {@link AddressRequest} object.
   *
   * @return a new {@link Address} instance
   */
  @CheckReturnValue
  public @org.jetbrains.annotations.NotNull Address toAddress() {
    return AddressBuilder.builder()
        .country(CountryCode.getByAlpha2Code(this.country()))
        .city(this.city())
        .streetAddress(this.streetAddress())
        .extendedAddress(this.extendedAddress())
        .region(this.region())
        .postalCode(this.postalCode())
        .build();
  }
}
