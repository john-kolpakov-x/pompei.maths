package pompei.maths.video;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

public class СatcherToImgFiles {
  private final static String ZZ = "000000000000000000000000000000000000000000000";
  private final static int I_LEN = 3;

  public static void main(String[] args) throws Exception {
    String dir = "build/001";
    new File(dir).mkdirs();

    Robot ro = new Robot();

    Rectangle rec = new Rectangle(433, 205, 857, 483);

    //noinspection InfiniteLoopStatement
    for (int i = 0; ; i++) {
      String I = "" + i;
      if (I.length() < I_LEN) {
        I = ZZ.substring(0, I_LEN - I.length()) + i;
      }
      BufferedImage img = ro.createScreenCapture(rec);
      ImageIO.write(img, "png", new File(dir + "/asd" + I + ".png"));

      Thread.sleep(1000 / 24);

      if (i % 100 == 0) {
        System.out.println("Outed " + I);
      }
    }
  }
}
