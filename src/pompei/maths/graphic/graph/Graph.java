package pompei.maths.graphic.graph;

import java.awt.Color;

public interface Graph {

  int funcCount();

  double f(int n, double x);

  Color color(int n);

}
