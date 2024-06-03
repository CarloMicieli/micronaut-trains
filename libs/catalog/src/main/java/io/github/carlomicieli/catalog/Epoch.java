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
package io.github.carlomicieli.catalog;

import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * It represents a model railway epoch
 *
 * <p><strong>Description</strong>
 *
 * <p>The model railway industry adopted an <em>"Era"</em>, or <em>"Epoch"</em> system; the idea
 * being to group models into a defined time bracket, so that locomotives, coaching and wagon stock
 * could be reasonably grouped together.
 *
 * <p>This enumeration respects the <em>European Epoch System</em>.
 *
 * <p>European Epoch System There are six main Epochs for European railways, although as with most
 * time periods, there is no hard and fast rule that every model must belong to a definitive era.
 * Each Epoch is preceded by a Roman Numeral to split them into six.
 *
 * <p>Again, it’s impossible to truly capture every single nuance as each country developed slightly
 * differently to fit their particular set of circumstances.
 *
 * <p>Typically, Epochs include dates to give an idea of the time period being referenced, but these
 * will differ country to country.
 */
public sealed interface Epoch {
  Epoch I = new Single("I");
  Epoch II = new Single("II");
  Epoch IIa = new Single("IIa");
  Epoch IIb = new Single("IIb");
  Epoch III = new Single("III");
  Epoch IIIa = new Single("IIIa");
  Epoch IIIb = new Single("IIIb");
  Epoch IV = new Single("IV");
  Epoch IVa = new Single("IVa");
  Epoch IVb = new Single("IVb");
  Epoch V = new Single("V");
  Epoch Va = new Single("Va");
  Epoch Vb = new Single("Vb");
  Epoch Vm = new Single("Vm");
  Epoch VI = new Single("VI");

  @Unmodifiable
  List<Epoch> ALL = List.of(I, II, IIa, IIb, III, IIIa, IIIb, IV, IVa, IVb, V, Va, Vb, Vm, VI);

  record Single(@NotNull String value) implements Epoch {
    public Single {
      Objects.requireNonNull(value, "Epoch value cannot be null");
    }

    @Override
    public String toString() {
      return value;
    }
  }

  record Multiple(@NotNull Epoch first, @NotNull Epoch second) implements Epoch {
    public Multiple {
      Objects.requireNonNull(first, "First epoch value cannot be null");
      Objects.requireNonNull(second, "Second epoch value cannot be null");
    }

    @Override
    public String toString() {
      return first + "/" + second;
    }
  }

  static Epoch parse(String value) {
    return switch (value) {
      case "I" -> Epoch.I;
      case "II" -> Epoch.II;
      case "IIa" -> Epoch.IIa;
      case "IIb" -> Epoch.IIb;
      case "III" -> Epoch.III;
      case "IIIa" -> Epoch.IIIa;
      case "IIIb" -> Epoch.IIIb;
      case "IV" -> Epoch.IV;
      case "IVa" -> Epoch.IVa;
      case "IVb" -> Epoch.IVb;
      case "IV/V" -> new Multiple(Epoch.IV, Epoch.V);
      case "V" -> Epoch.V;
      case "Va" -> Epoch.Va;
      case "Vb" -> Epoch.Vb;
      case "Vm" -> Epoch.Vm;
      case "V/VI" -> new Multiple(Epoch.V, Epoch.VI);
      case "VI" -> Epoch.VI;
      default -> throw new IllegalArgumentException("Invalid epoch value: " + value);
    };
  }
}
