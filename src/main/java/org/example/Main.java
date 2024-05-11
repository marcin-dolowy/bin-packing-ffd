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

  static class Node {
    int level;
    int numBins;
    int[] binRemain;

    Node(int level, int numBins, int[] binRemain) {
      this.level = level;
      this.numBins = numBins;
      this.binRemain = binRemain.clone();
    }
  }

  static int branchAndBound(int[] weights, int binCapacity) {
    Arrays.sort(weights);
    int n = weights.length;
    PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> b.level - a.level);
    pq.add(new Node(-1, 0, new int[n]));
    int res = n;
    while (!pq.isEmpty()) {
      Node node = pq.poll();
      if (node.level == n - 1) {
        res = Math.min(res, node.numBins);
      } else {
        for (int i = 0; i < node.numBins; i++) {
          if (node.binRemain[i] >= weights[node.level + 1]) {
            node.binRemain[i] -= weights[node.level + 1];
            pq.add(new Node(node.level + 1, node.numBins, node.binRemain));
            node.binRemain[i] += weights[node.level + 1];
          }
        }
        if (node.numBins + 1 < res) {
          node.binRemain[node.numBins] = binCapacity - weights[node.level + 1];
          pq.add(new Node(node.level + 1, node.numBins + 1, node.binRemain));
        }
      }
    }
    return res;
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

  public static int[] sortReversed(int[] arr) {
    Arrays.parallelSort(arr);
    return IntStream.range(0, arr.length).map(i -> arr[arr.length - 1 - i]).toArray();
  }

  private static int bruteForceBinPacking(int[] weights, int binCapacity) {
    return bruteForceBinPackingHelper(weights, binCapacity, 0, new ArrayList<>());
  }

  private static int bruteForceBinPackingHelper(
      int[] weights, int binCapacity, int index, List<Bin> bins) {
    if (index == weights.length) {
      return bins.size();
    }

    int bestBinCount = Integer.MAX_VALUE;

    List<Bin> binsCopy = new ArrayList<>(bins);

    for (Bin bin : binsCopy) {
      if (bin.canFit(weights[index])) {
        bin.add(weights[index]);
        int newBinCount = bruteForceBinPackingHelper(weights, binCapacity, index + 1, bins);
        if (newBinCount < bestBinCount) {
          bestBinCount = newBinCount;
        }
        bin.items.remove(bin.items.size() - 1);
        bin.capacity += weights[index];
      }
    }

    Bin newBin = new Bin(binCapacity);
    newBin.add(weights[index]);
    bins.add(newBin);
    int newBinCount = bruteForceBinPackingHelper(weights, binCapacity, index + 1, bins);
    if (newBinCount < bestBinCount) {
      bestBinCount = newBinCount;
    }
    bins.remove(bins.size() - 1);

    return bestBinCount;
  }

  public static void printResults(List<Bin> result) {
    System.out.println("We need minimum " + result.size() + " bins to accommodate all items");
    for (int i = 0; i < result.size(); i++) {
      System.out.println((i + 1) + " bin contains: " + result.get(i).items);
    }
  }

  public static void main(String[] args) {
    int[] weights = {51, 28, 28, 28, 27, 25, 12, 12, 10, 10, 10, 10, 10, 10, 10, 10};
    int binCapacity = 76;

    //    int[] weights = {44, 24, 24, 22, 21, 17, 8, 8, 6, 6};
    //    int binCapacity = 61;

    printResults(firstFitDecreasing(weights, binCapacity));

    int bruteForceBinPacking = bruteForceBinPacking(weights, binCapacity);
    System.out.println("Brute Force: " + bruteForceBinPacking);

    var branchAndBound = branchAndBound(weights, binCapacity);
    System.out.println("Branch and Bound: " + branchAndBound);
  }
}
