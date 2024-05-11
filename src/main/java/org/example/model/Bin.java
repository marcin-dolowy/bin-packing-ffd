package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Bin implements Cloneable {
  private int capacity;
  private List<Integer> items;

  public Bin(int capacity) {
    this.capacity = capacity;
    this.items = new ArrayList<>();
  }

  public boolean canFit(Integer item) {
    return item <= capacity;
  }

  public void add(Integer item) {
    items.add(item);
    capacity -= item;
  }

  public void remove(Integer item) {
    if (items.contains(item)) {
      items.remove(item);
      capacity += item;
    }
  }

  public int capacity() {
    return capacity;
  }

  public List<Integer> items() {
    return items;
  }

  @Override
  public Bin clone() {
    try {
      Bin clone = (Bin) super.clone();
      clone.items = new ArrayList<>(this.items);
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
