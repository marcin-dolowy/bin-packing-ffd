package org.example;

import static java.lang.Double.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GenericMain {

  public static class Bin<T extends Number> {
    T capacity;
    List<T> items;

    Bin(T capacity) {
      this.capacity = capacity;
      this.items = new ArrayList<>();
    }

    boolean canFit(T item) {
      return item.doubleValue() <= capacity.doubleValue();
    }

    @SuppressWarnings("unchecked")
    void add(T item) {
      items.add(item);
      capacity = (T) valueOf(capacity.doubleValue() - item.doubleValue());
    }
  }

  private static <T extends Number> List<Bin<T>> firstFit(T[] weights, T binCapacity) {
    List<Bin<T>> bins = new ArrayList<>();

    for (T weight : weights) {
      boolean placed = false;

      for (Bin<T> bin : bins) {
        if (bin.canFit(weight)) {
          bin.add(weight);
          placed = true;
          break;
        }
      }

      if (!placed) {
        Bin<T> newBin = new Bin<>(binCapacity);
        newBin.add(weight);
        bins.add(newBin);
      }
    }

    return bins;
  }

  public static <T extends Number & Comparable<T>> List<Bin<T>> firstFitDecreasing(
      T[] weights, T binCapacity) {
    T[] sorted = sortReversed(weights);
    return firstFit(sorted, binCapacity);
  }

  private static <T extends Number> List<Bin<T>> firstFitStream(T[] weights, T binCapacity) {
    List<Bin<T>> bins = new ArrayList<>();

    Arrays.stream(weights)
        .forEach(
            weight ->
                bins.stream()
                    .filter(bin -> bin.canFit(weight))
                    .findFirst()
                    .ifPresentOrElse(
                        bin -> bin.add(weight),
                        () -> {
                          Bin<T> newBin = new Bin<>(binCapacity);
                          newBin.add(weight);
                          bins.add(newBin);
                        }));

    return bins;
  }

  @SuppressWarnings("unused")
  public static <T extends Number & Comparable<T>> List<Bin<T>> firstFitStreamDecreasing(
      T[] weights, T binCapacity) {
    T[] sorted = sortReversed(weights);
    return firstFitStream(sorted, binCapacity);
  }

  public static <T extends Number & Comparable<T>> T[] sortReversed(T[] arr) {
    Arrays.sort(arr, Comparator.reverseOrder());
    return arr;
  }

  public static <T extends Number> void printResults(List<Bin<T>> result) {
    System.out.println("We need minimum " + result.size() + " bins to accommodate all items");
    for (int i = 0; i < result.size(); i++) {
      System.out.println((i + 1) + " bin contains: " + result.get(i).items);
    }
  }

  public static void main(String[] args) {
    Double[] doubleWeights = {0.9, 0.2, 0.2, 0.9, 0.4, 0.6, 0.5};
    double doubleBinCapacity = 1d;
    List<Bin<Double>> doubleBinResult = firstFitDecreasing(doubleWeights, doubleBinCapacity);
    printResults(doubleBinResult);

    System.out.println("===============");

    Integer[] integerWeights = {9, 2, 2, 9, 4, 6, 5};
    int integerBinCapacity = 10;
    List<Bin<Integer>> integerBinResult = firstFitDecreasing(integerWeights, integerBinCapacity);
    printResults(integerBinResult);
  }
}
