package pompei.maths.utils.collections;

public interface LinkedArray<Element> extends Iterable<Element> {

  LinkedArray<Element> putLast(Element element);

  LinkedArray<Element> putFirst(Element element);

  Element getAndRemoveFirst();

  Element getAndRemoveLast();

  int count();

  default boolean isEmpty() {
    return count() == 0;
  }

  LinkedArray<Element> reverse();

  static <Element> LinkedArray<Element> create() {
    return new LinkedArrayImpl<>();
  }
}
