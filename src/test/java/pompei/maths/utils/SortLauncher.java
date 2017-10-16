package pompei.maths.utils;

import java.util.Arrays;

import static pompei.maths.utils.SortUtil.shuffle;

public class SortLauncher {

  public static void main(String[] args) {
    String[] ss = new String[1_000_000];
    fill(ss);

    if (ss.length <= 10_000) {
      shuffle(ss);
      sortBoob(ss);
    }
    {
      shuffle(ss);
      sortNative(ss);
    }
    {
      shuffle(ss);
//      System.out.println("{\"" + Arrays.stream(ss).collect(Collectors.joining("\", \"")) + "\"}");
      sortMerge(ss);
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

  private static void sortBoob(String[] ss) {
    long started = System.nanoTime();
    SortUtil.sortBoob(ss);
    long time = System.nanoTime() - started;

    for (int i = 0, n = ss.length - 1; i < n; i++) {
      if (ss[i].compareTo(ss[i + 1]) >= 0) throw new RuntimeException("Sort Boob: i = " + i);
    }

    System.out.println("\nUsing Boob");
    System.out.println("          n = " + ss.length);
    System.out.println("  sort time = " + time);
  }


  private static void sortNative(String[] ss) {
    long started = System.nanoTime();
    Arrays.sort(ss);
    long time = System.nanoTime() - started;

    for (int i = 0, n = ss.length - 1; i < n; i++) {
      if (ss[i].compareTo(ss[i + 1]) >= 0) throw new RuntimeException("Sort Boob: i = " + i);
    }

    System.out.println("\nUsing Native");
    System.out.println("          n = " + ss.length);
    System.out.println("  sort time = " + time);
  }

  private static void sortMerge(String[] ss) {
    long started = System.nanoTime();
    SortUtil.sortMerge(ss);
    long time = System.nanoTime() - started;

//    System.out.println("{\"" + Arrays.stream(ss).collect(Collectors.joining("\", \"")) + "\"}");

    for (int i = 0, n = ss.length - 1; i < n; i++) {
      if (ss[i].compareTo(ss[i + 1]) >= 0) throw new RuntimeException("Sort Merge: i = " + i);
    }

    System.out.println("\nUsing Merge (written by me)");
    System.out.println("          n = " + ss.length);
    System.out.println("  sort time = " + time);
  }

}
