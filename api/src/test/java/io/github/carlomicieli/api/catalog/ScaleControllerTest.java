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

import io.github.carlomicieli.slug.Slug;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.math.BigDecimal;
import java.util.List;
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
    ScaleRequest request = new ScaleRequest("", 0f);
    assertThatThrownBy(() -> client.createScale(request))
        .isInstanceOf(HttpClientResponseException.class)
        .hasMessageContaining(
            "{\"field\":\"createScale.request.ratio\",\"message\":\"must be greater than 0\"}")
        .hasMessageContaining(
            "{\"field\":\"createScale.request.name\",\"message\":\"must not be blank\"}");
  }

  @Test
  void it_should_find_scale_by_id(final ScaleClient client) {
    ScaleView scale = client.getScale("1").body();
    assertThat(scale).isNotNull();
    assertThat(scale.id()).isEqualTo("1");
    assertThat(scale.name()).isEqualTo("1");
    assertThat(scale.ratio()).isEqualTo("1:32");
  }

  @Test
  void it_should_return_NOT_FOUND_when_the_scale_is_not_found(final ScaleClient client) {
    HttpResponse<?> response = client.getScale("not-found");
    assertThat(response).isNotNull();
    assertThat(response.getStatus().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
  }

  @Test
  void it_should_get_all_scales(final ScaleClient client) {
    List<ScaleView> scales = client.getScales().body();
    assertThat(scales)
        .isNotEmpty()
        .hasSize(4)
        .contains(
            scaleView("1", BigDecimal.valueOf(32)),
            scaleView("0", BigDecimal.valueOf(43.5)),
            scaleView("H0", BigDecimal.valueOf(87)),
            scaleView("N", BigDecimal.valueOf(160)));
  }

  private ScaleView scaleView(String name, BigDecimal ratio) {
    return ScaleViewBuilder.builder()
        .id(name)
        .name(name)
        .slug(Slug.of(name).toString())
        .ratio("1:" + ratio)
        .build();
  }

  @Client("/api/scales")
  interface ScaleClient {
    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    HttpResponse<?> createScale(@Body ScaleRequest request);

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<ScaleView> getScale(String id);

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    HttpResponse<List<ScaleView>> getScales();
  }
}
