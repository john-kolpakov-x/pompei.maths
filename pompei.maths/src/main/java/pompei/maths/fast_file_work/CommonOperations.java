package pompei.maths.fast_file_work;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommonOperations {

  String dir = "build/performance";

  File file1 = new File(dir + "/file1.txt");
  File file2 = new File(dir + "/file2.txt");
  File file3 = new File(dir + "/file3.txt");
  File file4 = new File(dir + "/file4.txt");

  File viewFile1 = new File(dir + "/file1.view.txt");
  File viewFile2 = new File(dir + "/file2.view.txt");
  File viewFile3 = new File(dir + "/file3.view.txt");
  File viewFile4 = new File(dir + "/file4.view.txt");

  private String rocksDir = "build/rocksDbs";

  File rocksDbFile1 = new File(rocksDir + "/file1.db");
  File rocksDbFile2 = new File(rocksDir + "/file2.db");
  File rocksDbFile3 = new File(rocksDir + "/file3.db");
  File rocksDbFile4 = new File(rocksDir + "/file4.db");

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

  protected void stopPrintThread() throws InterruptedException {
    see.set(false);
    t.interrupt();
    t.join();
    print.set(false);
  }

  protected void startPrintThread() {
    see.set(true);
    print.set(false);
    t = new Thread(r);
    t.start();
  }
}
