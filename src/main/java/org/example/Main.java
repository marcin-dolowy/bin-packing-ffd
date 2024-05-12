package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {

  private static final int BIN_CAPACITY = 10;
  private static final int MIN_ELEMENTS = 1000;
  private static final int MAX_ELEMENTS = 200000;
  private static final int STEP = 1000;

  public static void main(String[] args) {

    BinPacking binPacking = new BinPacking();
    Random random = new Random();

    try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
      writer.println("number_of_elements;time_taken;memory_used");
      for (int i = MIN_ELEMENTS; i <= MAX_ELEMENTS; i += STEP) {
        int[] weights = random.ints(i, 1, 10).toArray();

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        long startTime = System.nanoTime();
        binPacking.firstFitDecreasing(weights, BIN_CAPACITY);
        long endTime = System.nanoTime();

        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        long duration = endTime - startTime;
        long memoryUsed = memoryAfter - memoryBefore;

        System.out.println(i + ";" + duration + ";" + memoryUsed);
        writer.println(i + ";" + duration + ";" + memoryUsed);
      }
    } catch (IOException e) {
      System.out.println("Error writing to file: " + e.getMessage());
    }
  }
}
