package pompei.maths.utils.collections;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

  @Override
  default Spliterator<Element> spliterator() {
    return Spliterators.spliterator(iterator(), count(), 0);
  }


  default Stream<Element> stream() {
    return StreamSupport.stream(spliterator(), false);
  }


  default Stream<Element> parallelStream() {
    return StreamSupport.stream(spliterator(), true);
  }

  static <Element> LinkedArray<Element> create() {
    return new LinkedArrayImpl<>();
  }
}
