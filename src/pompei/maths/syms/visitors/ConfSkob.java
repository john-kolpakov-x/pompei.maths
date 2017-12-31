package pompei.maths.syms.visitors;

public interface ConfSkob {
  double ySizeWidthFactor(int level);
  
  double topSizeFactor(int level);
  
  double bottomSizeFactor(int level);
  
  int minWidth(int level);
}
