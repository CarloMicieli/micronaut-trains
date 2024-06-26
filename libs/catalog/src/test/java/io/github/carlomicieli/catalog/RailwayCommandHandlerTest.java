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

import static org.assertj.core.api.Assertions.assertThat;

import com.neovisionaries.i18n.CountryCode;
import io.github.carlomicieli.Address;
import io.github.carlomicieli.ContactInfo;
import io.github.carlomicieli.Metadata;
import io.github.carlomicieli.OrganizationEntityType;
import io.github.carlomicieli.TestConstants;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("RailwayCommandHandler")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayCommandHandlerTest {
  private final RailwayRepository railwayRepository = RailwayRepository.INSTANCE;
  private final RailwayCommandHandler railwayCommandHandler =
      new RailwayCommandHandler(railwayRepository, TestConstants.TEST_CLOCK);

  @Test
  void it_should_find_railway_by_id_when_it_exists() {
    RailwayCommand.FindRailwayById findRailwayById =
        new RailwayCommand.FindRailwayById(RailwayId.fromName("FS"));
    Optional<Railway> railway = railwayCommandHandler.handle(findRailwayById);
    assertThat(railway).isPresent();
    assertThat(railway.get().id()).isEqualTo(RailwayId.fromName("FS"));
    assertThat(railway.get().name()).isEqualTo("FS");
    assertThat(railway.get().abbreviation()).isEqualTo("fs");
    assertThat(railway.get().country()).isEqualTo(CountryCode.IT);
    assertThat(railway.get().slug().value()).isEqualTo("fs");
  }

  @Test
  void it_should_find_railway_by_id() {
    RailwayCommand.FindRailwayById findRailwayById =
        new RailwayCommand.FindRailwayById(RailwayId.fromName("Not Found"));
    Optional<Railway> railway = railwayCommandHandler.handle(findRailwayById);
    assertThat(railway).isEmpty();
  }

  @Test
  void it_should_find_all_railways() {
    RailwayCommand.FindAllRailways findAllRailways = new RailwayCommand.FindAllRailways();
    var railways = railwayCommandHandler.handle(findAllRailways);
    assertThat(railways).isNotNull().hasSize(6);
  }

  @ParameterizedTest
  @MethodSource("createRailwayArguments")
  void it_should_create_a_new_railway(String name, String abbreviation, String country) {
    Address address = new Address(CountryCode.IT, "Rome", "Via Roma", null, "RM", "00100");
    ContactInfo contactInfo =
        new ContactInfo("mail@mail.com", "+3900000000", URI.create("http://www.example.com"));
    RailwayPeriodOfActivity periodOfActivity =
        RailwayPeriodOfActivity.activeRailway(TestConstants.DATE_TIME_NOW.toLocalDate());
    RailwayCommand.CreateRailway createRailway =
        new RailwayCommand.CreateRailway(
            name, abbreviation, country, periodOfActivity, address, "LIMITED_COMPANY", contactInfo);
    RailwayId id = railwayCommandHandler.handle(createRailway);
    assertThat(id).isNotNull().isEqualTo(RailwayId.fromName("Ferrovie dello stato"));

    Optional<Railway> railway = railwayRepository.findById(id);
    assertThat(railway).isPresent();
    assertThat(railway.get().id()).isEqualTo(RailwayId.fromName("Ferrovie dello stato"));
    assertThat(railway.get().name()).isEqualTo(name);
    assertThat(railway.get().abbreviation()).isEqualTo(abbreviation);
    assertThat(railway.get().country()).isEqualTo(CountryCode.IT);
    assertThat(railway.get().periodOfActivity()).isNotNull();
    assertThat(railway.get().periodOfActivity().status()).isEqualTo(RailwayStatus.ACTIVE);
    assertThat(railway.get().periodOfActivity().operatingSince())
        .isEqualTo(TestConstants.DATE_TIME_NOW.toLocalDate());
    assertThat(railway.get().address()).isEqualTo(address);
    assertThat(railway.get().metadata()).isEqualTo(Metadata.createdAt(TestConstants.DATE_TIME_NOW));
    assertThat(railway.get().organizationEntityType())
        .isEqualTo(OrganizationEntityType.LIMITED_COMPANY);
    assertThat(railway.get().contactInfo()).isEqualTo(contactInfo);
  }

  private static Stream<Arguments> createRailwayArguments() {
    return Stream.of(
        Arguments.of("Ferrovie dello stato", "FS", "IT"),
        Arguments.of("Ferrovie dello stato", "FS", "it"));
  }
}
