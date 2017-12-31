package pompei.maths.end_element;

public class ConversionBuilder {

  private final int offsetX;
  private final int offsetY;
  private final int width;
  private final int height;

  public static ConversionBuilder rect(int offsetX, int offsetY, int width, int height) {
    return new ConversionBuilder(offsetX, offsetY, width, height);
  }

  private ConversionBuilder(int offsetX, int offsetY, int width, int height) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.width = width;
    this.height = height;
  }

  public class FromLeftBottom {
    private final double leftBottomX;
    private final double leftBottomY;

    private FromLeftBottom(double leftBottomX, double leftBottomY) {
      this.leftBottomX = leftBottomX;
      this.leftBottomY = leftBottomY;
    }

    public Conversion toRightTop(double rightTopX, double rightTopY) {
      return new Conversion() {
        double dWidth = rightTopX - leftBottomX;

        @Override
        public double convertX(double x) {
          double t = (x - leftBottomX) / dWidth;
          return (double) offsetX + t * width;
        }

        double dHeight = rightTopY - leftBottomY;

        @Override
        public double convertY(double y) {
          double t = (y - leftBottomY) / dHeight;
          return (double) offsetY + height - t * height;
        }
      };
    }
  }

  public FromLeftBottom fromLeftBottom(double x, double y) {
    return new FromLeftBottom(x, y);
  }
}
