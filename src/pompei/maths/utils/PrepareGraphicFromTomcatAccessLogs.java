package pompei.maths.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PrepareGraphicFromTomcatAccessLogs {
  public static void main(String[] args) throws Exception {
    new PrepareGraphicFromTomcatAccessLogs().exec();
  }

  private final Pattern LOG_LINE = Pattern.compile(
      "\\s*(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+\\[(\\S+)\\s+(\\S+)\\]\\s+\"([^\"]+)\"\\s+(\\d+)\\s+.*");

  static class Record {
    public final double t;

    final Map<String, Integer> map = new HashMap<>();

    Record(double t) {
      this.t = t;
    }

    public void inc(String key) {
      map.put(key, map.getOrDefault(key, 0) + 1);
    }

    public int get(String key) {
      int ret = 0;
      for (Map.Entry<String, Integer> e : map.entrySet()) {
        if (e.getKey().startsWith(key)) {
          ret += e.getValue();
        }
      }
      return ret;
    }

    public String line(List<String> keys) {
      return "t=" + t + "=" + keys.stream().map(k -> k + "=" + get(k)).collect(Collectors.joining("="));
    }
  }

  private void exec() throws Exception {
    File dir = new File("/home/pompei/Downloads/logs");

    final String fromTime = "23/Mar/2017:00:00:00";
//    final String fromTime = "22/Mar/2017:13:00:00";
    final double periodInMinutes = 60 * 10;
    final double deltaInSeconds = 10;

    int count = (int) Math.round(Math.ceil(periodInMinutes * 60 / deltaInSeconds));

    System.out.println("count = " + count);

    final SimpleDateFormat DF = new SimpleDateFormat("dd'/'MMM'/'yyyy:HH:mm:ss", Locale.ENGLISH);

    Date from = DF.parse(fromTime);
    long fromMillis = from.getTime();
    long deltaInMillis = Math.round(deltaInSeconds * 1000.0);
    long toMillis = fromMillis + deltaInMillis * count;

    final Set<String> allKeys = new HashSet<>();
    final Record[] arr = new Record[count];

    for (int i = 0; i < count; i++) {
      arr[i] = new Record(deltaInSeconds * (double) i / 60.0 / 60.0);
    }

    //noinspection ConstantConditions
    for (File accessLog : dir.listFiles(pathname -> pathname.getName().contains("access_log"))) {
      System.out.println("Paring file " + accessLog.getName() + " ...");
      try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(accessLog),
                                                                        StandardCharsets.UTF_8))) {
        while (true) {
          String line = br.readLine();
          if (line == null) {
            break;
          }
          Matcher m = LOG_LINE.matcher(line);
          if (m.matches()) {

            String dateStr = m.group(4);
            String status = m.group(7);

            String statusKey = "status_" + status;

            long millis = DF.parse(dateStr).getTime();
            if (millis < fromMillis) {
              continue;
            }
            if (millis >= toMillis) {
              continue;
            }
            int index = (int) ((millis - fromMillis) / deltaInMillis);
            arr[index].inc(statusKey);
            if (!allKeys.contains(statusKey)) {
              allKeys.add(statusKey);
              System.out.println("NEW KEY = " + statusKey);
            }
          } else {
            System.err.println(line);
          }
        }
      }
    }

    System.out.println("Paring complete. Prepare result...");

    List<String> keys = allKeys.stream().sorted().collect(Collectors.toList());
    keys.add("status");

    File outFile = new File("build/graphic_data.txt");
    outFile.getParentFile().mkdirs();

    try (PrintStream pr = new PrintStream(outFile, StandardCharsets.UTF_8)) {

      pr.println("t=t=" + keys.stream().map(k -> k + "=" + k).collect(Collectors.joining("=")));

      for (int i = 0; i < count; i++) {
        pr.println(arr[i].line(keys).replaceAll("\\.", ","));
      }

    }

    System.out.println("FINISH");
  }
}
