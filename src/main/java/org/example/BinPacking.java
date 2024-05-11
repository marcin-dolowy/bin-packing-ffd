package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.example.model.Bin;
import org.example.model.Node;

public class BinPacking {

  public BinPacking() {}

  List<Bin> firstFit(int[] weights, int binCapacity) {
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

  List<Bin> firstFitDecreasing(int[] weights, int binCapacity) {
    int[] sorted = sortReversed(weights);
    return firstFit(sorted, binCapacity);
  }

  List<Bin> branchAndBound(int[] items, int binCapacity) {
    sortReversed(items);

    int n = items.length;
    PriorityQueue<Node> pq = new PriorityQueue<>(Node::compare);
    List<Bin> resultBins = new ArrayList<>();

    pq.add(new Node(-1, new ArrayList<>()));
    int res = n;

    while (!pq.isEmpty()) {
      Node node = pq.poll();
      if (node.level() == n - 1) {
        res = Math.min(res, node.bins().size());
        resultBins = clone(node.bins());
      } else {
        int nextItem = items[node.level() + 1];
        for (int i = 0; i < node.bins().size(); i++) {
          Bin bin = node.bins().get(i);
          if (bin.capacity() >= nextItem) {
            bin.add(nextItem);
            pq.add(new Node(node.level() + 1, clone(node.bins())));
            bin.remove(nextItem);
          }
        }
        if (node.bins().size() + 1 < res) {
          Bin newBin = new Bin(binCapacity);
          newBin.add(nextItem);
          List<Bin> clonedListOfBins = clone(node.bins());
          clonedListOfBins.add(newBin);
          pq.add(new Node(node.level() + 1, clonedListOfBins));
        }
      }
    }

    return resultBins;
  }

  List<Bin> clone(List<Bin> bins) {
    return bins.stream().map(Bin::clone).collect(Collectors.toList());
  }

  int[] sortReversed(int[] arr) {
    Arrays.parallelSort(arr);
    return IntStream.range(0, arr.length).map(i -> arr[arr.length - 1 - i]).toArray();
  }

  void printResults(List<Bin> result) {
    System.out.println("We need minimum " + result.size() + " bins to accommodate all items");
    for (int i = 0; i < result.size(); i++) {
      System.out.println((i + 1) + " bin contains: " + result.get(i).items());
    }
  }
}
