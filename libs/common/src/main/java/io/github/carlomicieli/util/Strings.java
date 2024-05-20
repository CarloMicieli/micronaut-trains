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
package io.github.carlomicieli.util;

public final class Strings {
  /**
   * Checks that the specified String value is not blank and throws an IllegalArgumentException if
   * it is.
   *
   * @param input the value to check
   */
  public static void requireNonBlank(String input) {
    requireNonBlank(input, "input cannot be blank");
  }

  /**
   * Checks that the specified String value is not blank and throws an IllegalArgumentException if
   * it is.
   *
   * @param input the value to check
   * @param message detail message to be used in the event that a IllegalArgumentException is thrown
   */
  public static void requireNonBlank(String input, String message) {
    if (input == null || input.isBlank()) {
      throw new IllegalArgumentException(message);
    }
  }
}
