package pompei.maths.fast_file_work;

import java.io.*;
import java.nio.charset.StandardCharsets;
import org.rocksdb.BuiltinComparator;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;


import static java.lang.System.nanoTime;
import static pompei.maths.utils.Conv.nanoToSec;

public class ScanRockDb extends CommonOperations {
  public static void main(String[] args) throws Exception {
    new ScanRockDb().execute();
  }

  private void execute() throws Exception {
    viewRocksDb(viewFile1, rocksDbFile1);
    viewRocksDb(viewFile2, rocksDbFile2);
    viewRocksDb(viewFile3, rocksDbFile3);
    viewRocksDb(viewFile4, rocksDbFile4);
  }

  private void viewRocksDb(File viewFile, File rocksDbFile) throws Exception {
    try (Options options = new Options()) {
      options.setComparator(BuiltinComparator.BYTEWISE_COMPARATOR);

      try (RocksDB rocksDB1 = RocksDB.open(options, rocksDbFile.getAbsolutePath())) {

        try (RocksIterator rocksIterator = rocksDB1.newIterator()) {

          rocksIterator.seekToFirst();

          startPrintThread();
          int printLineCount = 0;
          long startPrintTime = nanoTime();
          System.out.println("Start print to file " + viewFile.getName() + " ...");

          viewFile.getParentFile().mkdirs();
          FileOutputStream fileOutputStream = new FileOutputStream(viewFile);
          OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
          BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
          try (PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            while (rocksIterator.isValid()) {

              String key = new String(rocksIterator.key(), StandardCharsets.UTF_8);
              String content = new String(rocksIterator.value(), StandardCharsets.UTF_8);
              rocksIterator.next();

              printWriter.println(key + ' ' + content);
              printLineCount++;

              if (print.getAndSet(false)) {
                System.out.println("--- printed " + printLineCount + " to " + viewFile.getName()
                  + " for " + nanoToSec(startPrintTime, nanoTime()));
              }

            }
          }

          long endPrintTime = nanoTime();
          stopPrintThread();

          System.out.println("TOTAL: Printed " + printLineCount + " to " + viewFile.getName()
            + " for " + nanoToSec(startPrintTime, endPrintTime));
        }
      }
    }
  }
}
