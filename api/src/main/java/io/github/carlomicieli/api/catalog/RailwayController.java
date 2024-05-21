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

import io.github.carlomicieli.catalog.RailwayCommand;
import io.github.carlomicieli.catalog.RailwayCommandHandler;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(ApiCatalog.API_RAILWAYS)
public class RailwayController {
  private static final Logger LOG = LoggerFactory.getLogger(RailwayController.class);
  private final RailwayCommandHandler commandHandler;

  public RailwayController(RailwayCommandHandler commandHandler) {
    this.commandHandler = Objects.requireNonNull(commandHandler, "The command handler is required");
  }

  @Get
  @Produces(MediaType.APPLICATION_JSON)
  HttpResponse<List<RailwayView>> getRailways() {
    LOG.info("GET {}", ApiCatalog.API_RAILWAYS);
    var command = new RailwayCommand.FindAllRailways();
    var railways = commandHandler.handle(command).stream().map(RailwayView::fromRailway).toList();
    return HttpResponse.ok(railways);
  }

  @Get("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  HttpResponse<RailwayView> getRailwayById(@PathVariable final String id) {
    LOG.info("GET {}/{}", ApiCatalog.API_RAILWAYS, id);
    var command = new RailwayCommand.FindRailwayById(id);
    return commandHandler
        .handle(command)
        .map(RailwayView::fromRailway)
        .map(HttpResponse::ok)
        .orElseGet(HttpResponse::notFound);
  }

  @Post
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  HttpResponse<?> createRailway(@Valid @Body final RailwayRequest request) {
    LOG.info("POST {} {}", ApiCatalog.API_RAILWAYS, request);
    var command =
        new RailwayCommand.CreateRailway(
            request.name(), request.abbreviation(), request.country(), request.status());
    var railwayId = commandHandler.handle(command);
    return HttpResponse.created(URI.create(ApiCatalog.API_RAILWAYS + "/" + railwayId));
  }
}
