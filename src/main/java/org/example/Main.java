package org.example;

import java.util.List;
import java.util.Random;
import org.example.model.Bin;

public class Main {
  public static void main(String[] args) {
    //        int[] weights = {51, 28, 28, 28, 27, 25, 12, 12, 10, 10, 10, 10, 10, 10, 10, 10};
    int[] weights = new Random().ints(100000, 1, 100).toArray();

    int binCapacity = 100;

    //            int[] weights = {44, 24, 24, 22, 21, 17, 8, 8, 6, 6};
    //            int binCapacity = 61;

    var ffd = new BinPacking();

    Long start = System.nanoTime();
    //        ffd.printResults(ffd.firstFitDecreasing(weights, binCapacity));
    List<Bin> bins = ffd.firstFitDecreasing(weights, binCapacity);
    Long end = System.nanoTime();
    System.out.println(end - start);
    System.out.println(bins.size());

    //        ffd.printResults(ffd.branchAndBound(weights, binCapacity));
  }
}
