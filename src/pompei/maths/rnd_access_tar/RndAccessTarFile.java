package pompei.maths.rnd_access_tar;


import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Objects.requireNonNull;

public class RndAccessTarFile implements Closeable {
  private final File file;

  public RndAccessTarFile(Path filePath) {
    file = filePath.toFile();
    if (!file.exists()) {
      file.getParentFile().mkdirs();
      try {
        file.createNewFile();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private BytesPos save0(byte[] bytes) throws IOException {
    BytesPos ret;

    try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw")) {
      try (FileChannel channel = accessFile.getChannel()) {

        while (true) {
          try (FileLock ignore = channel.lock()) {

            long offset = accessFile.length();
            long newLength = offset + bytes.length;
            ret = new BytesPos(offset, bytes.length);

            accessFile.setLength(newLength);
            break;
          } catch (OverlappingFileLockException ignore) {}
        }

      }
    }

    try (FileChannel channel = FileChannel.open(file.toPath(), READ, WRITE)) {

      ByteBuffer buffer = ByteBuffer.wrap(bytes);

      channel.write(buffer, ret.offset);

    }

    return requireNonNull(ret);
  }

  public BytesPos save(byte[] bytes) {
    try {
      return save0(bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<byte[]> load0(BytesPos position) throws IOException {
    if (position.count == 0) {
      return Optional.empty();
    }

    try (FileChannel channel = FileChannel.open(file.toPath(), READ)) {

      byte[] bytes = new byte[(int) position.count];

      ByteBuffer buffer = ByteBuffer.wrap(bytes);

      channel.read(buffer, position.offset);

      return Optional.of(bytes);
    }

  }

  public Optional<byte[]> load(BytesPos position) {
    try {
      return load0(position);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() {}
}
