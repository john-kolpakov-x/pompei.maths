package pompei.maths.serialization;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProbeZipping {
  public static void main(String[] args) throws Exception {
    new ProbeZipping().exec();
  }

  private void exec() throws Exception {
    File zipFile = new File("out/archive/archive-from-java.zip");
    zipFile.getParentFile().mkdirs();

    try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFile))) {

      AtomicInteger i = new AtomicInteger(1);
      String dir = "/home/pompei/tmp/spring-framework";
      Files.walk(Paths.get(dir))
          .filter(Files::isRegularFile)
          .filter(p -> {
            String name = p.toFile().getName();
            return name.endsWith(".java") || name.endsWith(".kt");
          })
          .sorted(Comparator.comparing(p -> p.toFile().getAbsolutePath()))
          .forEach(p -> {
            String path = p.toFile().getAbsolutePath().substring(dir.length() + 1);
            StringBuilder sb = new StringBuilder("" + i.getAndIncrement());
            while (sb.length() < 5) sb.insert(0, "0");
            sb.append(" --- ");
            sb.append(path);
            System.out.println(sb);

            try {
              zip.putNextEntry(new ZipEntry(path));
              FileUtils.copyFile(p.toFile(), zip);
              zip.closeEntry();
            } catch (IOException e) {
              throw new RuntimeException(e);
            }

          });

    }


  }
}
