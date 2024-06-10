package org.example;

import java.util.Arrays;

@SuppressWarnings("unused")
public class BinPackingExamples {

  private final int[] example1 = {44, 24, 24, 22, 21, 17, 8, 8, 6, 6};
  private final int[] example2 = {51, 28, 28, 28, 27, 25, 12, 12, 10, 10, 10, 10, 10, 10, 10, 10};

  private final BinPacking bp;

  public BinPackingExamples(BinPacking bp) {
    this.bp = bp;
  }

  public void firstCompare() {
    compareAlgorithms(example1, 60);
    compareAlgorithms(example1, 61);
  }

  public void secondCompare() {
    compareAlgorithms(example2, 75);
    compareAlgorithms(example2, 76);
  }

  private void compareAlgorithms(int[] weights, int binCapacity) {
    System.out.println("Elements: " + Arrays.toString(example1));
    System.out.println("Bin capacity: " + binCapacity);

    System.out.println("First Fit Decreasing:");
    bp.printResults(bp.firstFitDecreasing(weights, binCapacity));

    System.out.println("\nBranch and Bound:");
    bp.printResults(bp.branchAndBound(weights, binCapacity));

    System.out.println("\n");
  }
}
