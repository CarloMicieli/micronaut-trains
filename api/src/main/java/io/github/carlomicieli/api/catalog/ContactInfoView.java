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
package io.github.carlomicieli.api.catalog;

import io.github.carlomicieli.ContactInfo;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.net.URI;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

@RecordBuilder
@Serdeable(naming = SnakeCaseStrategy.class)
public record ContactInfoView(String email, String phone, URI websiteUrl) {
  /**
   * Creates a new instance of {@link ContactInfo} from a {@link ContactInfoView} instance.
   *
   * @param contactInfo the contact info
   * @return a new instance of {@link ContactInfoView}
   */
  @CheckReturnValue
  public static @NotNull ContactInfoView fromContactInfo(@NotNull final ContactInfo contactInfo) {
    return ContactInfoViewBuilder.builder()
        .email(contactInfo.email())
        .phone(contactInfo.phone())
        .websiteUrl(contactInfo.websiteUrl())
        .build();
  }
}
