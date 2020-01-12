package pompei.maths.crypt;

import static pompei.maths.utils.Conv.nanoToSec;

public class AllocManyBytes2 {

  public static class Cell {
    public float x, y, z;
  }

  @SuppressWarnings("DuplicatedCode")
  public static void main(String[] args) {

    int N = 830;

    long time1 = System.nanoTime();

    Cell[] cells = new Cell[N * N * N];
    for (int i = 0; i < N * N * N; i++) {
      cells[i] = new Cell();
    }

    long time2 = System.nanoTime();

    System.out.println("Allocates for " + nanoToSec(time1, time2));

    for (int i = 0; i < N * N * N; i++) {
      cells[i].x = (float) /*Math.sin*/(i * 0.3);
      cells[i].y = (float) /*Math.sin*/(i * 1.3);
      cells[i].z = (float) /*Math.sin*/(i * 0.13);
    }

    long time3 = System.nanoTime();
    System.out.println("Filled for " + nanoToSec(time2, time3));

    for (int i = 0; i < N * N * N; i++) {
      Cell cell = cells[i];
      cell.x += i * (1.23f - i) * (0.17f * i - 3.1f);
      cell.y += i * (1.32f + i) * (0.17f * i + 3.9f);
      cell.z += i * (1.23f - i) * (0.19f * i - 3.1f);
    }

    long time4 = System.nanoTime();
    System.out.println("Incremented for " + nanoToSec(time3, time4));

    float sum = 0, sum2 = 0;
    long count = 0;

    for (int i = 0; i < N * N * N; i++) {
      Cell cell = cells[i];
      sum2 += cell.x + cell.y + cell.z;
      if (sum2 < -1e10f || sum2 > 1e10f) {
        sum += sum2;
        sum2 = 0;
        count++;
      }
    }

    sum += sum2;

    long time5 = System.nanoTime();
    System.out.println("Calculated sum = " + sum + " for " + nanoToSec(time4, time5) + " :: count = " + count);
  }
}
