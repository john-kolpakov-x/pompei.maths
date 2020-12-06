package pompei.maths.distribution;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbeUniformity {
  public static void main(String[] args) throws Exception {
    ProbeUniformity p = new ProbeUniformity();
    p.exec();
  }

  private void exec() throws Exception {
    Desk desk = new Desk(1000, 0, 1);


    List<Thread> threads = new ArrayList<>();

    for (int u = 0; u < 16; u++) {
      threads.add(new Thread(() -> {
        Random random = new Random();
        for (int i = 0; i < 10_000 * 2000; i++) {
          desk.put(f(random.nextDouble()));
        }
      }));
    }

    threads.forEach(Thread::start);
    for (Thread thread : threads) {
      thread.join();
    }

    Path path = Paths.get("build/Uniformity4.data.txt");
    path.toFile().getParentFile().mkdirs();

    try (PrintStream pr = new PrintStream(new FileOutputStream(path.toFile()), false, StandardCharsets.UTF_8)) {
      desk.printTo(pr);
    }

    System.out.println("Finish");
  }

  private static double f(double v) {
    return (Math.exp(v) - 1) / (Math.E - 1);
  }

}
