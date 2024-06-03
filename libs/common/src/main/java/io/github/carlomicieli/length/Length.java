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
package io.github.carlomicieli.length;

import io.github.carlomicieli.util.BigDecimals;
import java.math.BigDecimal;
import java.util.Objects;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

/** Represents the length of a physical object. */
public sealed interface Length {
  BigDecimal value();

  /**
   * Returns the measure unit for this length.
   *
   * @return the measure unit for this length
   */
  @CheckReturnValue
  default @NotNull MeasureUnit measureUnit() {
    return switch (this) {
      case Inches ignored -> MeasureUnit.INCHES;
      case Kilometers ignored -> MeasureUnit.KILOMETERS;
      case Meters ignored -> MeasureUnit.METERS;
      case Miles ignored -> MeasureUnit.MILES;
      case Millimeters ignored -> MeasureUnit.MILLIMETERS;
    };
  }

  /**
   * Creates a new {@code Length} instance with the given value and measure unit.
   *
   * @param value the value for the length
   * @param measureUnit the measure unit for the length
   * @return a new {@code Length} instance
   */
  @CheckReturnValue
  static @NotNull Length of(@NotNull BigDecimal value, @NotNull MeasureUnit measureUnit) {
    Objects.requireNonNull(value);
    Objects.requireNonNull(measureUnit);

    return switch (measureUnit) {
      case INCHES -> new Inches(value);
      case KILOMETERS -> new Kilometers(value);
      case METERS -> new Meters(value);
      case MILES -> new Miles(value);
      case MILLIMETERS -> new Millimeters(value);
    };
  }
}

record Millimeters(@NotNull BigDecimal value) implements Length {
  public Millimeters {
    BigDecimals.requirePositive(value);
  }
}

record Meters(@NotNull BigDecimal value) implements Length {
  public Meters {
    BigDecimals.requirePositive(value);
  }
}

record Miles(@NotNull BigDecimal value) implements Length {
  public Miles {
    BigDecimals.requirePositive(value);
  }
}

record Inches(@NotNull BigDecimal value) implements Length {
  public Inches {
    BigDecimals.requirePositive(value);
  }
}

record Kilometers(@NotNull BigDecimal value) implements Length {
  public Kilometers {
    BigDecimals.requirePositive(value);
  }
}
