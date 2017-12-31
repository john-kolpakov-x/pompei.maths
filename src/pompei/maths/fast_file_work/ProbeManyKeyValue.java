package pompei.maths.fast_file_work;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import pompei.maths.utils.RND;


import static pompei.maths.utils.Conv.nanoToSec;

public class ProbeManyKeyValue {
  public static void main(String[] args) throws Exception {
    new ProbeManyKeyValue().execute();
  }

  private void execute() throws Exception {
    final int N = 2_500_000;

    final AtomicBoolean print = new AtomicBoolean(false), see = new AtomicBoolean(false);

    Thread t;
    final Runnable r = () -> {
      while (see.get()) {
        try {
          Thread.sleep(10_000);
        } catch (InterruptedException e) {
          break;
        }
        print.set(true);
      }
    };

    final List<String> list = new ArrayList<>();

    see.set(true);
    print.set(false);
    t = new Thread(r);
    t.start();

    final long startFillListTime = System.nanoTime();

    for (int i = 0; i < N; i++) {
      list.add(RND.str(320));
      if (i % 100 == 0 && print.get()) {
        print.set(false);
        System.out.println("--- filled " + i + " ids for " + nanoToSec(startFillListTime, System.nanoTime()));
      }
    }

    final long endFillListTime = System.nanoTime();

    System.out.println("TOTAL: Filled " + N + " ids for " + nanoToSec(startFillListTime, endFillListTime));

    see.set(false);
    t.interrupt();
    t.join();

    long sortStartTime = System.nanoTime();

    list.sort(Comparator.comparing(x -> x));

    long sortEndTime = System.nanoTime();

    System.out.println("list.size() = " + list.size() + ", sorted for " + nanoToSec(sortStartTime, sortEndTime));
  }
}
