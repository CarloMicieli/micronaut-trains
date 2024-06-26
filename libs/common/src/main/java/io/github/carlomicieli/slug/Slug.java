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
package io.github.carlomicieli.slug;

import com.github.slugify.Slugify;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

/** It converts a string to a "slug". */
public record Slug(@NotNull String value) {
  private static final Slugify SLUGIFY =
      Slugify.builder().lowerCase(true).customReplacement("ä", "ae").build();

  public Slug {
    value = SLUGIFY.slugify(value);
  }

  @CheckReturnValue
  public static @NotNull Slug of(@NotNull final String value) {
    return new Slug(value);
  }

  @CheckReturnValue
  public static @NotNull String slugify(@NotNull final String value) {
    return SLUGIFY.slugify(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
