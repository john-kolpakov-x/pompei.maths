package pompei.maths.utils.collections;

import org.testng.annotations.Test;
import pompei.maths.utils.RND;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public class LinkedArray_optimizations_Test {
  public static final String START_BOLD = "\033[0;1m";
  public static final String END_BOLD = "\033[0;0m";

  interface Value {
    String asStr();

    default boolean isNone() {
      return false;
    }
  }

  static class StrValue implements Value {

    private final String strValue;

    public StrValue(String strValue) {
      this.strValue = strValue;
    }

    @Override
    public String asStr() {
      return strValue;
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

    public boolean ok() {
      return !value.isNone();
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
        new Indicator("not null", oneTimeSec(nullTime, nullCount)),
        new Indicator("count", new StrValue("" + (nullCount + notNullCount)))
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
      return Stream.of(
        new Indicator("put", oneTimeSec(putTime, putCount)),
        new Indicator("count", new StrValue("" + putCount))
      );
    }
  }

  static class Pair {
    final String left, top;

    public Pair(String left, String top) {
      this.left = left;
      this.top = top;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Pair pair = (Pair) o;
      return Objects.equals(left, pair.left) &&
        Objects.equals(top, pair.top);
    }

    @Override
    public int hashCode() {
      return Objects.hash(left, top);
    }

    public Pair invert() {
      //noinspection SuspiciousNameCombination
      return new Pair(top, left);
    }
  }

  static class IndicatorTable {
    Map<Pair, Value> data = new HashMap<>();

    public void put(String top, Stream<Indicator> indicators) {
      data.putAll(indicators.collect(Collectors.toMap(i -> new Pair(i.name(), top), i -> i.value)));
    }

    public List<String> tops() {
      return data.keySet().stream().map(a -> a.top).distinct().sorted().collect(Collectors.toList());
    }

    public List<String> lefts() {
      return data.keySet().stream().map(a -> a.left).distinct().sorted().collect(Collectors.toList());
    }

    public Map<String, Value> topValues(String top) {
      return data.entrySet()
        .stream()
        .filter(a -> Objects.equals(top, a.getKey().top))
        .collect(Collectors.toMap(a -> a.getKey().left, Map.Entry::getValue));
    }

    public Value get(String left, String top) {
      Value value = data.get(new Pair(left, top));
      return value == null ? NONE : value;
    }

    public void T() {
      data = data.entrySet()
        .stream()
        .map(e -> new AbstractMap.SimpleEntry<>(e.getKey().invert(), e.getValue()))
        .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }
  }

  public static String toLen(String s, int len) {
    StringBuilder sb = new StringBuilder();
    if (s != null) sb.append(s);
    while (sb.length() < len) sb.append(' ');
    return sb.toString();
  }

  public static String toLenC(String s, int len) {
    StringBuilder sb = new StringBuilder();
    if (s != null) sb.append(s);
    while (true) {
      if (sb.length() >= len) return sb.toString();
      sb.append(' ');
      if (sb.length() >= len) return sb.toString();
      sb.insert(0, ' ');
    }
  }

  public static String len(String s, int len) {
    StringBuilder sb = new StringBuilder();
    while (sb.length() < len) sb.append(s);
    return sb.toString();
  }

  @Test
  public void timing() throws Exception {
    Stream<Indicator> original = timing(createOriginal(), "Original").filter(Indicator::ok);
    Stream<Indicator> optimum = timing(createOptimum(), "Optimum").filter(Indicator::ok);

    IndicatorTable table = new IndicatorTable();
    table.put("A Orig", original);
    table.put("B Opt", optimum);

    table.T();

    List<String> lefts = table.lefts();
    List<String> tops = table.tops();

    int leftLength = 0;
    for (String s : lefts) {
      int length = s.length();
      if (leftLength < length) leftLength = length;
    }

    final Map<String, Integer> topLengths = new HashMap<>();

    for (String top : tops) {
      int len = top.length();

      for (Value value : table.topValues(top).values()) {
        int length = value.asStr().length();
        if (len < length) len = length;
      }

      topLengths.put(top, len);
    }

    System.out.println(""
      + START_BOLD
      + len("-", leftLength) + "---"
      + tops.stream().map(s -> len("-", topLengths.get(s))).collect(Collectors.joining("---"))
      + END_BOLD
    );

    System.out.println(""
      + START_BOLD + toLen("", leftLength) + " | "
      + tops.stream().map(s -> toLenC(s, topLengths.get(s))).collect(Collectors.joining(" | "))
      + END_BOLD
    );

    System.out.println(""
      + START_BOLD
      + len("-", leftLength) + "---"
      + tops.stream().map(s -> len("-", topLengths.get(s))).collect(Collectors.joining("---"))
      + END_BOLD
    );


    for (String left : lefts) {
      System.out.println(toLen(left, leftLength) + " | " + tops.stream()
        .map(top -> toLen(table.get(left, top).asStr(), topLengths.get(top)))
        .collect(Collectors.joining(" | "))
      );
    }

    System.out.println(""
//      + START_BOLD
        + len("-", leftLength) + "---"
        + tops.stream().map(s -> len("-", topLengths.get(s))).collect(Collectors.joining("---"))
//      + END_BOLD
    );
  }

  private LinkedArray<String> createOriginal() {
    return new LinkedArrayImpl<>();
  }

  private LinkedArray<String> createOptimum() {
    return new LinkedArrayImpl_optimum<>();
  }

  private Stream<Indicator> timing(LinkedArray<String> original, String name) throws Exception {

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

    abstract class PuttingThread extends MyThread {
      abstract void put(String s);

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

    class Threads {
      final List<LastGettingThread> lastGettingThreads = new ArrayList<>();
      final List<FirstGettingThread> firstGettingThreads = new ArrayList<>();
      final List<LastPuttingThread> lastPuttingThreads = new ArrayList<>();
      final List<FirstPuttingThread> firstPuttingThreads = new ArrayList<>();

      Stream<MyThread> all() {
        return concat(
          concat(lastGettingThreads.stream(), firstGettingThreads.stream()),
          concat(lastPuttingThreads.stream(), firstPuttingThreads.stream())
        );
      }

      Stream<Indicator> allIndicators() {
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

        int maxCount = original.maxCount();

        return concat(lastGetting.indicators("getting last  "),
          concat(firstGetting.indicators("getting first "),
            concat(
              lastPutting.indicators("putting last  "),
              concat(
                firstPutting.indicators("putting first "),
                Stream.of(
                  new Indicator("max count ", () -> "" + maxCount)
                )
              )
            )
          )
        );
      }
    }

    final Threads tt = new Threads();

    //TODO THREAD Setting
    for (int i = 0; i < 2; i++) {
      tt.lastPuttingThreads.add(new LastPuttingThread());
      tt.firstPuttingThreads.add(new FirstPuttingThread());
    }
    for (int i = 0; i < 6; i++) {
      tt.lastGettingThreads.add(new LastGettingThread());
      tt.firstGettingThreads.add(new FirstGettingThread());
    }

    tt.all().forEach(Thread::start);

    File workingFile = new File("build/LinkedArrayTest/timing.working");
    workingFile.getParentFile().mkdirs();
    workingFile.createNewFile();

    File cleanStatisticsFile = new File("build/LinkedArrayTest/clean_statistics.do");
    cleanStatisticsFile.getParentFile().mkdirs();
    cleanStatisticsFile.createNewFile();

    while (workingFile.exists()) {

      Thread.sleep(100);
      showInfo.set(true);
      Thread.sleep(1000);
      showInfo.set(false);
      Thread.sleep(300);

      showInfo("row ", tt.allIndicators(), name);

      if (!cleanStatisticsFile.exists()) {
        cleanStatisticsFile.createNewFile();
        tt.all().parallel().forEach(MyThread::cleanStatistics);
      }
    }

    working.set(false);

    tt.all().forEach(myThread -> {
      try {
        myThread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    showInfo("TOTAL ", tt.allIndicators(), name);

    cleanStatisticsFile.delete();

    return tt.allIndicators();
  }

  private void showInfo(String prefix, Stream<Indicator> indicators, String name) {
    System.out.println("-------------------------- " + name);

    System.out.println(

      indicators
        .map(a -> a.setNameLen(18))
        .map(a -> a.addPrefix(prefix))
        .map(a -> a.setNameLen(30))
        .sorted()
        .map(Indicator::toString)
        .collect(Collectors.joining("\n"))

    );
  }


}
