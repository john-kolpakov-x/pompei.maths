package pompei.maths.crypt;

import static pompei.maths.utils.Conv.nanoToSec;

public class AllocManyBytes {

  public static class Cell {
    public float x, y, z;
  }

  public static void main(String[] args) {

    int N = 830;

    long time1 = System.nanoTime();

    Cell[][][] cells = new Cell[N][][];
    for (int i = 0; i < N; i++) {
      cells[i] = new Cell[N][];
      for (int j = 0; j < N; j++) {
        cells[i][j] = new Cell[N];
        for (int k = 0; k < N; k++) {
          cells[i][j][k] = new Cell();
        }
      }
    }

    long time2 = System.nanoTime();

    System.out.println("Allocates for " + nanoToSec(time1, time2));

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < N; k++) {
          cells[i][j][k].x = (float) /*Math.sin*/(i * 0.3 + j * 0.4 + k * 0.5);
          cells[i][j][k].y = (float) /*Math.sin*/(i * 1.3 + j * 1.4 + k * 1.5);
          cells[i][j][k].z = (float) /*Math.sin*/(i * 0.13 + j * 0.14 + k * 0.15);
        }
      }
    }

    long time3 = System.nanoTime();
    System.out.println("Filled for " + nanoToSec(time2, time3));

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < N; k++) {
          Cell cell = cells[i][j][k];
          cell.x += i * (1.23f - j) * (0.17f * k - 3.1f);
          cell.y += i * (1.32f + j) * (0.17f * k + 3.9f);
          cell.z += i * (1.23f - j) * (0.19f * k - 3.1f);
        }
      }
    }

    long time4 = System.nanoTime();
    System.out.println("Incremented for " + nanoToSec(time3, time4));

    float sum = 0, sum2 = 0;
    long count = 0;

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < N; k++) {
          sum2 += cells[i][j][k].x;
          sum2 += cells[i][j][k].y;
          sum2 += cells[i][j][k].z;
        }
        if (sum2 > 1e10f || sum2 < -1e10f) {
          sum += sum2;
          sum2 = 0;
          count++;
        }
      }
    }

    sum += sum2;

    long time5 = System.nanoTime();
    System.out.println("Calculated sum = " + sum + " for " + nanoToSec(time4, time5) + " :: count = " + count);
  }
}
