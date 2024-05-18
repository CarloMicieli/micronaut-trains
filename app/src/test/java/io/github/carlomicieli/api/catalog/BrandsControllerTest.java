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
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
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

    @Client("/api/brands")
    interface BrandsClient {
        @Get
        @Consumes("application/json")
        List<Brand> getBrands();
    }
}
