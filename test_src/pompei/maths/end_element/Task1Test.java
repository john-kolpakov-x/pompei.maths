package pompei.maths.end_element;

import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Task1Test {

  static abstract class AbstractPrinter {
    void print() throws Exception {
      Scene scene = new Scene();

      Task1 t = new Task1(scene);
      t.createField(5, 5, 15, 7);

      final int nx = 15, ny = 7;
      final double cellSize = 5;
      final int cellSIZE = 80;
      final int dist = 10;

      final int width = 2 * dist + nx * cellSIZE, height = 2 * dist + ny * cellSIZE;
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      {
        Graphics2D g = image.createGraphics();

        g.setRenderingHints(new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        ));
        g.setRenderingHints(new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        ));


        g.setBackground(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.black);

        double W = nx * cellSize, H = ny * cellSize;

        Conversion conversion = ConversionBuilder.rect(dist, dist, width - 2 * dist, height - 2 * dist)
            .fromLeftBottom(0, 0)
            .toRightTop(W, H);

        GraphicsDraw draw = new GraphicsDraw(g, conversion);

        draw(scene, draw);

        g.dispose();
      }

      File output = outPngFile();
      output.getParentFile().mkdirs();
      ImageIO.write(image, "png", output);
      System.out.println("OK");
    }

    protected abstract File outPngFile();

    protected abstract void draw(Scene scene, GraphicsDraw draw);
  }

  static class SidePrinter extends AbstractPrinter {
    protected void draw(Scene scene, GraphicsDraw draw) {
      scene.drawSides(draw);
    }

    protected File outPngFile() {
      return new File("build/task1_sides.png");
    }
  }

  @Test
  public void printSides() throws Exception {
    new SidePrinter().print();
  }

  static class CellPrinter extends AbstractPrinter {
    protected void draw(Scene scene, GraphicsDraw draw) {
      scene.drawCells(draw, new DrawCellsFilter() {
        @Override
        public boolean drawCell(Cell cell, int cellIndex) {
          return true;
        }

        @Override
        public boolean drawSideWithIndex(Cell cell, int sideIndex) {
          return true;
        }

        @Override
        public boolean drawPointLabels(Cell cell) {
          return false;
        }

        @Override
        public boolean drawCellLabel(Cell cell, int cellIndex) {
          return true;
        }
      });
    }

    protected File outPngFile() {
      return new File("build/task1_cells.png");
    }
  }

  @Test
  public void printCells() throws Exception {
    new CellPrinter().print();
  }
}