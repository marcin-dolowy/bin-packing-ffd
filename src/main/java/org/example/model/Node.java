package org.example.model;

import java.util.List;

public record Node(int level, List<Bin> bins) {

  public static int compare(Node a, Node b) {
    return b.level() - a.level();
  }
}
