package pompei.maths.syms_diff.visitors.paint;

public class Size {
  public final int width, heightTop, heightBottom;

  public Size(int width, int heightTop, int heightBottom) {
    this.width = width;
    this.heightTop = heightTop;
    this.heightBottom = heightBottom;
  }

  public int height() {
    return heightTop + heightBottom;
  }
}
