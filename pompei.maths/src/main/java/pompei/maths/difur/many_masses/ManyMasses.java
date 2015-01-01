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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pompei.maths.difur.DiffUr;
import pompei.maths.difur.F;
import pompei.maths.difur.ModelAdapter;

public class ManyMasses {
  public int width, height;
  
  public final Map<String, Uzel> uzelMap = new HashMap<>();
  public final Map<String, Svjaz> svjazMap = new HashMap<>();
  
  private final UzelSource uzelSource = new UzelSource() {
    @Override
    public Uzel getUzelById(String id) {
      if (!uzelMap.containsKey(id)) {
        throw new IllegalArgumentException("No uzel with id = " + id);
      }
      return uzelMap.get(id);
    }
  };
  
  public void save(PrintStream out) {
    out.println("Size " + width + ' ' + height);
    for (Uzel uzel : uzelMap.values()) {
      out.println(uzel.asStr());
    }
    for (Svjaz svjaz : svjazMap.values()) {
      out.println(svjaz.asStr());
    }
  }
  
  public void load(String filename) throws IOException {
    load(new FileInputStream(filename));
  }
  
  public void load(File file) throws IOException {
    load(new FileInputStream(file));
  }
  
  public void load(InputStream inputStream) throws IOException {
    load(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
  }
  
  public void load(BufferedReader br) throws IOException {
    uzelMap.clear();
    svjazMap.clear();
    width = height = 0;
    readData(br);
    fillFromToSets();
  }
  
  private void fillFromToSets() {
    for (Svjaz s : svjazMap.values()) {
      s.from.fromSet.add(s);
      s.to.toSet.add(s);
    }
  }
  
  private void readData(BufferedReader br) throws IOException {
    WHILE: while (true) {
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
          addUzel(point);
          continue WHILE;
        }
      }
      {
        Shar shar = Shar.parse(split);
        if (shar != null) {
          addUzel(shar);
          continue WHILE;
        }
      }
      {
        Rezinka rezinka = Rezinka.parse(split, uzelSource);
        if (rezinka != null) {
          addSvjaz(rezinka);
          continue WHILE;
        }
      }
    }
  }
  
  private void addSvjaz(Svjaz svjaz) {
    svjazMap.put(svjaz.id, svjaz);
  }
  
  private void addUzel(Uzel uzel) {
    uzelMap.put(uzel.id, uzel);
  }
  
  public BufferedImage print() {
    BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = ret.createGraphics();
    try {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, width, height);
      g.setColor(Color.BLACK);
      g.drawRect(0, 0, width - 1, height - 1);
      
      for (Svjaz svjaz : svjazMap.values()) {
        svjaz.draw(g);
      }
      
      for (Uzel uzel : uzelMap.values()) {
        uzel.draw(g);
      }
      
    } finally {
      g.dispose();
    }
    return ret;
  }
  
  public ModelAdapter createNewAdapter() {
    final List<Shar> ids = new ArrayList<>();
    for (Uzel uzel : uzelMap.values()) {
      if (uzel instanceof Shar) {
        ids.add((Shar)uzel);
      }
    }
    return new ModelAdapter() {
      @SuppressWarnings("unused")
      DiffUr diffUr;
      
      @Override
      public void writeToModel() {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void readFromModel() {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void prepare(DiffUr diffUrArg) {
        diffUr = diffUrArg;
        diffUrArg.prepare(ids.size() * 4, new F() {
          @Override
          public void f(double[] res, double t, double[] x) {
            
          }
        });
      }
    };
  }
}
