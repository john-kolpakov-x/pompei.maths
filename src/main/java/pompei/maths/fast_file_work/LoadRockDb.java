package pompei.maths.fast_file_work;

import java.io.*;
import java.nio.charset.StandardCharsets;
import org.rocksdb.BuiltinComparator;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;


import static java.lang.System.nanoTime;
import static pompei.maths.utils.Conv.nanoToSec;

public class LoadRockDb extends CommonOperations {
  public static void main(String[] args) throws Exception {
    new LoadRockDb().execute();
  }

  private void execute() throws Exception {
    loadFileToDb(rocksDbFile1, file1);
    loadFileToDb(rocksDbFile2, file2);
    loadFileToDb(rocksDbFile3, file3);
    loadFileToDb(rocksDbFile4, file4);
  }

  private void loadFileToDb(File rocksDbFile, File inputFile) throws IOException, RocksDBException, InterruptedException {
    RocksDB.loadLibrary();

    System.out.println("Loading file " + inputFile.getName() + " ...");

    try (Options options = new Options()) {
      options.setCreateIfMissing(true);
      options.setComparator(BuiltinComparator.BYTEWISE_COMPARATOR);

      rocksDbFile.getParentFile().mkdirs();

      try (RocksDB rocksDB1 = RocksDB.open(options, rocksDbFile.getAbsolutePath())) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
          new FileInputStream(inputFile), "UTF-8"))) {

          startPrintThread();

          long startLoadTime = nanoTime();
          int recordsMerged = 0, updateCount = 0, insertCount = 0;

          while (true) {
            String line = br.readLine();
            if (line == null) break;

            int space = line.indexOf(' ');
            if (space < 0) continue;

            String key = line.substring(0, space);
            String content = line.substring(space + 1);

            {
              byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
              byte[] currentContentBytes = rocksDB1.get(keyBytes);
              if (currentContentBytes == null) {
                insertCount++;
              } else {
                content = content + '|' + new String(currentContentBytes, StandardCharsets.UTF_8);
                updateCount++;
              }
              rocksDB1.put(keyBytes, content.getBytes(StandardCharsets.UTF_8));
              recordsMerged++;
            }

            if (print.getAndSet(false)) {
              System.out.println("--- " + inputFile.getName() + ": merged " + recordsMerged + ", inserted "
                + insertCount + ", updated " + updateCount + " record for " + nanoToSec(startLoadTime, nanoTime()));
            }

          }

          long endLoadTime = nanoTime();

          stopPrintThread();

          System.out.println("TOTAL: " + inputFile.getName() + ": merged " + recordsMerged + ", inserted "
            + insertCount + ", updated " + updateCount + " record for " + nanoToSec(startLoadTime, endLoadTime));
        }
      }
    }
  }
}
