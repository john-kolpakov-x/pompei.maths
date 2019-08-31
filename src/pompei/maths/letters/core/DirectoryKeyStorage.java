package pompei.maths.letters.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryKeyStorage implements KeyStorage {

  private final Path root;
  private final String valueExtension;

  public DirectoryKeyStorage(Path root, String valueExtension) {
    this.root = root;
    this.valueExtension = valueExtension;
  }

  public DirectoryKeyStorage(Path root) {
    this(root, ".txt");
  }

  private Path keyPath(String key) {
    while (key != null && key.length() > 0 && key.startsWith("/")) {
      key = key.substring(1);
    }
    if (key != null && key.length() == 0) {
      key = null;
    }

    if (key == null) {
      throw new RuntimeException("No key");
    }

    return root.resolve(key + valueExtension);
  }

  @Override
  public String load(String key) {

    Path path = keyPath(key);

    if (!Files.exists(path)) {
      return null;
    }

    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void save(String key, String value) {
    Path path = keyPath(key);

    if (value == null) {

      if (Files.exists(path)) {
        try {
          Files.delete(path);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      return;
    }

    path.toFile().getParentFile().mkdirs();
    try {
      Files.writeString(path, value);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
