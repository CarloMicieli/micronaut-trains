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

import io.github.carlomicieli.catalog.Brand;
import io.github.carlomicieli.catalog.BrandBuilder;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import java.net.URI;
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/brands")
public class BrandsController {
    private final Logger LOG = LoggerFactory.getLogger(BrandsController.class);

    @Get(produces = MediaType.APPLICATION_JSON)
    List<Brand> getAllBrands() {
        LOG.info("GET /api/brands");
        return IntStream.range(1, 6)
                .boxed()
                .map(id -> BrandBuilder.builder()
                        .id(String.valueOf(id))
                        .name("Brand " + id)
                        .build())
                .toList();
    }

    @Post(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    HttpResponse<?> createBrand(@Body Brand brand) {
        LOG.info("POST /api/brands {}", brand);
        return HttpResponse.created(URI.create("/api/brands/" + brand.id()));
    }
}
