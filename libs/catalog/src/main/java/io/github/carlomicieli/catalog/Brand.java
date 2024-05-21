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
import io.soabase.recordbuilder.core.RecordBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A brand represents a manufacturer or a company that produces model railways.
 *
 * @param id the brand id
 * @param name the brand name
 * @param slug the brand slug
 * @param kind the brand kind
 */
@RecordBuilder
public record Brand(
    @NotNull String id,
    @NotNull String name,
    @NotNull Slug slug,
    @Nullable BrandKind kind,
    @Nullable BrandStatus status) {
  public Brand {
    requireNonNull(id, "Brand id cannot be null");
    requireNonNull(name, "Brand name cannot be null");
    requireNonNull(slug, "Brand slug cannot be null");
  }
}
