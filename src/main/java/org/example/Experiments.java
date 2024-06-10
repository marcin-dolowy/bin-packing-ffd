package org.example;

public class Experiments {

  public static void main(String[] args) {
    BinPacking bp = new BinPacking();
    BinPackingExamples examples = new BinPackingExamples(bp);

    examples.firstCompare();

    examples.secondCompare();
  }
}
