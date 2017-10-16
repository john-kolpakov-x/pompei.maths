package pompei.maths.difur;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pompei.maths.difur.DiffUr;
import pompei.maths.difur.DiffUrDefault;
import pompei.maths.difur.F;
import pompei.maths.difur.Stepper_H3;
import pompei.maths.difur.Stepper_H4_Hoine;
import pompei.maths.difur.Stepper_H5_RungeKutta;

public class DiffUrTest {
  
  private static double res(double t) {
    return t * sin(t);
  }
  
  @DataProvider
  public Object[][] diff_urs() {
    return new Object[][] {
        //
        new Object[] { 1e-8, new DiffUrDefault(new Stepper_H5_RungeKutta()) {
          public String toString() {
            return "H5 - Runge-Kutta";
          };
        } },
        //
        new Object[] { 1e-8, new DiffUrDefault(new Stepper_H4_Hoine()) {
          public String toString() {
            return "H4 - Hoine";
          };
        } },
        //
        new Object[] { 1e-5, new DiffUrDefault(new Stepper_H3()) {
          public String toString() {
            return "H3";
          };
        } },
    //    
    };
  }
  
  @Test(dataProvider = "diff_urs")
  public void step(double DELTA, DiffUr dur) throws Exception {
    
    F f = new F() {
      @Override
      public void f(double[] res, double t, double[] x) {
        res[0] = t * cos(t) + x[0] / t;
      }
    };
    
    dur.prepare(1, f);
    
    dur.setT(0.1);
    dur.setH(0.0001);
    dur.getX()[0] = res(dur.getT());
    
    for (int u = 0; u < 10; u++) {
      for (int i = 0; i < 100000; i++) {
        dur.step();
      }
      {
        double X1 = dur.getX()[0];
        double X2 = res(dur.getT());
        assertThat(abs(X1 - X2)).isLessThan(DELTA);
      }
    }
    
    for (int i = 0; i < 100000; i++) {
      dur.step();
    }
    {
      double X1 = dur.getX()[0];
      double X2 = res(dur.getT());
      assertThat(abs(X1 - X2)).isLessThan(DELTA);
      
      System.out.println("DELTA = " + abs(X1 - X2) + " for " + dur);
    }
    
  }
}
