package pompei.maths.grav_masses;

import java.awt.Color;
import java.io.File;

public class TwoMasses extends GravitySystem {
  public TwoMasses() {
    super(2);
  }

  public static void main(String[] args) throws Exception {
    TwoMasses twoMasses = new TwoMasses();
    twoMasses.init();
    twoMasses.exec();
  }

  private void init() {
    reset(0, 0.00001);

    masses[0] = 10_000;
    masses[1] = 1;

    setPlace(0, 0, 0, 0);
    setPlace(1, 20, 0, 0);

    setVelocity(0, 0, 0, 0);
    setVelocity(1, 0, 22.4, 0);

    stopGlobalMoving();
  }

  private void exec() throws Exception {
    int stepCount = 0;
    double dt = 1.0 / 25.0;
    double t = 0;
    int imageIndex = 0;
    while (diffUr.getT() < 300) {
      if (diffUr.getT() >= t) {
        t += dt;
        File file = paintPictureTo(imageIndex++);
        System.out.println("file = " + file + ", stepCount = " + stepCount + ", t = " + diffUr.getT());
      }
      stepCount++;
      diffUr.step();
    }

    System.out.println("TOTAL stepCount = " + stepCount);
  }


  private File paintPictureTo(int imageIndex) throws Exception {
    StringBuilder I = new StringBuilder(5);
    I.append(imageIndex);
    while (I.length() < 5) I.insert(0, "0");
    File file = new File("build/two_masses/img_" + I + ".png");
    file.getParentFile().mkdirs();

    PicturePainter picturePainter = new PicturePainter();
    picturePainter.w2 = 25;
    picturePainter.W = 800;
    picturePainter.H = 800;
    picturePainter.initPicture();

    picturePainter.g.setColor(Color.BLACK);
    picturePainter.paintPoint(getPlace(0));
    picturePainter.g.setColor(Color.BLUE);
    picturePainter.paintPoint(getPlace(1));

    picturePainter.saveTo(file);
    return file;
  }
}
