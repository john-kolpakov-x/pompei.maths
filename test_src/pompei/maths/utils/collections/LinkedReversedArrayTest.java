package pompei.maths.utils.collections;

public class LinkedReversedArrayTest extends LinkedArrayTest {
  @Override
  protected <E> LinkedArray<E> createArray() {
    return super.<E>createArray().reverse();
  }
}
