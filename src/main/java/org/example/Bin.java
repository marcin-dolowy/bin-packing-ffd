package org.example;

import java.util.ArrayList;
import java.util.List;

public class Bin {
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
