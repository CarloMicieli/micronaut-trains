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

import io.github.carlomicieli.catalog.Brand;
import io.github.carlomicieli.catalog.BrandKind;
import io.github.carlomicieli.slug.Slug;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("BrandView")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BrandViewTest {
  @Test
  void it_should_convert_brands_to_brand_views() {
    Brand brand = new Brand("1", "Brand 1", Slug.of("Brand 1"), BrandKind.INDUSTRIAL);
    BrandView brandView = BrandView.fromBrand(brand);
    assertThat(brandView.id()).isEqualTo("1");
    assertThat(brandView.name()).isEqualTo("Brand 1");
    assertThat(brandView.slug()).isEqualTo("brand-1");
    assertThat(brandView.kind()).isEqualTo("INDUSTRIAL");
  }

  @Test
  void it_should_default_to_industrial_kind() {
    Brand brand = new Brand("1", "Brand 1", Slug.of("Brand 1"), null);
    BrandView brandView = BrandView.fromBrand(brand);
    assertThat(brandView.kind()).isEqualTo("INDUSTRIAL");
  }
}
