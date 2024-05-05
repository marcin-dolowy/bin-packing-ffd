package org.example;

import java.util.*;
import java.util.stream.IntStream;

public class Main {

  public static class Bin {
    int capacity;
    List<Integer> items;

    Bin(int capacity) {
      this.capacity = capacity;
      this.items = new ArrayList<>();
    }

    boolean canFit(int item) {
      return item <= capacity;
    }

    void add(int item) {
      items.add(item);
      capacity -= item;
    }
  }

  private static List<Bin> firstFit(int[] weights, int binCapacity) {
    List<Bin> bins = new ArrayList<>();

    for (int weight : weights) {
      boolean placed = false;

      for (Bin bin : bins) {
        if (bin.canFit(weight)) {
          bin.add(weight);
          placed = true;
          break;
        }
      }

      if (!placed) {
        Bin newBin = new Bin(binCapacity);
        newBin.add(weight);
        bins.add(newBin);
      }
    }

    return bins;
  }

  public static List<Bin> firstFitDecreasing(int[] weights, int binCapacity) {
    int[] sorted = sortReversed(weights);
    return firstFit(sorted, binCapacity);
  }

  private static List<Bin> firstFitStream(int[] weights, int binCapacity) {
    List<Bin> bins = new ArrayList<>();

    Arrays.stream(weights)
        .forEach(
            weight ->
                bins.stream()
                    .filter(bin -> bin.canFit(weight))
                    .findFirst()
                    .ifPresentOrElse(
                        bin -> bin.add(weight),
                        () -> {
                          Bin newBin = new Bin(binCapacity);
                          newBin.add(weight);
                          bins.add(newBin);
                        }));

    return bins;
  }

  public static List<Bin> firstFitStreamDecreasing(int[] weights, int binCapacity) {
    int[] sorted = sortReversed(weights);
    return firstFitStream(sorted, binCapacity);
  }

  public static int[] sortReversed(int[] arr) {
    Arrays.parallelSort(arr);
    return IntStream.range(0, arr.length).map(i -> arr[arr.length - 1 - i]).toArray();
  }

  public static void printResults(List<Bin> result) {
    System.out.println("We need minimum " + result.size() + " bins to accommodate all items");
    for (int i = 0; i < result.size(); i++) {
      System.out.println((i + 1) + " bin contains: " + result.get(i).items);
    }
  }

  public static void main(String[] args) {
    int[] weights = {9, 2, 2, 9, 4, 6, 5};
    int binCapacity = 10;

    List<Bin> result = firstFitDecreasing(weights, binCapacity);
    printResults(result);

    System.out.println("========");

    List<Bin> resultStream = firstFitStreamDecreasing(weights, binCapacity);
    printResults(resultStream);
  }
}
