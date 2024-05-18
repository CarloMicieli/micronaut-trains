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

import io.github.carlomicieli.catalog.Brand;
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

@DisplayName("/api/brands")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MicronautTest
class BrandsControllerTest {
    @Test
    void it_should_get_all_brands(final BrandsClient client) {
        List<Brand> brands = client.getBrands();
        assertThat(brands)
                .isNotEmpty()
                .hasSize(5)
                .containsExactly(
                        new Brand("1", "Brand 1"),
                        new Brand("2", "Brand 2"),
                        new Brand("3", "Brand 3"),
                        new Brand("4", "Brand 4"),
                        new Brand("5", "Brand 5"));
    }

    @Test
    void it_should_find_brands_by_id(final BrandsClient client) {
        Brand brand = client.getBrandById("1").body();
        assertThat(brand).isNotNull();
        assertThat(brand.id()).isEqualTo("1");
        assertThat(brand.name()).isEqualTo("Brand 1");
    }

    @Test
    void it_should_return_NOT_FOUND_when_no_brand_with_the_given_id_is_found(final BrandsClient client) {
        HttpResponse<Brand> response = client.getBrandById("not-found");
        assertThat(response).isNotNull();
        assertThat(response.getStatus().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
        assertThat(response.body()).isNull();
    }

    @Test
    void it_should_create_new_brands(final BrandsClient client) {
        Brand newBrand = new Brand("6", "Brand 6");
        HttpResponse<?> response = client.postBrand(newBrand);
        assertThat(response).isNotNull();
        assertThat(response.getStatus().getCode()).isEqualTo(HttpStatus.CREATED.getCode());
        assertThat(response.getHeaders().get("Location")).contains("/api/brands/6");
    }

    @Client("/api/brands")
    interface BrandsClient {
        @Get
        @Consumes(MediaType.APPLICATION_JSON)
        List<Brand> getBrands();

        @Post
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        HttpResponse<?> postBrand(@Body final Brand brand);

        @Get("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        HttpResponse<Brand> getBrandById(final String id);
    }
}
