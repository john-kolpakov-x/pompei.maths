package pompei.maths.syms.visitors;

public class PaintSize {
  int w, h1, h2;
  
  public PaintSize() {
    w = h1 = h2 = 0;
  }
  
  public PaintSize(int w, int h1, int h2) {
    this.w = w;
    this.h1 = h1;
    this.h2 = h2;
  }
  
  void expandOnRight(PaintSize size) {
    w += size.w;
    if (h1 < size.h1) h1 = size.h1;
    if (h2 < size.h2) h2 = size.h2;
  }
}