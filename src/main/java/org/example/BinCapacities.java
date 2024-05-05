package org.example;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class BinCapacities {
  public static List<Main.Bin> firstFitWithVariableBinCapacities(
      int[] weights, int[] binCapacities) {
    List<Main.Bin> bins = new ArrayList<>();
    int binIndex = 0;

    for (int weight : weights) {
      boolean placed = false;

      for (Main.Bin bin : bins) {
        if (bin.canFit(weight)) {
          bin.add(weight);
          placed = true;
          break;
        }
      }

      if (!placed) {
        if (binIndex >= binCapacities.length) {
          throw new IllegalArgumentException("Not enough bin capacities provided");
        }
        Main.Bin newBin = new Main.Bin(binCapacities[binIndex]);
        newBin.add(weight);
        bins.add(newBin);
        binIndex++;
      }
    }

    return bins;
  }
}
