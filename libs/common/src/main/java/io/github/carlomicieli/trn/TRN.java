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
package io.github.carlomicieli.trn;

import io.github.carlomicieli.slug.Slug;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;

/**
 * A <strong>Train Resource Name</strong> (<em>TRN</em>) is a Uniform Resource Identifier (URI) that
 * uses the urn scheme.
 *
 * <p>TRNs are globally unique persistent identifiers assigned within defined namespaces so they
 * will be available for a long period of time, even after the resource which they identify ceases
 * to exist or becomes unavailable.
 *
 * <p>TRNs cannot be used to directly locate an item and need not be resolvable, as they are simply
 * templates that another parser may use to find an item.
 *
 * @param namespaceIdentifier the namespace identifier
 * @param namespaceSpecificString the namespace-specific string
 */
public record TRN(String namespaceIdentifier, String namespaceSpecificString) {
  private static final String SCHEME = "trn:";

  private static final Pattern NAMESPACE_IDENTIFIER_PATTERN = Pattern.compile("[a-zA-Z0-9_\\-]+");
  private static final Pattern NAMESPACE_SPECIFIC_STRING_PATTERN =
      Pattern.compile("[a-zA-Z0-9_\\-]+");

  /**
   * Creates a new TRN value.
   *
   * @param namespaceIdentifier the namespace identifier
   * @param namespaceSpecificString1 the namespace-specific string
   * @param namespaceSpecificString2 the namespace-specific string
   */
  public TRN(
      @NotNull String namespaceIdentifier,
      @NotNull String namespaceSpecificString1,
      @NotNull String namespaceSpecificString2) {
    this(
        namespaceIdentifier,
        buildNamespaceSpecificString(namespaceSpecificString1, namespaceSpecificString2));
  }

  private static @NotNull String buildNamespaceSpecificString(
      @NotNull String s1, @NotNull String s2) {
    return Stream.of(s1, s2).map(Slug::slugify).reduce((a, b) -> a + ":" + b).orElseThrow();
  }

  public List<String> namespaceSpecificStrings() {
    return List.of(namespaceSpecificString.split(":"));
  }

  /**
   * Checks if the given string is a valid TRN.
   *
   * @param value the string to check
   * @return {@code true} if the given string is a valid TRN, {@code false} otherwise
   */
  @CheckReturnValue
  public static boolean isValid(@NotNull final String value) {
    String[] values = Objects.requireNonNull(value).split(":");
    return extractScheme(values).isPresent()
        && extractNamespaceIdentifier(values).isPresent()
        && extractNamespaceSpecificString(values).isPresent();
  }

  /**
   * Checks if the given string is a valid TRN.
   *
   * @param value the string to check
   * @param message the exception message
   * @throws IllegalArgumentException if the given string is not a valid TRN
   */
  public static TRN requireValid(@NotNull final String value, @NotNull final String message) {
    return tryParse(value).orElseThrow(() -> new IllegalArgumentException(message));
  }

  /**
   * Checks if the given string is a valid TRN with the specified namespace identifier.
   *
   * @param value the string to check
   * @param namespaceIdentifier the expected namespace identifier
   * @param message the exception message
   * @throws IllegalArgumentException if the given string is not a valid TRN or if the namespace
   *     identifier does not match the expected value
   */
  public static TRN requireValid(
      @NotNull final String value,
      @NotNull final String namespaceIdentifier,
      @NotNull final String message) {
    TRN trn = tryParse(value).orElseThrow(() -> new IllegalArgumentException(message));
    if (!trn.namespaceIdentifier().equals(namespaceIdentifier)) {
      throw new IllegalArgumentException(message);
    }
    return trn;
  }

  /**
   * Tries to parse the given string as a TRN.
   *
   * @param value the string to parse
   * @return an {@link Optional} containing the parsed TRN, or an empty {@link Optional} if the
   *     given string is not a valid TRN
   */
  @CheckReturnValue
  public static Optional<TRN> tryParse(@NotNull final String value) {
    return Optional.ofNullable(value).map(input -> input.split(":")).flatMap(TRN::extractValues);
  }

  private static Optional<TRN> extractValues(@NotNull final String[] tokens) {
    if (extractScheme(tokens).isEmpty()) {
      return Optional.empty();
    }

    record Pair(String lhs, String rhs) {}

    return extractNamespaceIdentifier(tokens)
        .flatMap(
            namespaceIdentifier -> {
              Optional<String> namespaceSpecificString = extractNamespaceSpecificString(tokens);
              return namespaceSpecificString.map(nss -> new Pair(namespaceIdentifier, nss));
            })
        .map(pair -> new TRN(pair.lhs(), pair.rhs()));
  }

  /**
   * Creates a new TRN value from the given string.
   *
   * @param value the TRN string
   * @return a new TRN value
   * @throws IllegalArgumentException if the given string is not a valid TRN
   */
  @CheckReturnValue
  public static @NotNull TRN of(@NotNull final String value) {
    String[] tokens = Objects.requireNonNull(value).split(":");
    String scheme =
        extractScheme(tokens)
            .orElseThrow(() -> new IllegalArgumentException("Invalid TRN value: " + value));
    String namespaceIdentifier =
        extractNamespaceIdentifier(tokens)
            .orElseThrow(() -> new IllegalArgumentException("Invalid TRN value: " + value));
    String namespaceSpecificString =
        extractNamespaceSpecificString(tokens)
            .orElseThrow(() -> new IllegalArgumentException("Invalid TRN value: " + value));

    return new TRN(namespaceIdentifier, namespaceSpecificString);
  }

  private static Optional<String> extractScheme(@NotNull final String[] values) {
    return values.length > 0
        ? Optional.of(values[0]).map(scheme -> scheme + ":").filter(SCHEME::equalsIgnoreCase)
        : Optional.empty();
  }

  private static Optional<String> extractNamespaceIdentifier(@NotNull final String[] values) {
    return values.length > 1
        ? Optional.of(values[1])
            .filter(
                namespaceIdentifier ->
                    NAMESPACE_IDENTIFIER_PATTERN.matcher(namespaceIdentifier).matches())
        : Optional.empty();
  }

  private static Optional<String> extractNamespaceSpecificString(@NotNull final String[] values) {
    if (values.length < 3) {
      return Optional.empty();
    }

    return Arrays.stream(values)
        .skip(2)
        .filter(
            namespaceSpecificString ->
                NAMESPACE_SPECIFIC_STRING_PATTERN.matcher(namespaceSpecificString).matches())
        .reduce((a, b) -> a + ":" + b);
  }

  @Override
  public String toString() {
    return SCHEME + namespaceIdentifier + ":" + namespaceSpecificString;
  }
}
