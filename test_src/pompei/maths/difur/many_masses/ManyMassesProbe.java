package pompei.maths.difur.many_masses;

import javax.imageio.ImageIO;
import java.io.File;

public class ManyMassesProbe {
  public static void main(String[] args) throws Exception {
    ManyMasses mm = new ManyMasses();

    mm.load("probes/Masses.001.txt");

    ImageIO.write(mm.print(), "png", new File("build/Masses.001.txt.png"));

    System.out.println("OK");
  }
}
