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

import static java.util.Objects.requireNonNull;

import io.github.carlomicieli.slug.Slug;
import jakarta.inject.Singleton;

@Singleton
public class BrandsCommandHandler {
  private final BrandRepository brandRepository;

  public BrandsCommandHandler(BrandRepository brandRepository) {
    this.brandRepository = requireNonNull(brandRepository, "brandRepository must not be null");
  }

  @SuppressWarnings("unchecked")
  public <R> R handle(final BrandCommand<R> command) {
    switch (command) {
      case BrandCommand.CreateBrand createBrand -> {
        Brand brand =
            BrandBuilder.builder()
                .id("7")
                .name(createBrand.name())
                .slug(Slug.of(createBrand.name()))
                .build();
        return (R) brandRepository.save(brand);
      }
      case BrandCommand.FindBrandById findBrandById -> {
        return (R) brandRepository.findById(findBrandById.brandId());
      }
      case BrandCommand.FindAllBrands ignored -> {
        return (R) brandRepository.findAll();
      }
      case null, default -> throw new IllegalArgumentException("Unknown command: " + command);
    }
  }
}
