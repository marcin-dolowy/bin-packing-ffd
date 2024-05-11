package org.example;

public class Node {
  int level;
  int numBins;
  int[] binRemain;

  Node(int level, int numBins, int[] binRemain) {
    this.level = level;
    this.numBins = numBins;
    this.binRemain = binRemain.clone();
  }
}
