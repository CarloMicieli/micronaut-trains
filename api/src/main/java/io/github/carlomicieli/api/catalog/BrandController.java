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

import io.github.carlomicieli.Address;
import io.github.carlomicieli.ContactInfo;
import io.github.carlomicieli.catalog.BrandCommand;
import io.github.carlomicieli.catalog.BrandCommandHandler;
import io.github.carlomicieli.catalog.BrandId;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(ApiCatalog.API_BRANDS)
public class BrandController {
  private final Logger LOG = LoggerFactory.getLogger(BrandController.class);
  private final BrandCommandHandler commandHandler;

  public BrandController(final BrandCommandHandler commandHandler) {
    this.commandHandler = Objects.requireNonNull(commandHandler, "commandHandler must not be null");
  }

  @Get()
  @Produces(MediaType.APPLICATION_JSON)
  List<BrandView> getAllBrands() {
    LOG.info("GET {}", ApiCatalog.API_BRANDS);
    return commandHandler.handle(new BrandCommand.FindAllBrands()).stream()
        .map(BrandView::fromBrand)
        .toList();
  }

  @Get("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  HttpResponse<BrandView> getBrandById(@PathVariable("id") final String id) {
    LOG.info("GET {}/{}", ApiCatalog.API_BRANDS, id);
    BrandId brandId = BrandId.fromName(id);
    return commandHandler
        .handle(new BrandCommand.FindBrandById(brandId))
        .map(BrandView::fromBrand)
        .map(HttpResponse::ok)
        .orElseGet(HttpResponse::notFound);
  }

  @Post()
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  HttpResponse<?> createBrand(@Valid @Body final BrandRequest brandRequest) {
    LOG.info("POST {} {}", ApiCatalog.API_BRANDS, brandRequest);
    Address address =
        Optional.ofNullable(brandRequest.address()).map(AddressRequest::toAddress).orElse(null);
    ContactInfo contactInfo =
        Optional.ofNullable(brandRequest.contactInfo())
            .map(ContactInfoRequest::toContactInfo)
            .orElse(null);
    BrandId brandId =
        commandHandler.handle(
            new BrandCommand.CreateBrand(
                brandRequest.name(),
                brandRequest.kind(),
                brandRequest.status(),
                address,
                brandRequest.organizationEntityType(),
                contactInfo));

    return HttpResponse.created(URI.create(ApiCatalog.API_BRANDS + "/" + brandId));
  }
}
