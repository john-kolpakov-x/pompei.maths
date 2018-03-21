package pompei.maths.utils.collections;

import java.util.Iterator;

class LinkedArrayImpl<Element> extends AbstractLinkedArray<Element> {
  private final Object mutex = new Object();

  static class Node<Element> {
    final Element element;
    Node<Element> next = null, prev = null;

    Node(Element element) {
      this.element = element;
    }
  }

  Node<Element> first = null;
  Node<Element> last = null;
  int count = 0;

  @Override
  public LinkedArray<Element> putLast(Element element) {
    Node<Element> node = new Node<>(element);

    synchronized (mutex) {
      if (last == null) {
        first = last = node;
      } else {
        node.prev = last;
        last.next = node;
        last = node;
      }
      count++;
    }

    return this;

  }

  @Override
  public LinkedArray<Element> putFirst(Element element) {
    Node<Element> node = new Node<>(element);

    synchronized (mutex) {
      if (first == null) {
        first = last = node;
      } else {
        node.next = first;
        first.prev = node;
        first = node;
      }
      count++;
    }

    return this;
  }

  @Override
  @SuppressWarnings("Duplicates")
  public Element getAndRemoveFirst() {
    synchronized (mutex) {
      if (first == null) return null;
      Element ret = first.element;
      first = first.next;
      if (first == null) {
        last = null;
      } else {
        first.prev = null;
      }
      count--;
      return ret;
    }
  }

  @Override
  @SuppressWarnings("Duplicates")
  public Element getAndRemoveLast() {
    synchronized (mutex) {
      if (last == null) return null;
      Element ret = last.element;
      last = last.prev;
      if (last == null) {
        first = null;
      } else {
        last.next = null;
      }
      count--;
      return ret;
    }
  }

  @Override
  public int count() {
    synchronized (mutex) {
      return count;
    }
  }

  @Override
  public LinkedArray<Element> reverse() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<Element> iterator() {
    return new Iterator<Element>() {
      Node<Element> current = first;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public Element next() {
        Element ret = current.element;
        current = current.next;
        return ret;
      }
    };
  }
}
