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
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("CatalogItem")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CatalogItemTest {
  private static final CatalogItemId ID = CatalogItemId.from("Marklin", "123456");
  private static final BrandId BRAND_ID = BrandId.fromName("Marklin");
  private static final ScaleId SCALE_ID = ScaleId.fromName("H0");
  private static final Epoch EPOCH = Epoch.III;
  private static final CatalogItemCategory CATEGORY = CatalogItemCategory.LOCOMOTIVES;
  private static final ItemNumber ITEM_NUMBER = new ItemNumber("123456");
  private static final PowerMethod POWER_METHOD = PowerMethod.AC;

  @Test
  void it_should_create_a_new_catalog_item() {
    var item =
        CatalogItemBuilder.builder()
            .id(ID)
            .category(CATEGORY)
            .brandId(BRAND_ID)
            .scaleId(SCALE_ID)
            .itemNumber(ITEM_NUMBER)
            .epoch(EPOCH)
            .powerMethod(POWER_METHOD)
            .count(1)
            .metadata(Metadata.createdAt(TestConstants.DATE_TIME_NOW))
            .build();

    assertThat(item).isNotNull();
    assertThat(item.id()).isEqualTo(ID);
    assertThat(item.brandId()).isEqualTo(BRAND_ID);
    assertThat(item.scaleId()).isEqualTo(SCALE_ID);
    assertThat(item.itemNumber()).isEqualTo(ITEM_NUMBER);
    assertThat(item.epoch()).isEqualTo(EPOCH);
    assertThat(item.category()).isEqualTo(CATEGORY);
    assertThat(item.powerMethod()).isEqualTo(POWER_METHOD);
    assertThat(item.count()).isEqualTo(1);
    assertThat(item.metadata().createdAt()).isEqualTo(TestConstants.DATE_TIME_NOW);
  }

  @Test
  void it_should_require_a_catalog_item_id() {
    assertThatNullPointerException()
        .isThrownBy(
            () ->
                CatalogItemBuilder.builder()
                    .category(CATEGORY)
                    .brandId(BRAND_ID)
                    .scaleId(SCALE_ID)
                    .itemNumber(ITEM_NUMBER)
                    .epoch(EPOCH)
                    .powerMethod(POWER_METHOD)
                    .metadata(Metadata.createdAt(TestConstants.DATE_TIME_NOW))
                    .build())
        .withMessage("Catalog item id cannot be null");
  }

  @Test
  void it_should_require_a_category() {
    assertThatNullPointerException()
        .isThrownBy(
            () ->
                CatalogItemBuilder.builder()
                    .id(ID)
                    .brandId(BRAND_ID)
                    .scaleId(SCALE_ID)
                    .itemNumber(ITEM_NUMBER)
                    .epoch(EPOCH)
                    .powerMethod(POWER_METHOD)
                    .metadata(Metadata.createdAt(TestConstants.DATE_TIME_NOW))
                    .build())
        .withMessage("Category cannot be null");
  }

  @Test
  void it_should_require_a_brand() {
    assertThatNullPointerException()
        .isThrownBy(
            () ->
                CatalogItemBuilder.builder()
                    .id(ID)
                    .category(CATEGORY)
                    .scaleId(SCALE_ID)
                    .itemNumber(ITEM_NUMBER)
                    .epoch(EPOCH)
                    .powerMethod(POWER_METHOD)
                    .metadata(Metadata.createdAt(TestConstants.DATE_TIME_NOW))
                    .build())
        .withMessage("Brand id cannot be null");
  }

  @Test
  void it_should_require_a_scale() {
    assertThatNullPointerException()
        .isThrownBy(
            () ->
                CatalogItemBuilder.builder()
                    .id(ID)
                    .category(CATEGORY)
                    .brandId(BRAND_ID)
                    .itemNumber(ITEM_NUMBER)
                    .epoch(EPOCH)
                    .powerMethod(POWER_METHOD)
                    .metadata(Metadata.createdAt(TestConstants.DATE_TIME_NOW))
                    .build())
        .withMessage("Scale id cannot be null");
  }

  @Test
  void it_should_require_an_epoch() {
    assertThatNullPointerException()
        .isThrownBy(
            () ->
                CatalogItemBuilder.builder()
                    .id(ID)
                    .category(CATEGORY)
                    .brandId(BRAND_ID)
                    .scaleId(SCALE_ID)
                    .itemNumber(ITEM_NUMBER)
                    .powerMethod(POWER_METHOD)
                    .metadata(Metadata.createdAt(TestConstants.DATE_TIME_NOW))
                    .build())
        .withMessage("Epoch cannot be null");
  }
}
