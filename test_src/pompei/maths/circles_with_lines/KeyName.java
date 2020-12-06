package pompei.maths.circles_with_lines;

import java.awt.event.KeyEvent;
import java.util.Objects;

public class KeyName {
  public final int code, location;

  public KeyName(int code, int location) {
    this.code = code;
    this.location = location;
  }

  public KeyName(KeyEvent e) {
    this(e.getKeyCode(), e.getKeyLocation());
  }

  @Override
  public String toString() {
    return "Key{" + code + "-" + location + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeyName keyName = (KeyName) o;
    return code == keyName.code &&
               location == keyName.location;
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, location);
  }
}
