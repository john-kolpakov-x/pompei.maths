package pompei.maths.mendeleev_table;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GenerateTablePng {
  public static void main(String[] args) throws IOException {
    BufferedImage image = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);

    MendeleevTable table = new MendeleevTable();

    Graphics2D graphics = image.createGraphics();
    table.paintTo(graphics);
    graphics.dispose();

    File output = new File("build/mendeleev_table.png");
    output.getParentFile().mkdirs();
    ImageIO.write(image, "png", output);
  }
}
