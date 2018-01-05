package pompei.maths.end_element;

import java.util.ArrayList;
import java.util.List;

public class Cell {
  public final double x1, y1, x2, y2;

  public int p1, p2, p3, p4;

  public final List<SideRef> sides = new ArrayList<>();

  public Cell(double x1, double y1, double x2, double y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  public double V;//объём

  public double ro;//плотность
  public double p;//давление
  public double vx, vy;//скорость

  public void step(Context c) {
    double dm = 0, dqx = 0, dqy = 0;

    for (SideRef side : sides) {
      Side s = side.side;
      double dqp = s.dqp;
      double dqn = s.dqn * (side.right ? +1 : -1);
      dm += s.dm;
      dqx += dqp * s.px + dqn * s.nx;
      dqy += dqp * s.py + dqn * s.ny;
    }

    double roOld = ro;
    double mOld = roOld * V;
    double pOld = p;
    double qxOld = mOld * vx, qyOld = mOld * vy;

    double mNew = mOld + dm;
    double qxNew = qxOld + dqx, qyNew = qyOld + dqy;
    double roNew = mNew / V;
    double pNew = pOld * roNew / roOld;//p * ro == const
  }
}
