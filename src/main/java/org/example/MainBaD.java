package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class MainBaD {
  private static final int BIN_CAPACITY = 10;
  private static final int MIN_ELEMENTS = 5;
  private static final int MAX_ELEMENTS = 21;
  private static final int STEP = 1;

  public static void main(String[] args) {

    BinPacking binPacking = new BinPacking();
    Random random = new Random();

    try (PrintWriter writer = new PrintWriter(new FileWriter("output_branch_and_bound.txt"))) {
      writer.println("number_of_elements;time_taken;memory_used");
      for (int i = MIN_ELEMENTS; i <= MAX_ELEMENTS; i += STEP) {

        long averageMemoryUsed = 0;
        long averageDuration = 0;

        for (int j = 0; j < 100; j++) {

          int[] weights = random.ints(i, 1, 10).toArray();

          Runtime runtime = Runtime.getRuntime();
          runtime.gc();
          long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

          long startTime = System.nanoTime();
          binPacking.branchAndBound(weights, BIN_CAPACITY);
          long endTime = System.nanoTime();

          long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

          long duration = endTime - startTime;
          long memoryUsed = memoryAfter - memoryBefore;

          averageDuration += duration;
          averageMemoryUsed += memoryUsed;
        }

        averageDuration /= 100;
        averageMemoryUsed /= 100;

        System.out.println(i + ";" + averageDuration + ";" + averageMemoryUsed);
        writer.println(i + ";" + averageDuration + ";" + averageMemoryUsed);
      }
    } catch (IOException e) {
      System.out.println("Error writing to file: " + e.getMessage());
    }
  }
}
