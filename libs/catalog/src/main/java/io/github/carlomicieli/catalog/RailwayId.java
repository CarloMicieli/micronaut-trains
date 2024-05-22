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
import io.github.carlomicieli.trn.TRN;
import java.util.Objects;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

/** A <strong>Railway ID</strong> is a unique identifier for a railway. */
public record RailwayId(@NotNull String value) {
  private static final String NAMESPACE = "railway";

  public RailwayId {
    Objects.requireNonNull(value, "The railway ID value cannot be null");
    TRN trn = TRN.requireValid(value, NAMESPACE, "Invalid railway ID value: " + value);
    value = trn.toString();
  }

  private RailwayId(@NotNull TRN trn) {
    this(trn.toString());
  }

  /**
   * Creates a new {@code RailwayId} from the given name.
   *
   * @param name the name of the railway
   * @return a new {@code RailwayId} instance
   */
  @CheckReturnValue
  public static @NotNull RailwayId fromName(@NotNull final String name) {
    TRN trn = new TRN(NAMESPACE, Slug.of(name).toString());
    return new RailwayId(trn);
  }

  @Override
  public String toString() {
    return value;
  }
}
