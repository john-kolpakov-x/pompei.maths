package pompei.maths.utils;

import java.util.Arrays;

import static pompei.maths.utils.SortUtil.shuffle;

public class SortLauncher {

  @SuppressWarnings("ConstantConditions")
  public static void main(String[] args) {
    String[] ss = new String[4_000_000];
    fill(ss);

    final int warmCount = 6;
    final int probeCount = 6;

    if (ss.length <= 10_000) {
      for (int i = 0; i < warmCount; i++) {
        shuffle(ss);
        sortBoob(ss);
      }
      int count = 0;
      long time = 0;
      for (int i = 0; i < probeCount; i++) {
        shuffle(ss);
        time += sortBoob(ss);
        count++;
      }

      checkSorted(ss, "Sort Boob");
      showInfo("Boob", ss.length, (double) time / count);
    }
    if (ss.length <= 10_000) {
      for (int i = 0; i < warmCount; i++) {
        shuffle(ss);
        sortBoob2(ss);
      }
      int count = 0;
      long time = 0;
      for (int i = 0; i < probeCount; i++) {
        shuffle(ss);
        time += sortBoob2(ss);
        count++;
      }

      checkSorted(ss, "Sort Boob2");
      showInfo("Boob2", ss.length, (double) time / count);
    }
    {
      for (int i = 0; i < warmCount; i++) {
        shuffle(ss);
        sortNative(ss);
      }
      int count = 0;
      long time = 0;
      for (int i = 0; i < probeCount; i++) {
        shuffle(ss);
        time += sortNative(ss);
        count++;
      }
      checkSorted(ss, "Sort Native");
      showInfo("Native", ss.length, (double) time / count);
    }
    {
      for (int i = 0; i < warmCount; i++) {
        shuffle(ss);
        sortMerge(ss);
      }
      int count = 0;
      long time = 0;
      for (int i = 0; i < probeCount; i++) {
        shuffle(ss);
        time += sortMerge(ss);
        count++;
      }
      checkSorted(ss, "Sort Merge");
      showInfo("Merge (written by me)", ss.length, (double) time / count);
    }
    {
      for (int i = 0; i < warmCount; i++) {
        shuffle(ss);
        sortMergeParallel(ss);
      }
      int count = 0;
      long time = 0;
      for (int i = 0; i < probeCount; i++) {
        shuffle(ss);
        time += sortMergeParallel(ss);
        count++;
      }
      checkSorted(ss, "Sort MergeParallel");
      showInfo("Merge Parallel (written by me)", ss.length, (double) time / count);
    }
  }

  private static final String ZZZ = "000000000000000000000000000000";

  private static void fill(String[] ss) {
    final int L = ("" + (ss.length - 1)).length();
    for (int i = 0, n = ss.length; i < n; i++) {
      String s = "" + i;
      ss[i] = ZZZ.substring(0, L - s.length()) + s;
    }
  }

  private static long sortBoob(String[] ss) {
    long started = System.nanoTime();
    SortUtil.sortBoob(ss);
    return System.nanoTime() - started;
  }

  private static long sortBoob2(String[] ss) {
    long started = System.nanoTime();
    SortUtil.sortBoob2(ss);
    return System.nanoTime() - started;
  }

  private static void checkSorted(String[] ss, String title) {
    for (int i = 0, n = ss.length - 1; i < n; i++) {
      if (ss[i].compareTo(ss[i + 1]) >= 0) throw new RuntimeException(title + ": i = " + i);
    }
  }


  private static long sortNative(String[] ss) {
    long started = System.nanoTime();
    Arrays.sort(ss);
    return System.nanoTime() - started;
  }

  private static long sortMerge(String[] ss) {
    long started = System.nanoTime();
    SortUtil.sortMerge(ss);
    return System.nanoTime() - started;
  }

  private static long sortMergeParallel(String[] ss) {
    long started = System.nanoTime();
    SortUtil.sortMergeParallel(ss);
    return System.nanoTime() - started;
  }

  final static double GIG = 1e9;

  private static void showInfo(String caption, int n, double time) {
    System.out.println("\nUsing " + caption);
    System.out.println("          n = " + n);
    System.out.println("  sort time = " + time / GIG + " s");
  }

}
