package pompei.maths.rnd_access_tar;

import org.testng.annotations.Test;
import pompei.maths.utils.RND;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

import static org.fest.assertions.Assertions.assertThat;

public class RndAccessTarFileTest {
  @Test
  public void save_load___fromManyThreads() throws IOException, InterruptedException {

    Path pwd = new File(".").getAbsoluteFile().toPath().normalize();

    Path workingDir = pwd.resolve("build").resolve("RndAccessTarFileTest");

    workingDir.toFile().mkdirs();

    Path filePath = workingDir.resolve(RND.str(10));

    filePath.toFile().createNewFile();

    try (var rndAccessTarFile = new RndAccessTarFile(filePath)) {

      int N = 20;
      byte[][] bytesArray = new byte[N][];
      byte[][] readBytesArray = new byte[N][];
      BytesPos[] positions = new BytesPos[N];
      Thread[] threads = new Thread[N];

      for (int i = 0; i < N; i++) {
        bytesArray[i] = RND.bytes(RND.plusInt(10) + 1);
        int I = i;
        threads[i] = new Thread(
          () -> positions[I] = rndAccessTarFile.save(bytesArray[I])
        );
      }

      for (int i = 0; i < N; i++) {
        threads[i].start();
      }
      for (int i = 0; i < N; i++) {
        threads[i].join();
      }

      for (int i = 0; i < N; i++) {
        readBytesArray[i] = rndAccessTarFile.load(positions[i]).orElseThrow();
      }

      for (int i = 0; i < N; i++) {
        assertThat(readBytesArray[i])
          .describedAs("i = " + i)
          .isEqualTo(bytesArray[i]);
      }

    }
  }

  @Test
  public void checkChanel() throws IOException {
    Path pwd = new File(".").getAbsoluteFile().toPath().normalize();

    Path workingDir = pwd.resolve("build").resolve("RndAccessTarFileTest");
    workingDir.toFile().mkdirs();

    Path filePath = workingDir.resolve(RND.str(10));

    filePath.toFile().createNewFile();

    try (var accessFile = new RandomAccessFile(filePath.toFile(), "rw")) {

      accessFile.setLength(1000);

    }
  }

  @Test
  public void save_load___oneThread() throws IOException {

    Path pwd = new File(".").getAbsoluteFile().toPath().normalize();

    Path workingDir = pwd.resolve("build").resolve("RndAccessTarFileTest");

    workingDir.toFile().mkdirs();

    Path filePath = workingDir.resolve(RND.str(10));

    filePath.toFile().createNewFile();

    try (var rndAccessTarFile = new RndAccessTarFile(filePath)) {

      int N = 17;
      byte[][] bytesArray = new byte[N][];
      byte[][] readBytesArray = new byte[N][];
      BytesPos[] positions = new BytesPos[N];

      for (int i = 0; i < N; i++) {
        bytesArray[i] = RND.bytes(RND.plusInt(10) + 1);
        positions[i] = rndAccessTarFile.save(bytesArray[i]);
      }

      for (int i = 0; i < N; i++) {
        readBytesArray[i] = rndAccessTarFile.load(positions[i]).orElseThrow();
      }

      for (int i = 0; i < N; i++) {
        assertThat(readBytesArray[i])
          .describedAs("i = " + i)
          .isEqualTo(bytesArray[i]);
      }

    }
  }
}
