package pompei.maths.difur.many_masses;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ManyMassesProbe {
  public static void main(String[] args) throws Exception {
    String filename = "probes/Masses.001.txt";
    ManyMasses mm = new ManyMasses();
    mm.load(filename);
    
    BufferedImage image = mm.print();
    ImageIO.write(image, "png", new File("build/Masses.001.txt.png"));
    
    System.out.println("OK");
  }
}
