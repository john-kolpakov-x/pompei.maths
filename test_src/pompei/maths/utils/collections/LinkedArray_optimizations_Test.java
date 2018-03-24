package pompei.maths.utils.collections;

import org.testng.annotations.Test;
import pompei.maths.utils.RND;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class LinkedArray_optimizations_Test {

  protected <E> LinkedArray<E> createArray() {
    return LinkedArray.create();
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
