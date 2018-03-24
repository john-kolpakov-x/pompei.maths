package pompei.maths.utils.collections;

import org.testng.annotations.Test;
import pompei.maths.utils.RND;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedArray_optimizations_Test {

  interface Value {
    String asStr();

    default boolean isNone() {
      return false;
    }
  }

  private static final Value NONE = new Value() {
    @Override
    public String asStr() {
      return "None";
    }

    @Override
    public boolean isNone() {
      return true;
    }
  };

  public static Value oneTimeSec(long time, long count) {
    if (count == 0) return NONE;
    double div = (double) time / (double) count / 1e9;
    DecimalFormat fmt = new DecimalFormat("0.0000000000000000");
    DecimalFormatSymbols sym = new DecimalFormatSymbols();
    sym.setGroupingSeparator(' ');
    sym.setDecimalSeparator('.');
    fmt.setDecimalFormatSymbols(sym);
    return () -> fmt.format(div) + " sec";
  }

  static class Indicator implements Comparable<Indicator> {
    public final String name;
    public final Value value;

    public Indicator(String name, Value value) {
      Objects.requireNonNull(name);
      Objects.requireNonNull(value);
      this.name = name;
      this.value = value;
    }

    public Indicator addPrefix(String prefix) {
      return new Indicator(prefix == null ? name : prefix + name, value);
    }

    @Override
    public String toString() {
      return name + " = " + value.asStr();
    }

    public String name() {
      return name == null ? "" : name;
    }

    @Override
    public int compareTo(Indicator o) {
      return name().compareTo(o.name());
    }

    public Indicator setNameLen(int len) {
      StringBuilder sb = new StringBuilder();
      sb.append(name());
      while (sb.length() < len) sb.append(' ');
      return new Indicator(sb.toString(), value);
    }
  }

  abstract static class Statistics {
    public abstract Stream<Indicator> indicators();

    public Stream<Indicator> indicators(String prefix) {
      return indicators().map(a -> a.addPrefix(prefix));
    }

    @Override
    public String toString() {
      return indicators().map(Indicator::toString).collect(Collectors.joining(", "));
    }
  }

  static class GettingStatistics extends Statistics {
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
    public Stream<Indicator> indicators() {
      return Stream.of(
        new Indicator("call", oneTimeSec(notNullTime, notNullCount)),
        new Indicator("not null", oneTimeSec(nullTime, nullCount))
      );
    }

    public final void clean() {
      nullCount = 0;
      notNullCount = 0;
      nullTime = 0;
      notNullTime = 0;
    }
  }

  static class PuttingStatistics extends Statistics {
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

    public final void clean() {
      putTime = 0;
      putCount = 0;
    }

    @Override
    public Stream<Indicator> indicators() {
      return Stream.of(new Indicator("put", oneTimeSec(putTime, putCount)));
    }
  }

  @Test
  public void timing_original() throws Exception {
    timing(LinkedArray.create());
  }

  private void timing(LinkedArray<String> original) throws Exception {

    final AtomicBoolean working = new AtomicBoolean(true);
    final AtomicBoolean showInfo = new AtomicBoolean(false);

    abstract class MyThread extends Thread {
      abstract void cleanStatistics();
    }

    abstract class GettingThread extends MyThread {
      abstract String get();

      final GettingStatistics stat = new GettingStatistics();
      boolean localShowInfo = false;
      long lowCaseCount = 0;
      final AtomicReference<GettingStatistics> toShow = new AtomicReference<>(new GettingStatistics());

      GettingStatistics showStat() {
        return toShow.get();
      }

      final AtomicBoolean cleanStatistics = new AtomicBoolean(false);

      @Override
      void cleanStatistics() {
        cleanStatistics.set(true);
      }

      @Override
      public void run() {
        while (working.get()) {
          if (cleanStatistics.get()) {
            cleanStatistics.set(false);
            stat.clean();
            toShow.set(new GettingStatistics());
          }
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
        return original.getAndRemoveLast();
      }
    }

    class FirstGettingThread extends GettingThread {
      @Override
      String get() {
        return original.getAndRemoveFirst();
      }
    }

    class PuttingThread extends MyThread {
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

      final AtomicBoolean cleanStatistics = new AtomicBoolean(false);

      @Override
      void cleanStatistics() {
        cleanStatistics.set(true);
      }

      @Override
      public void run() {
        while (working.get()) {

          for (String s : array) {
            if (cleanStatistics.get()) {
              cleanStatistics.set(false);
              stat.clean();
              toShow.set(new PuttingStatistics());
            }

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
        original.putLast(s);
      }
    }

    class FirstPuttingThread extends PuttingThread {
      @Override
      void put(String s) {
        original.putFirst(s);
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

    File cleanStatisticsFile = new File("build/LinkedArrayTest/clean_statistics.do");
    cleanStatisticsFile.getParentFile().mkdirs();
    cleanStatisticsFile.createNewFile();

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

        showInfo("row ", lastGetting, firstGetting, lastPutting, firstPutting);
      }

      if (!cleanStatisticsFile.exists()) {
        cleanStatisticsFile.createNewFile();

        Stream.concat(
          Stream.concat(lastGettingThreads.stream(), firstGettingThreads.stream()),
          Stream.concat(lastPuttingThreads.stream(), firstPuttingThreads.stream())
        ).parallel().forEach(MyThread::cleanStatistics);

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

      showInfo("TOTAL ", lastGetting, firstGetting, lastPutting, firstPutting);
    }

    cleanStatisticsFile.delete();
  }

  private static void showInfo(String prefix,
                               GettingStatistics lastGetting,
                               GettingStatistics firstGetting,
                               PuttingStatistics lastPutting,
                               PuttingStatistics firstPutting) {


    System.out.println("---------------------------------------------------------------------------------------------");

    System.out.println(

      Stream.concat(
        lastGetting.indicators("getting last  "),
        Stream.concat(
          firstGetting.indicators("getting first "),
          Stream.concat(
            lastPutting.indicators("putting last  "),
            firstPutting.indicators("putting first ")
          )))
        .map(a -> a.setNameLen(18))
        .map(a -> a.addPrefix(prefix))
        .map(a -> a.setNameLen(30))
        .sorted()
        .map(Indicator::toString)
        .collect(Collectors.joining("\n"))

    );
  }

  @Test
  public void oneTimeSec() throws Exception {
    long start = System.nanoTime();
    Thread.sleep(500);
    long time = System.nanoTime() - start;
    System.out.println(oneTimeSec(time, 1));
  }
}
