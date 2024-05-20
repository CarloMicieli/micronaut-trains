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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@MicronautTest
@DisplayName("/api/scales")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScaleControllerTest {
  @Test
  void it_should_create_new_scales(final ScaleClient client) {
    ScaleRequest request = new ScaleRequest("H0", 87f);
    HttpResponse<?> response = client.createScale(request);
    assertThat(response).isNotNull();
    assertThat(response.getStatus().getCode()).isEqualTo(HttpStatus.CREATED.getCode());
    assertThat(response.getHeaders().get("Location")).contains("/api/scales/7");
  }

  @Test
  void it_should_reject_invalid_scale_requests(final ScaleClient client) {
    String expected =
        "{\"type\":\"https://zalando.github.io/problem/constraint-violation\","
            + "\"title\":\"Constraint Violation\",\"status\":400,"
            + "\"violations\":["
            + "{\"field\":\"createScale.request.ratio\",\"message\":\"must be greater than 0\"},"
            + "{\"field\":\"createScale.request.name\",\"message\":\"must not be blank\"}]}";
    ScaleRequest request = new ScaleRequest("", 0f);
    assertThatThrownBy(() -> client.createScale(request))
        .isInstanceOf(HttpClientResponseException.class)
        .hasMessageContaining(expected);
  }

  @Client("/api/scales")
  interface ScaleClient {
    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    HttpResponse<?> createScale(@Body ScaleRequest request);
  }
}
