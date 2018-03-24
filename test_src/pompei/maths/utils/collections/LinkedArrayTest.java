package pompei.maths.utils.collections;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pompei.maths.utils.RND;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.System.identityHashCode;
import static org.fest.assertions.Assertions.assertThat;

public class LinkedArrayTest extends LinkedArrayTestParent {

  protected <E> LinkedArray<E> createArray() {
    return LinkedArray.create();
  }

  @Test
  public void empty() throws Exception {
    LinkedArray<String> array = createArray();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveFirst()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveLast()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();
  }

  @Test
  public void putFirst_one_getAndRemoveFirst() throws Exception {
    String x = RND.str(10);

    LinkedArray<String> array = createArray();

    LinkedArray<String> ret = array.putFirst(x);
    assertThat(identityHashCode(ret)).isEqualTo(identityHashCode(array));

    assertThat(array.count()).isEqualTo(1);
    assertThat(array.isEmpty()).isFalse();

    assertThat(array.getAndRemoveFirst()).isEqualTo(x);

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveFirst()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveLast()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();
  }

  @Test
  public void putFirst_one_getAndRemoveLast() throws Exception {
    String x = RND.str(10);

    LinkedArray<String> array = createArray();

    LinkedArray<String> ret = array.putFirst(x);
    assertThat(identityHashCode(ret)).isEqualTo(identityHashCode(array));

    assertThat(array.count()).isEqualTo(1);
    assertThat(array.isEmpty()).isFalse();

    assertThat(array.getAndRemoveLast()).isEqualTo(x);

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveLast()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveFirst()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();
  }


  @Test
  public void putLast_one_getAndRemoveLast() throws Exception {

    String x = RND.str(10);

    LinkedArray<String> array = createArray();

    LinkedArray<String> ret = array.putLast(x);
    assertThat(identityHashCode(ret)).isEqualTo(identityHashCode(array));

    assertThat(array.count()).isEqualTo(1);
    assertThat(array.isEmpty()).isFalse();

    assertThat(array.getAndRemoveLast()).isEqualTo(x);

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveLast()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveFirst()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();
  }

  @Test
  public void putLast_one_getAndRemoveFirst() throws Exception {

    String x = RND.str(10);

    LinkedArray<String> array = createArray();

    LinkedArray<String> ret = array.putLast(x);
    assertThat(identityHashCode(ret)).isEqualTo(identityHashCode(array));

    assertThat(array.count()).isEqualTo(1);
    assertThat(array.isEmpty()).isFalse();

    assertThat(array.getAndRemoveFirst()).isEqualTo(x);

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveFirst()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();

    assertThat(array.getAndRemoveLast()).isNull();

    assertThat(array.count()).isZero();
    assertThat(array.isEmpty()).isTrue();
  }

  @Test(dataProvider = "multipleOperationsInOneThread_DP")
  public void multipleOperationsInOneThread(String operations) throws Exception {

    final LinkedArray<String> testing = createArray();
    final LinkedArray<String> std = new LinkedArrayOnArrayList<>();

    for (String pairStr : operations.split(",")) {
      String[] pair = pairStr.split("-");
      String des = "index = " + pair[0];
      String command = pair[1];
      switch (command) {
        case "putLast": {
          String s = RND.str(10);
          LinkedArray<String> ret = testing.putLast(s);
          std.putLast(s);

          assertThat(identityHashCode(ret)).describedAs(des).isEqualTo(identityHashCode(testing));
          assertThat(testing.count()).describedAs(des).isEqualTo(std.count());
          break;
        }
        case "putFirst": {
          String s = RND.str(10);
          LinkedArray<String> ret = testing.putFirst(s);
          std.putFirst(s);

          assertThat(identityHashCode(ret)).describedAs(des).isEqualTo(identityHashCode(testing));
          assertThat(testing.count()).describedAs(des).isEqualTo(std.count());
          break;
        }
        case "getFirst": {
          String actual = testing.getAndRemoveFirst();
          String expected = std.getAndRemoveFirst();

          assertThat(actual).describedAs(des).isEqualTo(expected);
          assertThat(testing.count()).describedAs(des).isEqualTo(std.count());
          break;
        }
        case "getLast": {
          String actual = testing.getAndRemoveLast();
          String expected = std.getAndRemoveLast();

          assertThat(actual).describedAs(des).isEqualTo(expected);
          assertThat(testing.count()).describedAs(des).isEqualTo(std.count());
          break;
        }
        default:
          throw new RuntimeException("Unknown command " + command);
      }
      {

      }
    }

  }

  interface Putter {
    void go(LinkedArray<String> array, String element);
  }

  interface GetAndRemover {
    String go(LinkedArray<String> array);
  }

  @DataProvider
  public Object[][] countOfWrites_countOfReads_DP() {
    return new Object[][]{

      {(Putter) LinkedArray::putLast, (GetAndRemover) LinkedArray::getAndRemoveLast},
      {(Putter) LinkedArray::putLast, (GetAndRemover) LinkedArray::getAndRemoveFirst},
      {(Putter) LinkedArray::putFirst, (GetAndRemover) LinkedArray::getAndRemoveLast},
      {(Putter) LinkedArray::putFirst, (GetAndRemover) LinkedArray::getAndRemoveFirst},

    };
  }

  @Test(dataProvider = "countOfWrites_countOfReads_DP")
  public void countOfWrites_countOfReads(Putter putter, GetAndRemover getAndRemover) throws Exception {

    final LinkedArray<String> testing = createArray();
    final Set<String> set = new HashSet<>();

    final int count = 100 + RND.plusInt(1000);

    for (int i = 0; i < count; i++) {

      String str = RND.str(10);
      while (set.contains(str)) str = RND.str(10);

      set.add(str);
      putter.go(testing, str);

    }

    class LocalThread extends Thread {
      int count = 0;
      final Set<String> localSet = new HashSet<>();

      @Override
      public void run() {
        while (true) {
          String str = getAndRemover.go(testing);
          if (str == null) break;
          localSet.add(str);
          count++;
        }
      }
    }

    final List<LocalThread> threadList = new ArrayList<>();
    for (int i = 0; i < 13; i++) {
      threadList.add(new LocalThread());
    }

    threadList.forEach(Thread::start);

    final Set<String> newSet = new HashSet<>();

    for (LocalThread localThread : threadList) {
      localThread.join();
      assertThat(localThread.localSet).hasSize(localThread.count);
      newSet.addAll(localThread.localSet);
    }

    int actualCount = threadList.stream().mapToInt(a -> a.count).sum();

    assertThat(actualCount).isEqualTo(count);
    assertThat(newSet).isEqualTo(set);
  }

  @Test
  public void test_toString() throws Exception {
    final LinkedArray<String> testing = createArray();

    testing.putLast("A").putLast("B").putLast("C");

    assertThat(testing.toString()).isEqualTo("[A, B, C]");
  }

  @Test
  public void putAndGetInManyThreads() throws Exception {
    final LinkedArray<String> testing = createArray();
    List<String> control = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      String str = RND.str(11);
      control.add(str);
      testing.putLast(str);
    }

    final AtomicBoolean working = new AtomicBoolean(true);

    class LocalThread extends Thread {

      final AtomicBoolean getLast = new AtomicBoolean();
      final List<String> list = new ArrayList<>(control.size());
      int nullCount = 0;

      LocalThread(boolean getLast) {this.getLast.set(getLast);}

      String get() {
        return getLast.get() ? testing.getAndRemoveLast() : testing.getAndRemoveFirst();
      }

      @Override
      public void run() {
        while (working.get() || !testing.isEmpty()) work();
      }

      private void work() {
        String element = get();
        if (element == null) {
          nullCount++;
        } else {
          list.add(element);
        }
      }

      void invert() {
        getLast.set(!getLast.get());
      }
    }

    final List<LocalThread> threadList = new ArrayList<>();

    for (int i = 0; i < 16 * 2; i++) {
      threadList.add(new LocalThread(i % 2 == 0));
    }

    threadList.forEach(Thread::start);

    for (int u = 0; u < 10; u++) {

      for (int i = 0; i < 100; i++) {
        String str = RND.str(7);
        control.add(str);
        testing.putFirst(str);
      }

      threadList.forEach(LocalThread::invert);
    }

    int waitCount = 0;

    while (true) {
      if (testing.isEmpty()) break;
      waitCount++;
    }

    System.out.println("waitCount = " + waitCount);

    working.set(false);
    for (LocalThread localThread : threadList) {
      localThread.join();
    }

    assertThat(testing.count()).isZero();

    int totalCount = threadList.stream().mapToInt(x -> x.list.size()).sum();
    assertThat(totalCount).isEqualTo(control.size());
    Collections.sort(control);

    List<String> actualList = threadList.stream().flatMap(x -> x.list.stream()).sorted().collect(Collectors.toList());
    assertThat(actualList).isEqualTo(control);

    int totalNullCount = threadList.stream().mapToInt(x -> x.nullCount).sum();
    System.out.println("totalNullCount = " + totalNullCount);
  }

  public static String oneTimeSec(long time, long count) {
    double div = (double) time / (double) count / 1e9;
    DecimalFormat fmt = new DecimalFormat("0.################");
    DecimalFormatSymbols sym = new DecimalFormatSymbols();
    sym.setGroupingSeparator(' ');
    sym.setDecimalSeparator('.');
    fmt.setDecimalFormatSymbols(sym);
    return fmt.format(div);
  }

  static class GettingStatistics {
    long nullCount = 0, notNullCount = 0;
    long nullTime = 0, notNullTime = 0;

    public GettingStatistics() {}

    public GettingStatistics(GettingStatistics a) {
      this.nullCount = a.nullCount;
      this.notNullCount = a.notNullCount;
      this.nullTime = a.nullTime;
      this.notNullTime = a.notNullTime;
    }

    public GettingStatistics add(GettingStatistics a) {
      this.nullCount += a.nullCount;
      this.notNullCount += a.notNullCount;
      this.nullTime += a.nullTime;
      this.notNullTime += a.notNullTime;
      return this;
    }

    @Override
    public String toString() {
      return "call " + oneTimeSec(notNullTime, notNullCount) + " sec"
        + ", null call " + oneTimeSec(nullTime, nullCount) + " sec";
    }
  }

  static class PuttingStatistics {
    long putTime = 0, putCount = 0;

    public PuttingStatistics() {}

    public PuttingStatistics(PuttingStatistics a) {
      this.putTime = a.putTime;
      this.putCount = a.putCount;
    }

    PuttingStatistics add(PuttingStatistics a) {
      this.putTime += a.putTime;
      this.putCount += a.putCount;
      return this;
    }

    @Override
    public String toString() {
      return "time " + oneTimeSec(putTime, putCount);
    }
  }

  @Test
  public void timing() throws Exception {
    final LinkedArray<String> testing = createArray();

    final AtomicBoolean working = new AtomicBoolean(true);
    final AtomicBoolean showInfo = new AtomicBoolean(false);


    class GettingThread extends Thread {
      String get() {return null;}

      final GettingStatistics stat = new GettingStatistics();
      boolean localShowInfo = false;
      long lowCaseCount = 0;
      final AtomicReference<GettingStatistics> toShow = new AtomicReference<>(new GettingStatistics());

      GettingStatistics showStat() {
        return toShow.get();
      }

      @Override
      public void run() {
        while (working.get()) {
          String[] acceptor = new String[100];
          for (int i = 0, c = acceptor.length; i < c; i++) {
            long time = System.nanoTime();
            String s = get();
            time = System.nanoTime() - time;
            acceptor[i] = s;
            if (s == null) {
              stat.nullTime += time;
              stat.nullCount++;
            } else {
              stat.notNullTime += time;
              stat.notNullCount++;
            }

            {
              boolean topShowInfo = showInfo.get();
              if (!localShowInfo && topShowInfo) {
                toShow.set(new GettingStatistics(stat));
              }
              localShowInfo = topShowInfo;
            }
          }
          for (String s : acceptor) {
            if (s != null) for (int i = 0, c = s.length(); i < c; i++) {
              char ch = s.charAt(i);
              if (ch == Character.toLowerCase(ch)) lowCaseCount++;
            }
          }
        }

        {
          toShow.set(new GettingStatistics(stat));
        }
      }
    }

    class LastGettingThread extends GettingThread {
      @Override
      String get() {
        return testing.getAndRemoveLast();
      }
    }

    class FirstGettingThread extends GettingThread {
      @Override
      String get() {
        return testing.getAndRemoveFirst();
      }
    }

    class PuttingThread extends Thread {
      void put(String s) {}

      final List<String> array = new ArrayList<>();

      {
        for (int i = 0; i < 100; i++) {
          array.add(RND.str(13));
        }
      }

      boolean localShowInfo = false;
      final PuttingStatistics stat = new PuttingStatistics();
      final AtomicReference<PuttingStatistics> toShow = new AtomicReference<>(new PuttingStatistics());

      PuttingStatistics showStat() {
        return toShow.get();
      }

      @Override
      public void run() {
        while (working.get()) {

          for (String s : array) {
            long time = System.nanoTime();
            put(s);
            stat.putTime += System.nanoTime() - time;
            stat.putCount++;

            {
              boolean topShow = showInfo.get();
              if (!localShowInfo && topShow) {
                toShow.set(new PuttingStatistics(stat));
              }
              localShowInfo = topShow;
            }
          }

        }

        {
          toShow.set(new PuttingStatistics(stat));
        }
      }
    }

    class LastPuttingThread extends PuttingThread {
      @Override
      void put(String s) {
        testing.putLast(s);
      }
    }

    class FirstPuttingThread extends PuttingThread {
      @Override
      void put(String s) {
        testing.putFirst(s);
      }
    }

    final List<LastGettingThread> lastGettingThreads = new ArrayList<>();
    final List<FirstGettingThread> firstGettingThreads = new ArrayList<>();
    final List<LastPuttingThread> lastPuttingThreads = new ArrayList<>();
    final List<FirstPuttingThread> firstPuttingThreads = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
      lastGettingThreads.add(new LastGettingThread());
      firstGettingThreads.add(new FirstGettingThread());
      lastPuttingThreads.add(new LastPuttingThread());
      firstPuttingThreads.add(new FirstPuttingThread());
    }

    lastGettingThreads.forEach(Thread::start);
    firstGettingThreads.forEach(Thread::start);
    lastPuttingThreads.forEach(Thread::start);
    firstPuttingThreads.forEach(Thread::start);

    File workingFile = new File("build/LinkedArrayTest/timing.working");
    workingFile.getParentFile().mkdirs();
    workingFile.createNewFile();

    while (workingFile.exists()) {

      Thread.sleep(100);
      showInfo.set(true);
      Thread.sleep(600);
      showInfo.set(false);
      Thread.sleep(300);

      {
        GettingStatistics lastGetting = lastGettingThreads
          .stream()
          .map(GettingThread::showStat)
          .reduce(new GettingStatistics(), GettingStatistics::add);

        GettingStatistics firstGetting = firstGettingThreads
          .stream()
          .map(GettingThread::showStat)
          .reduce(new GettingStatistics(), GettingStatistics::add);

        PuttingStatistics lastPutting = lastPuttingThreads
          .stream()
          .map(PuttingThread::showStat)
          .reduce(new PuttingStatistics(), PuttingStatistics::add);

        PuttingStatistics firstPutting = firstPuttingThreads
          .stream()
          .map(PuttingThread::showStat)
          .reduce(new PuttingStatistics(), PuttingStatistics::add);

        showInfo("row", lastGetting, firstGetting, lastPutting, firstPutting);
      }
    }

    working.set(false);

    for (LastGettingThread lastGettingThread : lastGettingThreads) {
      lastGettingThread.join();
    }
    for (FirstGettingThread firstGettingThread : firstGettingThreads) {
      firstGettingThread.join();
    }
    for (LastPuttingThread lastPuttingThread : lastPuttingThreads) {
      lastPuttingThread.join();
    }
    for (FirstPuttingThread firstPuttingThread : firstPuttingThreads) {
      firstPuttingThread.join();
    }

    {
      GettingStatistics lastGetting = lastGettingThreads
        .stream()
        .map(GettingThread::showStat)
        .reduce(new GettingStatistics(), GettingStatistics::add);

      GettingStatistics firstGetting = firstGettingThreads
        .stream()
        .map(GettingThread::showStat)
        .reduce(new GettingStatistics(), GettingStatistics::add);

      PuttingStatistics lastPutting = lastPuttingThreads
        .stream()
        .map(PuttingThread::showStat)
        .reduce(new PuttingStatistics(), PuttingStatistics::add);

      PuttingStatistics firstPutting = firstPuttingThreads
        .stream()
        .map(PuttingThread::showStat)
        .reduce(new PuttingStatistics(), PuttingStatistics::add);

      showInfo("TOTAL", lastGetting, firstGetting, lastPutting, firstPutting);
    }

  }

  private static void showInfo(String prefix,
                               GettingStatistics lastGetting,
                               GettingStatistics firstGetting,
                               PuttingStatistics lastPutting,
                               PuttingStatistics firstPutting) {

    System.out.println("---------------------------------------------------------------------------------------------");
    System.out.println(prefix + " lastGetting  " + lastGetting);
    System.out.println(prefix + " firstGetting " + firstGetting);
    System.out.println(prefix + " lastPutting  " + lastPutting);
    System.out.println(prefix + " firstPutting " + firstPutting);

  }

  @Test
  public void oneTimeSec() throws Exception {
    long start = System.nanoTime();
    Thread.sleep(500);
    long time = System.nanoTime() - start;
    System.out.println(oneTimeSec(time, 1));
  }
}
