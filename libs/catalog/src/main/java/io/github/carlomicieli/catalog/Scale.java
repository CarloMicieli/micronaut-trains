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
import java.math.BigDecimal;
import org.jetbrains.annotations.NotNull;

/**
 * A scale represents the ratio between the size of a model and the size of the real object.
 *
 * @param id the scale id
 * @param name the scale name
 * @param slug the scale slug
 * @param ratio the scale ratio
 */
@RecordBuilder
public record Scale(
    @NotNull String id, @NotNull String name, @NotNull Slug slug, @NotNull BigDecimal ratio) {
  public Scale {
    requireNonNull(id, "Scale id cannot be null");
    requireNonNull(name, "Scale name cannot be null");
    requireNonNull(slug, "Scale slug cannot be null");
    requireNonNull(ratio, "Scale ratio cannot be null");
  }

  @Override
  public String toString() {
    return name() + " (1:" + ratio + ")";
  }
}
