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

import io.github.carlomicieli.slug.Slug;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class BrandInMemoryRepository implements BrandRepository {
  @Override
  public @NotNull List<Brand> findAll() {
    return brands().toList();
  }

  @Override
  public @NotNull Optional<Brand> findById(final @NotNull String brandId) {
    return brands().filter(brand -> brand.id().equals(brandId)).findAny();
  }

  @Override
  public @NotNull String save(@NotNull final Brand brand) {
    return brand.id();
  }

  private Stream<Brand> brands() {
    return IntStream.range(1, 6)
        .boxed()
        .map(
            id ->
                BrandBuilder.builder()
                    .id(String.valueOf(id))
                    .name("Brand " + id)
                    .slug(Slug.of("brand-" + id))
                    .kind(BrandKind.INDUSTRIAL)
                    .build());
  }
}
