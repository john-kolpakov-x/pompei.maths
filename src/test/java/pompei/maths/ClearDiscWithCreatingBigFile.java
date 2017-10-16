package pompei.maths;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class ClearDiscWithCreatingBigFile {
  public static void main(String[] args) throws Exception {
    System.out.println("Start");


    byte[] buffer = new byte[1024 * 1024 * 1024];

    System.out.println("Buffer has been allocated");

    Random random = new Random();
    random.nextBytes(buffer);

    System.out.println("Buffer has been filled with random data");

    for (int u = 0; u < 10; u++) {

      for (int i = 0; i < 30; i++) {
        File f = new File("/home/pompei/tmp/d/U1_DIR_" + u + "/X_FILE_" + i + ".bin");

        f.getParentFile().mkdirs();
        FileOutputStream outputStream = new FileOutputStream(f, true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 1024 * 8);
        bufferedOutputStream.write(buffer);
        bufferedOutputStream.close();

        System.out.println("Buffer written into a file " + f.getParentFile().getName() + "/" + f.getName());
      }

    }

    System.out.println("FINISH");

  }
}
