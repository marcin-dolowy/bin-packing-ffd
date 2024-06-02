package org.example;

import java.util.Random;

public class DataGenerator {
  private final Random random;

  public DataGenerator(Random random) {
    this.random = random;
  }

  public int[] generate(int elements, int min, int max) {
    return random.ints(elements, min, max + 1).toArray();
  }
}
