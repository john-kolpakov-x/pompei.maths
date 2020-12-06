package pompei.maths.distribution;

import java.io.File;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class CalcPiByMonteCarlo {
  public static void main(String[] args) throws Exception {

    class MyThread extends Thread {
      long count = 0, hit = 0;
      private final AtomicBoolean working;

      public MyThread(AtomicBoolean working) {
        this.working = working;
      }

      @Override
      public void run() {
        Random random = new SecureRandom();
        while (working.get()) {
          for (int i = 0; i < 100_000; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            double d = x * x + y * y;
            if (d < 1) {
              hit++;
            }
            count++;
          }
        }
      }
    }

    List<MyThread> threads = new ArrayList<>();

    AtomicBoolean working = new AtomicBoolean(true);

    for (int u = 0; u < 4; u++) {
      threads.add(new MyThread(working));
    }

    File file = Paths.get("build/working____").toFile();
    file.getParentFile().mkdirs();
    file.createNewFile();

    long startedAt = System.nanoTime();

    threads.forEach(Thread::start);

    while (file.exists()) {
      try {
        Thread.sleep(700);
      } catch (InterruptedException e) {
        break;
      }
    }

    working.set(false);

    for (Thread thread : threads) {
      thread.join();
    }

    long time = System.nanoTime() - startedAt;

    long count = threads.stream().mapToLong(t -> t.count).sum();
    long hit = threads.stream().mapToLong(t -> t.hit).sum();

    System.out.println("count = " + count + ", hit = " + hit + ", pi = " + ((double) hit / (double) count * 4.0));
    System.out.println("time = " + (time / 1e9) + " sec, count/time = " + ((double) count / time * 1e9) + " tries/sec");
  }
}
