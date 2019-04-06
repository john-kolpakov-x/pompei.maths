package pompei.maths.multi_tree;

public class KeyValue {

  public final Object key;
  public Object value;

  public KeyValue(Object key, Object value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String toString() {
    return key + "=" + value;
  }
}
