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
package io.github.carlomicieli;

import io.soabase.recordbuilder.core.RecordBuilder;
import java.time.ZonedDateTime;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

/**
 * It represents a resource metadata
 *
 * @param version the resource version
 * @param createdAt the creation timestamp for this resource
 * @param lastModifiedAt the timestamp when this resource has been modified for the last time
 */
@RecordBuilder
public record Metadata(int version, ZonedDateTime createdAt, ZonedDateTime lastModifiedAt)
    implements MetadataBuilder.With {
  public Metadata {
    if (version < 0) {
      throw new IllegalArgumentException("Version must be a positive integer");
    }
  }

  /**
   * Creates a new {@link Metadata} instance with the given creation timestamp
   *
   * @param now the creation timestamp
   * @return a new {@link Metadata} instance
   */
  @CheckReturnValue
  public static @NotNull Metadata createdAt(@NotNull final ZonedDateTime now) {
    return MetadataBuilder.builder().createdAt(now).lastModifiedAt(now).version(0).build();
  }

  /**
   * Updates the last modified timestamp for this metadata
   *
   * @param now the new last modified timestamp
   * @return a new {@link Metadata} instance
   */
  @CheckReturnValue
  public @NotNull Metadata lastModifiedAt(@NotNull final ZonedDateTime now) {
    return this.withLastModifiedAt(now).withVersion(this.version() + 1);
  }
}
