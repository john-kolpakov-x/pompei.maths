package pompei.maths.difur.many_masses;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class ManyMasses {
  public int width, height;
  
  public final Set<Uzel> uzelSet = new HashSet<>();
  public final Set<Svjaz> svjazSet = new HashSet<>();
  
  public void save(PrintStream out) {
    out.println("Size " + width + ' ' + height);
    for (Uzel uzel : uzelSet) {
      out.println(uzel.asStr());
    }
  }
  
  public void load(BufferedReader br) {

    while (true) {
      final String line;
      try {
        line = br.readLine();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      if (line == null) return;
      .........
    }
  }
  
  public BufferedImage print() {
    return null;
  }
}
