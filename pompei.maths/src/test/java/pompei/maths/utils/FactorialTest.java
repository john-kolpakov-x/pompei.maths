package pompei.maths.utils;

import java.math.BigInteger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class FactorialTest {

  @DataProvider
  public Object[][] factorial_DP() {
    return new Object[][]{

      {1000}, {100}, {10}, {213}, {1111}, {315}

    };
  }

  @Test(dataProvider = "factorial_DP")
  public void factorial(int n) throws Exception {

    BigInteger testingValue = Factorial.factorial(n);


    BigInteger I = BigInteger.ONE.add(BigInteger.ONE);
    BigInteger actualValue = BigInteger.ONE;

    for (int i = 2; i <= n; i++) {
      actualValue = actualValue.multiply(I);
      I = I.add(BigInteger.ONE);
    }

    assertThat(testingValue).isEqualTo(actualValue);
  }
}