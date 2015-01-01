package pompei.maths.difur.many_masses;

import java.io.File;

import javax.imageio.ImageIO;

public class ManyMassesProbe {
  public static void main(String[] args) throws Exception {
    ManyMasses mm = new ManyMasses();
    
    mm.load("probes/Masses.001.txt");
    
    ImageIO.write(mm.print(), "png", new File("build/Masses.001.txt.png"));
    
    System.out.println("OK");
  }
}
