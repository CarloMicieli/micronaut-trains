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

/** Represents the measure units for the length. */
public enum MeasureUnit {
  MILES("mi"),
  MILLIMETERS("mm"),
  METERS("m"),
  INCHES("in"),
  KILOMETERS("km");

  private final String symbol;

  MeasureUnit(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Returns the symbol for the MeasureUnit.
   *
   * @return the symbol for the MeasureUnit
   */
  public String symbol() {
    return symbol;
  }

  /**
   * Returns the MeasureUnit for the given symbol.
   *
   * @param symbol the symbol to search for
   * @return the MeasureUnit for the given symbol
   */
  public static MeasureUnit fromSymbol(String symbol) {
    for (MeasureUnit unit : values()) {
      if (unit.symbol.equalsIgnoreCase(symbol)) {
        return unit;
      }
    }
    return null;
  }
}
