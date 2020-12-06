package pompei.maths.fast_file_work;

import pompei.maths.utils.RND;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.nanoTime;
import static pompei.maths.utils.Conv.nanoToSec;

public class PrepareFiles extends CommonOperations {
  public static void main(String[] args) throws Exception {
    new PrepareFiles().execute();
  }

  static final int N = 2_500_0;
  static final int CONTENT_LEN = 10;

  private void execute() throws Exception {
    final List<String> idList = new ArrayList<>();

    startPrintThread();

    final long startFillListTime = nanoTime();

    for (int i = 0; i < N; i++) {
      idList.add(RND.str(32));
      if (i % 100 == 0 && print.get()) {
        print.set(false);
        System.out.println("--- filled " + i + " ids for " + nanoToSec(startFillListTime, nanoTime()));
      }
    }

    final long endFillListTime = nanoTime();

    stopPrintThread();

    System.out.println("TOTAL: Filled " + N + " ids for " + nanoToSec(startFillListTime, endFillListTime));


    sub_shuffle_saveToFile(idList, file1, "SubIdList #1");
    sub_shuffle_saveToFile(idList, file2, "SubIdList #2");
    sub_shuffle_saveToFile(idList, file3, "SubIdList #3");
    sub_shuffle_saveToFile(idList, file4, "SubIdList #4");

    System.out.println("FINISH");
  }


  private void sub_shuffle_saveToFile(List<String> idList, File file, String subIdListName) throws Exception {
    List<String> subIdList = new ArrayList<>();
    long startFillSubIdList, endFillSubIdList, startShuffleSubIdList, endShuffleSubIdList;

    startFillSubIdList = nanoTime();
    for (String id : idList) {

      int n = RND.plusInt(3);
      if (n == 2) n++;
      for (int i = 0; i < n; i++) {
        subIdList.add(id);
      }
    }
    endFillSubIdList = nanoTime();

    System.out.println(subIdListName + " size = " + subIdList.size() + " filled for "
      + nanoToSec(startFillSubIdList, endFillSubIdList));

    startShuffleSubIdList = nanoTime();
    Collections.shuffle(subIdList);
    endShuffleSubIdList = nanoTime();
    System.out.println("Shuffled " + subIdListName + " for " + nanoToSec(startShuffleSubIdList, endShuffleSubIdList));

    startPrintThread();

    int printCount = 0;

    file.getParentFile().mkdirs();
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
    try (PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

      long startPrintTime = nanoTime();

      for (String id : subIdList) {
        printWriter.println(id + " " + RND.str(CONTENT_LEN));
        printCount++;

        if (print.getAndSet(false)) {
          System.out.println("--- printed " + printCount + " records in " + file.getName()
            + " for " + nanoToSec(startPrintTime, nanoTime()));
        }

      }

      System.out.println("TOTAL: Printed " + printCount + " records in " + file.getName()
        + " for " + nanoToSec(startPrintTime, nanoTime()));

    }

    stopPrintThread();
  }
}
