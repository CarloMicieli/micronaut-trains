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

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.AddressBuilder;
import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.OrganizationEntityType;
import io.github.carlomicieli.slug.Slug;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Brands")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BrandTest {
  private static final ZonedDateTime NOW = ZonedDateTime.now();

  @Test
  void it_should_create_a_new_brand() {
    assertThatCode(
            () ->
                new Brand(
                    BrandId.fromName("Brand 1"),
                    "Brand 1",
                    Slug.of("brand-1"),
                    BrandKind.INDUSTRIAL,
                    BrandStatus.ACTIVE,
                    AddressBuilder.builder()
                        .city("Milan")
                        .country(CountryCode.IT)
                        .streetAddress("Via Roma, 1")
                        .postalCode("20100")
                        .region("MI")
                        .build(),
                    OrganizationEntityType.LIMITED_COMPANY,
                    Metadata.createdAt(NOW)))
        .doesNotThrowAnyException();
  }

  @Test
  void it_must_have_an_id() {
    assertThatThrownBy(
            () ->
                new Brand(
                    null,
                    "Brand 1",
                    Slug.of("brand-1"),
                    BrandKind.INDUSTRIAL,
                    BrandStatus.ACTIVE,
                    null,
                    null,
                    Metadata.createdAt(NOW)))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Brand id cannot be null");
  }

  @Test
  void it_must_have_a_name() {
    assertThatThrownBy(
            () ->
                new Brand(
                    BrandId.fromName("brand-1"),
                    null,
                    Slug.of("brand-1"),
                    BrandKind.INDUSTRIAL,
                    BrandStatus.ACTIVE,
                    null,
                    null,
                    Metadata.createdAt(NOW)))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Brand name cannot be null");
  }

  @Test
  void it_must_have_a_slug() {
    assertThatThrownBy(
            () ->
                new Brand(
                    BrandId.fromName("brand-1"),
                    "Brand 1",
                    null,
                    BrandKind.INDUSTRIAL,
                    BrandStatus.ACTIVE,
                    null,
                    null,
                    Metadata.createdAt(NOW)))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Brand slug cannot be null");
  }
}
