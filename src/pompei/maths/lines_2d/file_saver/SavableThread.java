package pompei.maths.lines_2d.file_saver;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SavableThread {

  private final Thread thread;
  private final AtomicBoolean working = new AtomicBoolean(true);

  public SavableThread() {
    thread = new Thread(this::run);
    thread.start();
  }

  public void stop() {
    working.set(false);
    thread.interrupt();
  }

  private void run() {
    while (working.get()) {

      savers.values().forEach(Saver::save);

      try {
        //noinspection BusyWait
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return;
      }

    }
  }

  private final AtomicInteger idNext = new AtomicInteger(1);
  private final ConcurrentHashMap<Integer, Saver> savers = new ConcurrentHashMap<>();

  public void register(Saver saver) {
    var id = idNext.incrementAndGet();
    savers.put(id, saver);
  }

}
