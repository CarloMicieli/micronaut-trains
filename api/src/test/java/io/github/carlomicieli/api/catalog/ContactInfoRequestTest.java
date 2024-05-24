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

import static org.assertj.core.api.Assertions.assertThat;

import io.github.carlomicieli.ContactInfo;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("ContactInfoRequest")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ContactInfoRequestTest {
  @Test
  void it_should_convert_to_ContactInfo_values() {
    ContactInfoRequest contactInfoRequest =
        new ContactInfoRequest("mail@mail.com", "+391234567890", URI.create("http://example.com"));
    ContactInfo contactInfo = contactInfoRequest.toContactInfo();
    assertThat(contactInfo).isNotNull();
    assertThat(contactInfo.email()).isEqualTo("mail@mail.com");
    assertThat(contactInfo.phone()).isEqualTo("+391234567890");
    assertThat(contactInfo.websiteUrl()).isEqualTo(URI.create("http://example.com"));
  }
}
