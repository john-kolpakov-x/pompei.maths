package pompei.maths.utils;

import java.util.Random;

public class SortUtil {
  private static final float K = 5f / 7f;

  public static void sortBoob(String ss[]) {
    int len = ss.length;
    int distance = len / 2;

    while (true) {

      for (int i = 0, c = len - distance; i < c; i++) {
        swapIfNeed(ss, i, i + distance);
      }

      distance = (int) ((float) distance * K + 0.5f);

      if (distance <= 1) break;
    }

    while (true) {

      boolean swapped = false;

      for (int i = 0, c = len - 1; i < c; i++) {
        swapped = swapped || swapIfNeed(ss, i, i + 1);
      }

      if (!swapped) return;
    }
  }

  public static boolean swapIfNeed(String[] ss, int i, int j) {
    String s1 = ss[i];
    String s2 = ss[j];
    int cmp = s1.compareTo(s2);
    if (cmp <= 0) return false;
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

  public static void sortMerge(String ss[]) {
    String ss2[] = new String[ss.length];
    sortMergeFromTo(ss, 0, ss.length, ss2);
  }

  private static void sortMerge2(String[] ss, int from) {
    String s1 = ss[from];
    String s2 = ss[from + 1];
    if (s1.compareTo(s2) <= 0) return;
    ss[from] = s2;
    ss[from + 1] = s1;
  }

  private static void sortMerge3(String[] ss, int from) {
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
    if (s1Change) ss[from] = s1;
    if (s2Change) ss[from + 1] = s2;
    if (s3Change) ss[from + 2] = s3;
  }

  private static void sortMerge4(String[] ss, int from) {
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
    if (s1Change) ss[from] = s1;
    if (s2Change) ss[from + 1] = s2;
    if (s3Change) ss[from + 2] = s3;
    if (s4Change) ss[from + 3] = s4;
  }

  public static void sortMergeFromTo(String[] ss, int from, int to, String[] ss2) {
    int len = to - from;
    switch (len) {
      case 0:
      case 1:
        return;
      case 2:
        sortMerge2(ss, from);
        return;
      case 3:
        sortMerge3(ss, from);
        return;
      case 4:
        sortMerge4(ss, from);
        return;
      case 5:
      case 6:
      case 7:
      case 8:
        sortMergeSmallBoob(ss, from, to);
        return;
    }
    {
      int middle = from + len / 2;
      sortMergeFromTo(ss, from, middle, ss2);
      sortMergeFromTo(ss, middle, to, ss2);

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
            if (rightIndex < to) leftCmpRight = left.compareTo(right);
          } else {
            leftCmpRight = 1;
          }

        } else {

          ss[i] = right;
          rightIndex++;
          if (rightIndex < to) {
            right = ss2[rightIndex];
            if (leftIndex < middle) leftCmpRight = left.compareTo(right);
          } else {
            leftCmpRight = -1;
          }

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
      if (!swapped) return;
    }
  }

  public static void shuffle(String[] ss) {
    Random rnd = new Random();
    for (int i = 0, n = ss.length; i < n; i++) {
      swap(ss, i, rnd.nextInt(n));
    }
  }
}
