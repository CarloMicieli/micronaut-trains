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

import static org.assertj.core.api.Assertions.assertThat;

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.Address;
import io.github.carlomicieli.ContactInfo;
import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.OrganizationEntityType;
import io.github.carlomicieli.TestConstants;
import io.github.carlomicieli.slug.Slug;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Brands Command Handler")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BrandCommandHandlerTest {

  private final BrandRepository brandRepository = BrandRepository.INSTANCE;
  private final BrandCommandHandler commandHandler =
      new BrandCommandHandler(brandRepository, TestConstants.TEST_CLOCK);

  @Test
  void it_should_create_a_new_brand() {
    Address address = new Address(CountryCode.IT, "Rome", "Via Roma", null, "RM", "00100");
    ContactInfo contactInfo =
        new ContactInfo("mail@mail.com", "+391234567890", URI.create("http://www.example.com"));
    BrandCommand.CreateBrand createBrand =
        new BrandCommand.CreateBrand(
            "New Brand", "INDUSTRIAL", "ACTIVE", address, "LIMITED_COMPANY", contactInfo);
    BrandId brandId = commandHandler.handle(createBrand);
    assertThat(brandId).isNotNull().isEqualTo(new BrandId("trn:brand:new-brand"));

    Optional<Brand> newBrand = brandRepository.findById(BrandId.fromName("new-brand"));
    assertThat(newBrand).isNotEmpty();
    assertThat(newBrand.get().name()).isEqualTo("New Brand");
    assertThat(newBrand.get().slug()).isEqualTo(Slug.of("new-brand"));
    assertThat(newBrand.get().kind()).isEqualTo(BrandKind.INDUSTRIAL);
    assertThat(newBrand.get().status()).isEqualTo(BrandStatus.ACTIVE);
    assertThat(newBrand.get().address()).isEqualTo(address);
    assertThat(newBrand.get().metadata())
        .isEqualTo(Metadata.createdAt(TestConstants.DATE_TIME_NOW));
    assertThat(newBrand.get().organizationEntityType())
        .isEqualTo(OrganizationEntityType.LIMITED_COMPANY);
    assertThat(newBrand.get().contactInfo()).isEqualTo(contactInfo);
  }

  @Test
  void it_should_find_a_brand_by_id() {
    Brand expected =
        BrandBuilder.builder()
            .id(BrandId.fromName("Brand 1"))
            .name("Brand 1")
            .slug(Slug.of("brand-1"))
            .kind(BrandKind.INDUSTRIAL)
            .metadata(Metadata.createdAt(TestConstants.DATE_TIME_NOW))
            .build();
    BrandCommand.FindBrandById findBrandById =
        new BrandCommand.FindBrandById(BrandId.fromName("brand-1"));
    Optional<Brand> brand = commandHandler.handle(findBrandById);
    assertThat(brand).isNotEmpty().contains(expected);
  }

  @Test
  void it_should_find_all_brands() {
    BrandCommand.FindAllBrands findAllBrands = new BrandCommand.FindAllBrands();
    List<Brand> brands = commandHandler.handle(findAllBrands);
    assertThat(brands).isNotNull().hasSize(7);
  }
}
