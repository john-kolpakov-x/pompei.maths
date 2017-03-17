package pompei.maths.fast_file_work;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MultiPrinting {
  public static void main(String[] args) throws Exception {
    new MultiPrinting().exec();
  }

  private void exec() throws Exception {
    final int N = 17;

    final File file = new File("build/res.txt");
    file.getParentFile().mkdirs();

    try (PrintStream pr = new PrintStream(new FileOutputStream(file), true, "UTF-8")) {
      final Thread threads[] = new Thread[N];
      for (int i = 0; i < N; i++) {
        final String I = toStr(i);
        threads[i] = new Thread(() -> {
          StringBuilder sb = new StringBuilder();
          for (int j = 0; j < 100; j++) {
            sb.append(I).append('^').append(j).append(' ');
          }
          for (int u = 0; u < 100; u++) {
            pr.println(I + "-" + toStr(u) + "        " + sb);
          }
        });
      }

      for (Thread thread : threads) {
        thread.start();
      }
      for (Thread thread : threads) {
        thread.join();
      }

    }

    System.out.println("Finish");
  }

  private static String toStr(int intValue) {
    if (intValue < 10) return "0" + intValue;
    return "" + intValue;
  }
}
