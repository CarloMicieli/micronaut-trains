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

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.slug.Slug;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

@Singleton
public class RailwayInMemoryRepository implements RailwayRepository {
  private final List<Railway> railways =
      initRailways().collect(Collectors.toCollection(ArrayList::new));

  @Override
  public @NotNull RailwayId save(@NotNull Railway railway) {
    railways.add(railway);
    return railway.id();
  }

  @Override
  public Optional<Railway> findById(@NotNull RailwayId id) {
    return railways().filter(railway -> railway.id().equals(id)).findAny();
  }

  @Override
  public List<Railway> findAll() {
    return List.copyOf(railways);
  }

  private Stream<Railway> railways() {
    return railways.stream();
  }

  private Stream<Railway> initRailways() {
    return Stream.of(
        RailwayBuilder.builder()
            .id(RailwayId.fromName("FS"))
            .name("FS")
            .slug(Slug.of("fs"))
            .abbreviation("fs")
            .country(CountryCode.IT)
            .build(),
        RailwayBuilder.builder()
            .id(RailwayId.fromName("DB"))
            .name("DB")
            .slug(Slug.of("db"))
            .abbreviation("db")
            .country(CountryCode.DE)
            .build(),
        RailwayBuilder.builder()
            .id(RailwayId.fromName("SNCF"))
            .name("SNCF")
            .slug(Slug.of("sncf"))
            .abbreviation("sncf")
            .country(CountryCode.FR)
            .build(),
        RailwayBuilder.builder()
            .id(RailwayId.fromName("RENFE"))
            .name("RENFE")
            .slug(Slug.of("renfe"))
            .abbreviation("renfe")
            .country(CountryCode.ES)
            .build(),
        RailwayBuilder.builder()
            .id(RailwayId.fromName("NS"))
            .name("NS")
            .slug(Slug.of("ns"))
            .abbreviation("ns")
            .country(CountryCode.NL)
            .build(),
        RailwayBuilder.builder()
            .id(RailwayId.fromName("SBB"))
            .name("SBB")
            .slug(Slug.of("sbb"))
            .abbreviation("sbb")
            .country(CountryCode.CH)
            .build());
  }
}
