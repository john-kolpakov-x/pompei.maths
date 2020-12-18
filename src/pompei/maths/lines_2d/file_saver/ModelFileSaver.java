package pompei.maths.lines_2d.file_saver;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ModelFileSaver implements Saver {

  private final Object savableObject;
  private final File saveFile;

  public ModelFileSaver(Object savableObject, File saveFile) {
    this.savableObject = savableObject;
    this.saveFile = saveFile;
  }

  private final AtomicBoolean initiated = new AtomicBoolean(false);

  private Map<String, FieldAcceptor> acceptorMap;

  public void init() {
    acceptorMap =
        FieldAcceptorCollect.collect(savableObject)
                            .stream()
                            .collect(Collectors.toMap(FieldAcceptor::name, x -> x));

    if (saveFile.exists()) {
      try {
        for (String line : Files.readAllLines(saveFile.toPath())) {
          if (line.trim().startsWith("#")) {
            continue;
          }
          if (line.trim().length() == 0) {
            continue;
          }
          var i = line.indexOf('=');
          if (i < 0) {
            continue;
          }
          String key = line.substring(0, i).trim();
          String strValue = line.substring(i + 1).trim();
          var fieldAcceptor = acceptorMap.get(key);
          if (fieldAcceptor != null) {
            fieldAcceptor.write(strValue);
          }
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    initiated.set(true);
    save();
  }

  @Override
  public void save() {
    if (!initiated.get()) {
      return;
    }

    if (!saveFile.exists()) {
      saveFile.getParentFile().mkdirs();
    }

    try (var printStream = new PrintStream(saveFile, StandardCharsets.UTF_8)) {
      for (FieldAcceptor fieldAcceptor : acceptorMap.values()) {
        printStream.println(fieldAcceptor.name() + '=' + fieldAcceptor.read());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

}
