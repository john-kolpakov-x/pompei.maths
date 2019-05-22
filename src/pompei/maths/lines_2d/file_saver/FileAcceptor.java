package pompei.maths.lines_2d.file_saver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileAcceptor<T> implements Acceptor<T> {

  private final Path file;
  private final ObjectSerializer<T> serializer;

  public FileAcceptor(Path file, ObjectSerializer<T> serializer) {
    this.file = file;
    this.serializer = serializer;
  }

  @Override
  public T getValueOrNull() {
    if (!Files.exists(file)) {
      return null;
    }

    try {
      return serializer.fromStr(Files.readString(file));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void set(Optional<T> optional) {

    optional.ifPresentOrElse(t -> {
      file.toFile().getParentFile().mkdirs();
      try {
        Files.writeString(file, serializer.toStr(t));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }, () -> {
      try {
        Files.delete(file);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

  }
}
