package pompei.maths.circles_with_lines;

import java.util.ArrayList;
import java.util.List;

public class DownKeyStack {
  private final List<KeyName> list = new ArrayList<>();

  public void down(KeyName keyName) {
    list.remove(keyName);
    list.add(keyName);
  }

  public void up(KeyName keyName) {
    list.remove(keyName);
  }

  @Override
  public String toString() {
    return list.toString();
  }

  public boolean does(KeyName upKeyName, String strRequest) {
    if ("Simple_C".equals(strRequest)) {
      return upKeyName.code == 67 && list.size() == 1 && list.get(0).code == 67;
    }
    throw new IllegalArgumentException("Unknown strRequest = " + strRequest);
  }
}
