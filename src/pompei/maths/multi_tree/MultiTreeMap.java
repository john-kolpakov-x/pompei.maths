package pompei.maths.multi_tree;

final class MultiTreeMap<K extends Comparable<K>, V> {

  private final int nodeSize;

  public MultiTreeMap(int nodeSize) {
    this.nodeSize = nodeSize;
  }

  public MultiTreeMap() {
    this(7);
  }

  Node root = null;

  int modCount = 0;

  @SuppressWarnings("UnusedReturnValue")
  public V put(K key, V value) {

    if (key == null) {
      throw new IllegalArgumentException("jn654j :: key == null");
    }
    if (value == null) {
      throw new IllegalArgumentException("k6m35k :: value == null");
    }

    if (root == null) {
      root = new Node(nodeSize, null);
      root.elements[0] = new KeyValue(key, value);
      root.count = 1;
      modCount++;
      return null;
    }

    return putTo(root, key, value);
  }

  @SuppressWarnings("Duplicates")
  public V putTo(Node node, K key, V value) {

    int firstMoreIndex = -1, lastNullIndex = -1;

    for (int i = 0; i < nodeSize; i++) {

      KeyValue element = (KeyValue) node.elements[i];

      if (element == null) {
        lastNullIndex = i;
        break;
      }

      //noinspection unchecked
      int cmp = key.compareTo((K) element.key);

      if (cmp == 0) {

        if (element.value == value) {
          return null;
        }

        Object oldValue = element.value;
        element.value = value;
        //noinspection unchecked
        return (V) oldValue;
      }

      if (cmp < 0 && firstMoreIndex < 0) {
        firstMoreIndex = i;
      }

    }

    if (lastNullIndex >= 0) {

      if (firstMoreIndex < 0) {
        node.elements[lastNullIndex] = new KeyValue(key, value);
      } else {

        //noinspection ManualArrayCopy
        for (int i = lastNullIndex; i > firstMoreIndex; i--) {
          node.elements[i] = node.elements[i - 1];
        }

        node.elements[firstMoreIndex] = new KeyValue(key, value);

      }

      node.count++;
      return null;

    }

    {
      if (firstMoreIndex < 0) {
        firstMoreIndex = node.elements.length;
      }
      Node subNode = node.references[firstMoreIndex];
      if (subNode == null) {
        subNode = new Node(nodeSize, node);
        subNode.elements[0] = new KeyValue(key, value);
        node.references[firstMoreIndex] = subNode;
        subNode.count = 1;
        node.count++;
        modCount++;
        return null;
      }
      {
        return putTo(subNode, key, value);
      }
    }

  }

  public V remove(K key) {
    if (key == null) {
      throw new IllegalArgumentException("5j4236b :: key == null");
    }

    if (root == null) {
      return null;
    }

    return removeFrom(root, key);
  }

  @SuppressWarnings("Duplicates")
  private V removeFrom(Node node, K key) {

    int firstMoreIndex = -1, lastNullIndex = -1;

    for (int i = 0; i < nodeSize; i++) {

      KeyValue element = (KeyValue) node.elements[i];

      if (element == null) {
        lastNullIndex = i;
        break;
      }

      //noinspection unchecked
      int cmp = key.compareTo((K) element.key);

      if (cmp == 0) {

        Object oldValue = element.value;
        node.elements[i] = null;
        modCount++;
        node.count--;

        removeHole(node, i);

        //noinspection unchecked
        return (V) oldValue;
      }

      if (cmp < 0 && firstMoreIndex < 0) {
        firstMoreIndex = i;
      }

    }

    if (lastNullIndex >= 0) {
      return null;
    }

    if (firstMoreIndex < 0) {
      firstMoreIndex = nodeSize;
    }

    Node ref = node.references[firstMoreIndex];

    if (ref == null) {
      return null;
    }

    V removeValue = removeFrom(ref, key);

    if (removeValue != null) {
      node.count--;
    }

    return removeValue;
  }

  void removeHole(Node node, int refIndex) {
    int beforeIndex = -1;
    Node beforeRef = null;
    for (int i = refIndex; i > 0; i--) {

      Node ref = node.references[i];

      if (ref != null) {
        beforeIndex = i;
        beforeRef = ref;
        break;
      }

    }

    int afterIndex = -1;
    Node afterRef = null;
    for (int i = refIndex + 1; i <= nodeSize; i++) {

      Node ref = node.references[i];
      if (ref != null) {
        afterIndex = i;
        afterRef = ref;
        break;
      }

    }

    int deltaBefore = refIndex - beforeIndex;
    int deltaAfter = afterIndex - refIndex - 1;

    if (beforeRef != null && (afterRef == null || deltaBefore < deltaAfter)) {

      for (int i = refIndex; i > beforeIndex; i++) {
        node.elements[i] = node.elements[i - 1];
      }

      Node ref = node.references[beforeIndex];
      node.elements[beforeIndex] = removeRightest(ref);
      if (ref.count == 0) {
        node.references[beforeIndex] = null;
      }

    }

    if (afterRef != null && (beforeRef == null || deltaAfter <= deltaBefore)) {

      //noinspection ManualArrayCopy
      for (int i = refIndex; i < afterIndex; i++) {
        node.elements[i] = node.elements[i + 1];
      }

      Node ref = node.references[afterIndex];
      node.elements[afterIndex - 1] = removeLeftest(ref);
      if (ref.count == 0) {
        node.references[afterIndex] = null;
      }

    }
  }

  private KeyValue removeLeftestElement(Node node) {
    KeyValue ret = (KeyValue) node.elements[0];

    for (int i = 1; i < nodeSize; i++) {
      KeyValue kv = (KeyValue) node.elements[i];
      node.elements[i - 1] = kv;
      if (kv == null) {
        break;
      }
    }

    node.elements[nodeSize - 1] = null;
    node.count--;
    return ret;
  }

  KeyValue removeLeftest(Node node) {

    if (node.elements[nodeSize - 1] == null) {
      return removeLeftestElement(node);
    }

    int firstNotNullRefIndex = -1;

    for (int i = 0; i <= nodeSize; i++) {

      if (node.references[i] != null) {
        firstNotNullRefIndex = i;
        break;
      }

    }

    if (firstNotNullRefIndex < 0) {
      return removeLeftestElement(node);
    }

    if (firstNotNullRefIndex == 0) {
      //noinspection ConstantConditions
      KeyValue ret = removeLeftest(node.references[0]);
      if (node.references[0].count == 0) {
        node.references[0] = null;
      }
      node.count--;
      return ret;
    }

    {
      KeyValue ret = (KeyValue) node.elements[0];

      //noinspection ManualArrayCopy
      for (int i = 0; i < firstNotNullRefIndex - 1; i++) {
        node.elements[i] = node.elements[i + 1];
      }

      node.elements[firstNotNullRefIndex - 1] = removeLeftest(node.references[firstNotNullRefIndex]);

      if (node.references[firstNotNullRefIndex].count == 0) {
        node.references[firstNotNullRefIndex] = null;
      }

      node.count--;
      return ret;
    }
  }

  @SuppressWarnings("Duplicates")
  KeyValue removeRightest(Node node) {

    int lastNotNullRefIndex = -1;

    for (int i = 0; i < nodeSize; i++) {
      if (node.elements[i] == null) {
        KeyValue ret = (KeyValue) node.elements[i - 1];
        node.elements[i - 1] = null;
        node.count--;
        return ret;
      }

      if (node.references[i] != null) {
        lastNotNullRefIndex = i;
      }
    }

    {
      Node last = node.references[nodeSize];
      if (last != null) {
        KeyValue ret = removeRightest(last);
        if (last.count == 0) {
          node.references[nodeSize] = null;
        }
        node.count--;
        return ret;
      }
    }

    if (lastNotNullRefIndex < 0) {

      KeyValue ret = (KeyValue) node.elements[nodeSize - 1];

      node.elements[nodeSize - 1] = null;
      node.count--;

      return ret;
    }

    {
      KeyValue ret = (KeyValue) node.elements[nodeSize - 1];

      //noinspection ManualArrayCopy
      for (int i = nodeSize - 1; i > lastNotNullRefIndex; i--) {
        node.elements[i] = node.elements[i - 1];
      }

      node.elements[lastNotNullRefIndex] = removeRightest(node.references[lastNotNullRefIndex]);

      if (node.references[lastNotNullRefIndex].count == 0) {
        node.references[lastNotNullRefIndex] = null;
      }

      node.count--;

      return ret;
    }

  }
}
