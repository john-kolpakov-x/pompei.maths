package pompei.maths.syms2.model.display;

public class Size {
  public int top, bottom, width;

  public Size(int top, int bottom, int width) {
    this.top = top;
    this.bottom = bottom;
    this.width = width;
  }

  public Size() {
  }

  public Size(Size size) {
    if (size != null) copyFrom(size);
  }

  public Size copy() {
    return new Size(this);
  }

  public final Size copyFrom(Size size) {
    if (size == null) return this;
    top = size.top;
    bottom = size.bottom;
    width = size.width;
    return this;
  }

  public int height() {
    return top + bottom;
  }
}
