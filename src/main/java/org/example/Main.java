package org.example;

public class Main {
  public static void main(String[] args) {
    int[] weights = {51, 28, 28, 28, 27, 25, 12, 12, 10, 10, 10, 10, 10, 10, 10, 10};
    int binCapacity = 76;

    //            int[] weights = {44, 24, 24, 22, 21, 17, 8, 8, 6, 6};
    //            int binCapacity = 61;

    var ffd = new BinPacking();

    ffd.printResults(ffd.firstFitDecreasing(weights, binCapacity));

    ffd.printResults(ffd.branchAndBound(weights, binCapacity));
  }
}
