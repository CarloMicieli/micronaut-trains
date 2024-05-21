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

import io.github.carlomicieli.catalog.ScaleCommand;
import io.github.carlomicieli.catalog.ScaleCommandHandler;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(ApiCatalog.API_SCALES)
public class ScaleController {
  private static final Logger LOG = LoggerFactory.getLogger(ScaleController.class);
  private final ScaleCommandHandler commandHandler;

  public ScaleController(final ScaleCommandHandler commandHandler) {
    this.commandHandler = Objects.requireNonNull(commandHandler, "commandHandler must not be null");
  }

  @Post
  @Consumes(MediaType.APPLICATION_JSON)
  HttpResponse<?> createScale(@Valid @Body ScaleRequest request) {
    LOG.info("POST {} {}", ApiCatalog.API_SCALES, request);
    var command =
        new ScaleCommand.CreateScale(request.name(), request.ratio(), request.trackGauge());
    var scaleId = commandHandler.handle(command);
    return HttpResponse.created(URI.create(ApiCatalog.API_SCALES + "/" + scaleId));
  }

  @Get("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  HttpResponse<ScaleView> getScale(String id) {
    LOG.info("GET {}/{}", ApiCatalog.API_SCALES, id);
    var command = new ScaleCommand.FindScaleById(id);
    return commandHandler
        .handle(command)
        .map(ScaleView::fromScale)
        .map(HttpResponse::ok)
        .orElse(HttpResponse.notFound());
  }

  @Get
  @Produces(MediaType.APPLICATION_JSON)
  HttpResponse<List<ScaleView>> getScales() {
    LOG.info("GET {}", ApiCatalog.API_SCALES);
    var command = new ScaleCommand.FindAllScales();
    var scales = commandHandler.handle(command).stream().map(ScaleView::fromScale).toList();
    return HttpResponse.ok(scales);
  }
}
