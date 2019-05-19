package pompei.maths.lines_2d.util;

import java.util.Optional;

public interface Acceptor<T> {

  default Optional<T> get() {
    return Optional.ofNullable(getValueOrNull());
  }

  T getValueOrNull();

  void set(Optional<T> optional);

  default void setValue(T t) {
    set(Optional.ofNullable(t));
  }

}
