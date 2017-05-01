package pompei.maths.difur.many_masses;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pompei.maths.difur.DiffUr;
import pompei.maths.difur.ModelAdapter;

public class ManyMasses {
  public int width, height;

  public final Map<String, Node> nodeMap = new HashMap<>();
  public final Map<String, Connection> connectionMap = new HashMap<>();

  private final NodeSource nodeSource = id -> {
    if (!nodeMap.containsKey(id)) {
      throw new IllegalArgumentException("No node with id = " + id);
    }
    return nodeMap.get(id);
  };

  @SuppressWarnings("unused")
  public void save(PrintStream out) {
    out.println("Size " + width + ' ' + height);
    for (Node node : nodeMap.values()) {
      out.println(node.asStr());
    }
    for (Connection connection : connectionMap.values()) {
      out.println(connection.asStr());
    }
  }

  public void load(String filename) throws IOException {
    load(new FileInputStream(filename));
  }

  @SuppressWarnings("unused")
  public void load(File file) throws IOException {
    load(new FileInputStream(file));
  }

  public void load(InputStream inputStream) throws IOException {
    load(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
  }

  public void load(BufferedReader br) throws IOException {
    nodeMap.clear();
    connectionMap.clear();
    width = height = 0;
    readData(br);
    fillFromToSets();
  }

  private void fillFromToSets() {
    for (Connection s : connectionMap.values()) {
      s.from.fromSet.add(s);
      s.to.toSet.add(s);
    }
  }

  private void readData(BufferedReader br) throws IOException {
    WHILE:
    while (true) {
      final String line = br.readLine();
      if (line == null) {
        br.close();
        return;
      }
      if (line.trim().startsWith("#")) continue WHILE;

      String[] split = line.split("\\s+");
      if ("Size".equals(split[0])) {
        width = Integer.parseInt(split[1]);
        height = Integer.parseInt(split[2]);
        continue WHILE;
      }
      {
        Point point = Point.parse(split);
        if (point != null) {
          addNode(point);
          continue WHILE;
        }
      }
      {
        Ball ball = Ball.parse(split);
        if (ball != null) {
          addNode(ball);
          continue WHILE;
        }
      }
      {
        Elastic elastic = Elastic.parse(split, nodeSource);
        if (elastic != null) {
          addConnection(elastic);
          continue WHILE;
        }
      }
    }
  }

  private void addConnection(Connection connection) {
    connectionMap.put(connection.id, connection);
  }

  private void addNode(Node node) {
    nodeMap.put(node.id, node);
  }

  public BufferedImage print() {
    BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = ret.createGraphics();
    try {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, width, height);
      g.setColor(Color.BLACK);
      g.drawRect(0, 0, width - 1, height - 1);

      for (Connection connection : connectionMap.values()) {
        connection.draw(g);
      }

      for (Node node : nodeMap.values()) {
        node.draw(g);
      }

    } finally {
      g.dispose();
    }
    return ret;
  }

  @SuppressWarnings("unused")
  public ModelAdapter createNewAdapter() {
    final List<Ball> ids = nodeMap.values().stream()
      .filter(node -> node instanceof Ball)
      .map(node -> (Ball) node)
      .collect(Collectors.toList());
    return new ModelAdapter() {
      @SuppressWarnings("unused")
      DiffUr diffUr;

      @Override
      public void writeToModel() {
        throw new UnsupportedOperationException();
      }

      @Override
      public void readFromModel() {
        throw new UnsupportedOperationException();
      }

      @Override
      public void prepare(DiffUr diffUrArg) {
        diffUr = diffUrArg;
        diffUrArg.prepare(ids.size() * 4, (res, t, x) -> {
          throw new UnsupportedOperationException();
        });
      }
    };
  }
}
