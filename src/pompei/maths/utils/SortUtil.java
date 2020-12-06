package pompei.maths.utils;

import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class SortUtil {
  private static final float K = 5f / 7f;

  public static void sortBoob(String[] ss) {
    int len = ss.length;

    boobDistancePresort(ss, len);

    while (true) {

      boolean swapped = false;

      for (int i = 0, c = len - 1; i < c; i++) {
        swapped = swapped || swapIfNeed(ss, i, i + 1);
      }

      if (!swapped) {
        return;
      }
    }
  }

  private static void boobDistancePresort(String[] ss, int len) {
    int distance = len / 2;
    while (true) {

      for (int i = 0, c = len - distance; i < c; i++) {
        swapIfNeed(ss, i, i + distance);
      }

      distance = (int) ((float) distance * K + 0.5f);

      if (distance <= 1) {
        break;
      }
    }
  }

  public static void sortBoob2(String[] ss) {
    int len = ss.length;

    boobDistancePresort(ss, len);

    int n8 = len / 8;
    if (n8 * 8 == len) {
      n8--;
    }

    while (true) {
      boolean ok1 = true, ok2 = true, ok3 = true, ok4 = true;

      for (int i = 0; i < n8; i++) {
        int I = i * 8;

        ok1 = ok1 && !swapIfNeed(ss, I + 0, I + 1);
        ok2 = ok2 && !swapIfNeed(ss, I + 2, I + 3);
        ok3 = ok3 && !swapIfNeed(ss, I + 4, I + 5);
        ok4 = ok4 && !swapIfNeed(ss, I + 6, I + 7);

        ok1 = ok1 && !swapIfNeed(ss, I + 1, I + 2);
        ok2 = ok2 && !swapIfNeed(ss, I + 3, I + 4);
        ok3 = ok3 && !swapIfNeed(ss, I + 5, I + 6);
        ok4 = ok4 && !swapIfNeed(ss, I + 7, I + 8);
      }

      for (int i = n8 * 8, c = len - 1; i < c; i++) {
        ok1 = ok1 && !swapIfNeed(ss, i, i + 1);
      }

      if ((ok1 && ok2) && (ok3 && ok4)) {
        return;
      }
    }
  }

  public static boolean swapIfNeed(String[] ss, int i, int j) {
    String s1 = ss[i];
    String s2 = ss[j];
    int cmp = s1.compareTo(s2);
    if (cmp <= 0) {
      return false;
    }
    ss[i] = s2;
    ss[j] = s1;
    return true;
  }

  public static void swap(String[] ss, int i, int j) {
    String s1 = ss[i];
    String s2 = ss[j];
    ss[i] = s2;
    ss[j] = s1;
  }

  public static void sortMerge(String[] ss) {
    String[] ss2 = new String[ss.length];
    sortMergeFromTo(ss, 0, ss.length, ss2);
  }

  public static void sortMergeParallel(String[] ss) {
    new MergeSortPart(ss, new String[ss.length], 0, ss.length).fork().join();
  }

  private static void sort2elems(String[] ss, int from) {
    String s1 = ss[from];
    String s2 = ss[from + 1];
    if (s1.compareTo(s2) <= 0) {
      return;
    }
    ss[from] = s2;
    ss[from + 1] = s1;
  }

  private static void sort3elems(String[] ss, int from) {
    String s1 = ss[from];
    String s2 = ss[from + 1];
    String s3 = ss[from + 2];
    boolean s1Change = false, s2Change = false, s3Change = false;
    if (s1.compareTo(s2) > 0) {
      String tmp = s1;
      s1 = s2;
      s2 = tmp;
      s1Change = s2Change = true;
    }
    if (s2.compareTo(s3) > 0) {
      String tmp = s2;
      s2 = s3;
      s3 = tmp;
      s2Change = s3Change = true;
    }
    if (s1.compareTo(s2) > 0) {
      String tmp = s1;
      s1 = s2;
      s2 = tmp;
      s1Change = s2Change = true;
    }
    if (s1Change) {
      ss[from] = s1;
    }
    if (s2Change) {
      ss[from + 1] = s2;
    }
    if (s3Change) {
      ss[from + 2] = s3;
    }
  }

  private static void sort4elems(String[] ss, int from) {
    String s1 = ss[from];
    String s2 = ss[from + 1];
    String s3 = ss[from + 2];
    String s4 = ss[from + 3];
    boolean s1Change = false, s2Change = false, s3Change = false, s4Change = false;
    if (s1.compareTo(s2) > 0) {
      String tmp = s1;
      s1 = s2;
      s2 = tmp;
      s1Change = s2Change = true;
    }
    if (s3.compareTo(s4) > 0) {
      String tmp = s3;
      s3 = s4;
      s4 = tmp;
      s3Change = s4Change = true;
    }
    if (s1.compareTo(s3) > 0) {
      String tmp = s1;
      s1 = s3;
      s3 = tmp;
      s1Change = s3Change = true;
    }
    if (s2.compareTo(s4) > 0) {
      String tmp = s2;
      s2 = s4;
      s4 = tmp;
      s2Change = s4Change = true;
    }
    if (s2.compareTo(s3) > 0) {
      String tmp = s2;
      s2 = s3;
      s3 = tmp;
      s2Change = s3Change = true;
    }
    if (s1Change) {
      ss[from] = s1;
    }
    if (s2Change) {
      ss[from + 1] = s2;
    }
    if (s3Change) {
      ss[from + 2] = s3;
    }
    if (s4Change) {
      ss[from + 3] = s4;
    }
  }

  private static class MergeSortPart extends RecursiveAction {

    private final String[] ss;
    private final String[] ss2;
    private final int from;
    private final int to;

    public MergeSortPart(String[] ss, String[] ss2, int from, int to) {
      this.ss = ss;
      this.ss2 = ss2;
      this.from = from;
      this.to = to;
    }

    @Override
    protected void compute() {
      int from = this.from, to = this.to;
      String[] ss = this.ss;
      String[] ss2 = this.ss2;

      int len = to - from;
      if (sortSmall(from, to, ss, len)) {
        return;
      }
      {
        int middle = from + len / 2;

        MergeSortPart part1 = new MergeSortPart(ss, ss2, from, middle);
        MergeSortPart part2 = new MergeSortPart(ss, ss2, middle, to);

        part1.fork();
        part2.fork();

        part1.join();
        part2.join();

        mergeSortedParts(ss, ss2, from, middle, to);
      }
    }
  }

  private static boolean sortSmall(int from, int to, String[] ss, int len) {
    switch (len) {
      case 0:
      case 1:
        return true;
      case 2:
        sort2elems(ss, from);
        return true;
      case 3:
        sort3elems(ss, from);
        return true;
      case 4:
        sort4elems(ss, from);
        return true;

      case 5:
        sort5elements(ss, from);
        return true;
      case 6:
        sort6elements(ss, from);
        return true;
      case 7:
        sort7elements(ss, from);
        return true;
      case 8:
        sort8elements(ss, from);
        return true;
      case 9:
        sort9elements(ss, from);
        return true;
    }
    return false;
  }

  @SuppressWarnings("DuplicatedCode")
  private static void sort9elements(String[] ss, int from) {

    swapIfNeed(ss, from + 0, from + 5);
    swapIfNeed(ss, from + 1, from + 6);
    swapIfNeed(ss, from + 2, from + 7);
    swapIfNeed(ss, from + 3, from + 8);

    swapIfNeed(ss, from + 0, from + 4);
    swapIfNeed(ss, from + 1, from + 5);
    swapIfNeed(ss, from + 2, from + 6);
    swapIfNeed(ss, from + 3, from + 7);

    while (true) {
      boolean ok1 = !swapIfNeed(ss, from + 0, from + 1);
      boolean ok2 = !swapIfNeed(ss, from + 2, from + 3);
      boolean ok3 = !swapIfNeed(ss, from + 4, from + 5);
      boolean ok4 = !swapIfNeed(ss, from + 6, from + 7);

      boolean ok5 = !swapIfNeed(ss, from + 1, from + 2);
      boolean ok6 = !swapIfNeed(ss, from + 3, from + 4);
      boolean ok7 = !swapIfNeed(ss, from + 5, from + 6);
      boolean ok8 = !swapIfNeed(ss, from + 7, from + 8);

      if ((ok1 && ok2) && (ok3 && ok4) && (ok5 && ok6) && (ok7 && ok8)) {
        return;
      }
    }
  }


  private static void sort8elements(String[] ss, int from) {

    swapIfNeed(ss, from + 0, from + 4);
    swapIfNeed(ss, from + 1, from + 5);
    swapIfNeed(ss, from + 2, from + 6);
    swapIfNeed(ss, from + 3, from + 7);

    while (true) {
      boolean ok1 = !swapIfNeed(ss, from + 0, from + 1);
      boolean ok2 = !swapIfNeed(ss, from + 2, from + 3);
      boolean ok3 = !swapIfNeed(ss, from + 4, from + 5);
      boolean ok4 = !swapIfNeed(ss, from + 6, from + 7);

      ok2 = ok2 && !swapIfNeed(ss, from + 1, from + 2);
      ok3 = ok3 && !swapIfNeed(ss, from + 3, from + 4);
      ok4 = ok4 && !swapIfNeed(ss, from + 5, from + 6);

      if ((ok1 && ok2) && (ok3 && ok4)) {
        return;
      }
    }
  }

  private static void sort7elements(String[] ss, int from) {
    swapIfNeed(ss, from + 0, from + 4);
    swapIfNeed(ss, from + 1, from + 5);
    swapIfNeed(ss, from + 2, from + 6);

    while (true) {
      boolean ok1 = !swapIfNeed(ss, from + 0, from + 1);
      boolean ok2 = !swapIfNeed(ss, from + 2, from + 3);
      boolean ok3 = !swapIfNeed(ss, from + 4, from + 5);

      boolean ok4 = !swapIfNeed(ss, from + 1, from + 2);
      boolean ok5 = !swapIfNeed(ss, from + 3, from + 4);
      boolean ok6 = !swapIfNeed(ss, from + 5, from + 6);

      if ((ok1 && ok2) && (ok3 && ok4) && (ok5 && ok6)) {
        return;
      }
    }
  }

  private static void sort6elements(String[] ss, int from) {
    swapIfNeed(ss, from + 0, from + 3);
    swapIfNeed(ss, from + 1, from + 4);
    swapIfNeed(ss, from + 2, from + 5);

    while (true) {
      boolean ok1 = !swapIfNeed(ss, from + 0, from + 1);
      boolean ok2 = !swapIfNeed(ss, from + 2, from + 3);
      boolean ok3 = !swapIfNeed(ss, from + 4, from + 5);

      boolean ok4 = !swapIfNeed(ss, from + 1, from + 2);
      boolean ok5 = !swapIfNeed(ss, from + 3, from + 4);

      if ((ok1 && ok2) && (ok3 && ok4) && ok5) {
        return;
      }
    }
  }

  private static void sort5elements(String[] ss, int from) {
    swapIfNeed(ss, from + 0, from + 3);
    swapIfNeed(ss, from + 1, from + 4);

    while (true) {
      boolean ok1 = !swapIfNeed(ss, from + 0, from + 1);
      boolean ok2 = !swapIfNeed(ss, from + 2, from + 3);

      boolean ok3 = !swapIfNeed(ss, from + 1, from + 2);
      boolean ok4 = !swapIfNeed(ss, from + 3, from + 4);

      if ((ok1 && ok2) && (ok3 && ok4)) {
        return;
      }
    }
  }

  public static void sortMergeFromTo(String[] ss, int from, int to, String[] ss2) {
    int len = to - from;
    if (sortSmall(from, to, ss, len)) {
      return;
    }

    int middle = from + len / 2;
    sortMergeFromTo(ss, from, middle, ss2);
    sortMergeFromTo(ss, middle, to, ss2);

    mergeSortedParts(ss, ss2, from, middle, to);
  }

  private static void mergeSortedParts(String[] ss, String[] ss2, int from, int middle, int to) {
    int len = to - from;
    System.arraycopy(ss, from, ss2, from, len);
    int leftIndex = from, rightIndex = middle;
    String left = ss2[leftIndex], right = ss2[rightIndex];
    int leftCmpRight = left.compareTo(right);

    for (int i = from; i < to; i++) {

      if (leftCmpRight <= 0) {

        ss[i] = left;
        leftIndex++;
        if (leftIndex < middle) {
          left = ss2[leftIndex];
          if (rightIndex < to) {
            leftCmpRight = left.compareTo(right);
          }
        } else {
          leftCmpRight = 1;
        }

      } else {

        ss[i] = right;
        rightIndex++;
        if (rightIndex < to) {
          right = ss2[rightIndex];
          if (leftIndex < middle) {
            leftCmpRight = left.compareTo(right);
          }
        } else {
          leftCmpRight = -1;
        }

      }

    }
  }

  private static void sortMergeSmallBoob(String[] ss, int from, int to) {
    {
      final int dist = 4;
      for (int i = from, end = to - dist; i < end; i++) {
        swapIfNeed(ss, i, i + dist);
      }
    }

    {
      final int dist = 2;
      for (int i = from, end = to - dist; i < end; i++) {
        swapIfNeed(ss, i, i + dist);
      }
    }

    while (true) {
      boolean swapped = false;
      for (int i = from, end = to - 1; i < end; i++) {
        swapped = swapped || swapIfNeed(ss, i, i + 1);
      }
      if (!swapped) {
        return;
      }
    }
  }

  public static void shuffle(String[] ss) {
    Random rnd = new Random();
    for (int i = 0, n = ss.length; i < n; i++) {
      swap(ss, i, rnd.nextInt(n));
    }
  }
}
