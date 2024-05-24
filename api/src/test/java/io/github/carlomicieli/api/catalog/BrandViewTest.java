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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.Address;
import io.github.carlomicieli.AddressBuilder;
import io.github.carlomicieli.ContactInfo;
import io.github.carlomicieli.ContactInfoBuilder;
import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.OrganizationEntityType;
import io.github.carlomicieli.catalog.Brand;
import io.github.carlomicieli.catalog.BrandBuilder;
import io.github.carlomicieli.catalog.BrandId;
import io.github.carlomicieli.catalog.BrandKind;
import io.github.carlomicieli.catalog.BrandStatus;
import io.github.carlomicieli.slug.Slug;
import java.net.URI;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("BrandView")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BrandViewTest {
  private static final ZonedDateTime NOW = ZonedDateTime.now();

  @Test
  void it_should_convert_brands_to_brand_views() {
    Address address =
        AddressBuilder.builder()
            .streetAddress("Via Roma, 1")
            .city("Roma")
            .postalCode("00100")
            .country(CountryCode.IT)
            .build();
    ContactInfo contactInfo =
        ContactInfoBuilder.builder()
            .email("mail@mail.com")
            .phone("+39 06 12345678")
            .websiteUrl(URI.create("http://www.example.com"))
            .build();
    Brand brand =
        BrandBuilder.builder()
            .id(BrandId.fromName("Brand 1"))
            .name("Brand 1")
            .slug(Slug.of("Brand 1"))
            .kind(BrandKind.INDUSTRIAL)
            .status(BrandStatus.ACTIVE)
            .address(address)
            .organizationEntityType(OrganizationEntityType.LIMITED_COMPANY)
            .contactInfo(contactInfo)
            .metadata(Metadata.createdAt(NOW))
            .build();

    BrandView brandView = BrandView.fromBrand(brand);
    assertThat(brandView.id()).isEqualTo("trn:brand:brand-1");
    assertThat(brandView.name()).isEqualTo("Brand 1");
    assertThat(brandView.slug()).isEqualTo("brand-1");
    assertThat(brandView.kind()).isEqualTo("INDUSTRIAL");
    assertThat(brandView.status()).isEqualTo("ACTIVE");
    assertThat(brandView.address()).isNotNull();
    assertThat(brandView.address().streetAddress()).isEqualTo(address.streetAddress());
    assertThat(brandView.address().city()).isEqualTo(address.city());
    assertThat(brandView.address().postalCode()).isEqualTo(address.postalCode());
    assertThat(brandView.address().country()).isEqualTo(address.country().getAlpha2());
    assertThat(brandView.address().region()).isEqualTo(address.region());
    assertThat(brandView.address().extendedAddress()).isEqualTo(address.extendedAddress());
    assertThat(brandView.contactInfo()).isNotNull();
    assertThat(brandView.contactInfo().email()).isEqualTo(contactInfo.email());
    assertThat(brandView.contactInfo().phone()).isEqualTo(contactInfo.phone());
    assertThat(brandView.contactInfo().websiteUrl()).isEqualTo(contactInfo.websiteUrl());
    assertThat(brandView.metadata()).isNotNull();
    assertThat(brandView.metadata().createdAt()).isEqualTo(NOW);
    assertThat(brandView.metadata().lastModifiedAt()).isEqualTo(NOW);
    assertThat(brandView.metadata().version()).isEqualTo(0);
  }

  @Test
  void it_should_default_to_industrial_kind() {
    Brand brand =
        new Brand(
            BrandId.fromName("Brand 1"),
            "Brand 1",
            Slug.of("Brand 1"),
            null,
            null,
            null,
            null,
            null,
            Metadata.createdAt(ZonedDateTime.now()));
    BrandView brandView = BrandView.fromBrand(brand);
    assertThat(brandView.kind()).isEqualTo("INDUSTRIAL");
  }
}
