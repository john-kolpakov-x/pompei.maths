package pompei.maths.end_element;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PointIndexTest {
  @Test
  public void test1() throws Exception {
    PointIndex pointIndex = new PointIndex(1, 1);
    pointIndex.addIndex(0, 0, 34);

    int nearestIndex = pointIndex.getNearestIndex(3, 3);
    assertThat(nearestIndex).isEqualTo(34);
  }
}