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

import static org.assertj.core.api.Assertions.assertThat;

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.Address;
import io.github.carlomicieli.AddressBuilder;
import io.github.carlomicieli.ContactInfo;
import io.github.carlomicieli.ContactInfoBuilder;
import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.OrganizationEntityType;
import io.github.carlomicieli.catalog.Railway;
import io.github.carlomicieli.catalog.RailwayBuilder;
import io.github.carlomicieli.catalog.RailwayId;
import io.github.carlomicieli.catalog.RailwayStatus;
import io.github.carlomicieli.slug.Slug;
import java.net.URI;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("RailwayView")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayViewTest {
  private static final ZonedDateTime NOW = ZonedDateTime.now();

  @Test
  void it_should_create_a_RailwayView_from_a_Railway() {
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
    Railway railway =
        RailwayBuilder.builder()
            .id(RailwayId.fromName("FS"))
            .name("FS")
            .slug(Slug.of("FS"))
            .abbreviation("fs")
            .country(CountryCode.IT)
            .organizationEntityType(OrganizationEntityType.LIMITED_COMPANY)
            .status(RailwayStatus.ACTIVE)
            .address(address)
            .contactInfo(contactInfo)
            .metadata(Metadata.createdAt(NOW))
            .build();
    RailwayView view = RailwayView.fromRailway(railway);
    assertThat(view).isNotNull();
    assertThat(view.id()).isEqualTo("trn:railway:fs");
    assertThat(view.name()).isEqualTo("FS");
    assertThat(view.slug()).isEqualTo("fs");
    assertThat(view.abbreviation()).isEqualTo("fs");
    assertThat(view.country()).isEqualTo(CountryCode.IT.getAlpha2());
    assertThat(view.status()).isEqualTo(RailwayStatus.ACTIVE.name());
    assertThat(view.organizationEntityType())
        .isEqualTo(OrganizationEntityType.LIMITED_COMPANY.name());
    assertThat(view.address()).isNotNull();
    assertThat(view.address().streetAddress()).isEqualTo(address.streetAddress());
    assertThat(view.address().city()).isEqualTo(address.city());
    assertThat(view.address().postalCode()).isEqualTo(address.postalCode());
    assertThat(view.address().country()).isEqualTo(address.country().getAlpha2());
    assertThat(view.address().region()).isEqualTo(address.region());
    assertThat(view.address().extendedAddress()).isEqualTo(address.extendedAddress());
    assertThat(view.contactInfo()).isNotNull();
    assertThat(view.contactInfo().email()).isEqualTo(contactInfo.email());
    assertThat(view.contactInfo().phone()).isEqualTo(contactInfo.phone());
    assertThat(view.contactInfo().websiteUrl()).isEqualTo(contactInfo.websiteUrl());
    assertThat(view.metadata()).isNotNull();
    assertThat(view.metadata().createdAt()).isEqualTo(NOW);
    assertThat(view.metadata().lastModifiedAt()).isEqualTo(NOW);
    assertThat(view.metadata().version()).isEqualTo(0);
  }
}
