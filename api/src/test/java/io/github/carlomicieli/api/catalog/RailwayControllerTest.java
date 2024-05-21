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
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("/api/railways")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MicronautTest
class RailwayControllerTest {
  @Test
  void it_should_return_all_railways(final RailwayClient client) {
    var railways = client.getRailways();
    assertThat(railways).isNotNull().hasSize(6);
  }

  @Test
  void it_should_return_a_railway_by_id(final RailwayClient client) {
    var railway = client.getRailwayById("1");
    assertThat(railway).isNotNull();
    assertThat(railway.body()).isNotNull();
    assertThat(railway.body().id()).isEqualTo("1");
    assertThat(railway.body().name()).isEqualTo("FS");
    assertThat(railway.body().slug()).isEqualTo("fs");
    assertThat(railway.body().abbreviation()).isEqualTo("fs");
    assertThat(railway.body().country()).isEqualTo(CountryCode.IT.getAlpha2());
  }

  @Test
  void it_should_create_a_new_railway(final RailwayClient client) {
    var request = new RailwayRequest("Trenitalia", "FS", CountryCode.IT.getAlpha2());
    var response = client.createRailway(request);
    assertThat(response).isNotNull();
    assertThat(response.status().getCode()).isEqualTo(HttpStatus.CREATED.getCode());
    assertThat(response.getHeaders().get("Location")).isNotNull().isEqualTo("/api/railways/7");
  }

  @Client("/api/railways")
  interface RailwayClient {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    List<RailwayView> getRailways();

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<RailwayView> getRailwayById(final String id);

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<?> createRailway(@Body final RailwayRequest request);
  }
}
