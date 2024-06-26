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

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("AddressView")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AddressViewTest {
  @Test
  void it_should_create_an_AddressView_instance_from_an_Address_value() {
    Address address = new Address(CountryCode.IT, "Milano", "Via Roma", "1", "MI", "20100");
    AddressView view = AddressView.fromAddress(address);
    assertThat(view).isNotNull();
    assertThat(view.country()).isEqualTo("IT");
    assertThat(view.city()).isEqualTo("Milano");
    assertThat(view.streetAddress()).isEqualTo("Via Roma");
    assertThat(view.extendedAddress()).isEqualTo("1");
    assertThat(view.region()).isEqualTo("MI");
  }
}
