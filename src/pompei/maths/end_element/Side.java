package pompei.maths.end_element;

public class Side {
  public final int p1;
  public final int p2;
  public final SideType type;

  public SideRef leftRef, rightRef;

  public Side(int p1, int p2, SideType type) {
    this.p1 = p1;
    this.p2 = p2;
    this.type = type;
  }

  //геометрические размеры
  public double S;//площать грани
  public double px, py;//направление вдоль грани (единичный вектор)
  public double nx, ny;//нормаль - направление поперёк грани: вправо, если смотреть по {px, py}

  //передача слева-на-право (+) (и справа-на-лево (-))

  public double dm;// переданное количество массы вещества
  public double dqn, dqp;// переданное количество движения: n - по нормали, p - вдоль
  //Рельный импульс считается так:
  //   dqx = dqn * nx + dqp * px
  //   dqy = dqn * ny + dqp * py
  public double dU;//передаваемое количество внутренней энергии / i (i - коэффициент степени свободы)

  public void step(Context c) {
    dm = dqn = dqp = dU = 0;

    switch (type) {
      case WORKING:
        stepWorking(c);
        return;

      case LEFT_WALL:
        stepLeftWall(c);
        return;

      case RIGHT_WALL:
        stepRightWall(c);
        return;

      case LEFT_INPUT:
        stepLeftInput(c);
        return;

      default:
        throw new RuntimeException("Unknown SideType = " + type);
    }

  }

  private void stepWorking(Context c) {
    //простое движение
    {
      double roL = leftRef.owner.ro;
      double roR = rightRef.owner.ro;

      double pL = leftRef.owner.p;
      double pR = rightRef.owner.p;

      double vxL = leftRef.owner.vx, vyL = leftRef.owner.vy;
      double vxR = rightRef.owner.vx, vyR = rightRef.owner.vy;

      //Проекция скорости на нормаль
      double vnL = vxL * nx + vyL * ny;//слева
      double vnR = vxR * nx + vyR * ny;//справа

      //объём газа перешедшего слева-на-право
      double dVL = vnL * c.dt * S;//с точки зрения L
      double dVR = vnR * c.dt * S;//с точки зрения R

      //масса газа перешедьшего слева-на-право
      double dmL = dVL * roL;//с точки зрения L
      double dmR = dVR * roR;//с точки зрения R

      //внутренняя энергия-i, перешедьшая слева-на-право
      double dUL = dVL * pL;//с точки зрения L
      double dUR = dVR * pR;//с точки зрения L

      //импульс перешедьший с этой массой
      double dmL_qx = vxL * dmL, dmL_qy = vyL * dmL;//с точки зрения L
      double dmR_qx = vxL * dmR, dmR_qy = vyR * dmL;//с точки зрения R

      //берём средние значения
      double dmLR = (dmL + dmR) / 2;//Количество массы перешедьшее слева-на-право в результате движения
      double dm_qx = (dmL_qx + dmR_qx) / 2, dm_qy = (dmL_qy + dmR_qy) / 2;//Переданное количество движения
      double dULR = (dUL + dUR) / 2;//Количество внутренней энергии-i

      dm += dmLR;
      dqn += dm_qx * nx + dm_qy * ny;
      dqp += dm_qx * px + dm_qy * py;
      dU += dULR;

      //давление
      {
        //разница давлений (слева-на-право)
        double delta_p = pL - pR;
        //сила возникающая в результате разницы этих давлений
        double Fn = delta_p * S;
        //импульс, который успевает передаваться за квант времени
        double dqn_Fn = Fn * c.dt;

        dqn += dqn_Fn;
      }

      //вязкость
      {
        //относительная скорость
        double delta_vx = vxR - vxL, delta_vy = vyR - vyL;

        //продольная проекция относительной скорости
        double delta_vp = delta_vx * px + delta_vy * py;

        double pM = (pL + pR) / 2;

        //импульс трения
        double dqp_NU = S * delta_vp * c.mu(pM);

        dqp -= dqp_NU;
      }
    }

  }

  private void stepLeftWall(Context c) {
    double vxR = rightRef.owner.vx, vyR = rightRef.owner.vy;
    double vpR = vxR * px + vyR * py;

    //импульс трения о стенку
    double dqp_NU = S * vpR * c.muWall(rightRef.owner.p);

    dqp -= dqp_NU;
  }

  private void stepRightWall(Context c) {
    double vxL = leftRef.owner.vx, vyL = leftRef.owner.vy;
    double vpL = vxL * px + vyL * py;

    //импульс трения о стенку
    double dqp_NU = S * vpL * c.muWall(leftRef.owner.p);

    dqp -= dqp_NU;
  }

  private void stepLeftInput(Context c) {
    throw new UnsupportedOperationException();
  }
}
