package pompei.maths;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pompei.maths.runge_kutta.RungeKuttaDiffUr;

public class DiffUrTest {
  
  private static double res(double t) {
    return t * sin(t);
  }
  
  @DataProvider
  public Object[][] diff_urs() {
    return new Object[][] {
    //
    new Object[] { new RungeKuttaDiffUr() {
      public String toString() {
        return "Runge-Kutta";
      };
    } }
    //    
    };
  }
  
  @Test(dataProvider = "diff_urs")
  public void step(DiffUr dur) throws Exception {
    double DELTA = 1e-8;
    
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
    
    for (int i = 0; i < 100000; i++) {
      dur.step();
    }
    
    {
      double X1 = dur.getX()[0];
      double X2 = res(dur.getT());
      assertThat(abs(X1 - X2)).isLessThan(DELTA);
    }
    for (int i = 0; i < 100000; i++) {
      dur.step();
    }
    
    {
      double X1 = dur.getX()[0];
      double X2 = res(dur.getT());
      assertThat(abs(X1 - X2)).isLessThan(DELTA);
    }
    for (int i = 0; i < 100000; i++) {
      dur.step();
    }
    
    {
      double X1 = dur.getX()[0];
      double X2 = res(dur.getT());
      assertThat(abs(X1 - X2)).isLessThan(DELTA);
    }
    
  }
}
