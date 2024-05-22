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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Metadata")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MetadataTest {
  @Test
  void it_should_have_a_non_negative_version() {
    assertThatThrownBy(() -> MetadataBuilder.builder().version(-1).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Version must be a positive integer");
  }

  @Test
  void it_should_represent_a_creation_metadata() {
    Metadata metadata = Metadata.createdAt(ZonedDateTime.now());
    assertThat(metadata).isNotNull();
    assertThat(metadata.version()).isEqualTo(0);
    assertThat(metadata.createdAt()).isNotNull();
    assertThat(metadata.lastModifiedAt()).isNotNull();
  }

  @Test
  void it_should_update_the_last_modified_timestamp() {
    Metadata metadata = Metadata.createdAt(ZonedDateTime.now());
    ZonedDateTime now = ZonedDateTime.now();
    Metadata updated = metadata.lastModifiedAt(now);
    assertThat(updated).isNotNull().isNotSameAs(metadata);
    assertThat(updated.version()).isEqualTo(1);
    assertThat(updated.createdAt()).isNotNull();
    assertThat(updated.lastModifiedAt()).isEqualTo(now);
  }
}
