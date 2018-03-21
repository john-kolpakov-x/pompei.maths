package pompei.maths.utils.collections;

import java.util.Iterator;
import java.util.stream.Collectors;

class LinkedArrayImpl<Element> implements LinkedArray<Element> {
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
      Node<Element> F = first;
      if (F == null) {
        first = last = node;
      } else {
        node.next = F;
        F.prev = node;
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
      Node<Element> F = this.first;
      if (F == null) return null;
      Element ret = F.element;
      F = first = F.next;
      if (F == null) {
        last = null;
      } else {
        F.prev = null;
      }
      count--;
      return ret;
    }
  }

  @Override
  @SuppressWarnings("Duplicates")
  public Element getAndRemoveLast() {
    synchronized (mutex) {
      Node<Element> L = last;
      if (L == null) return null;
      Element ret = L.element;
      L = last = L.prev;
      if (L == null) {
        first = null;
      } else {
        L.next = null;
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
    return new LinkedArray<Element>() {
      @Override
      public LinkedArray<Element> putLast(Element element) {
        LinkedArrayImpl.this.putFirst(element);
        return this;
      }

      @Override
      public LinkedArray<Element> putFirst(Element element) {
        LinkedArrayImpl.this.putLast(element);
        return this;
      }

      @Override
      public Element getAndRemoveFirst() {
        return LinkedArrayImpl.this.getAndRemoveLast();
      }

      @Override
      public Element getAndRemoveLast() {
        return LinkedArrayImpl.this.getAndRemoveFirst();
      }

      @Override
      public int count() {
        synchronized (mutex) {
          return count;
        }
      }

      @Override
      public LinkedArray<Element> reverse() {
        return LinkedArrayImpl.this;
      }

      @Override
      public Iterator<Element> iterator() {
        return new Iterator<Element>() {
          Node<Element> current = last;

          @Override
          public boolean hasNext() {
            return current != null;
          }

          @Override
          public Element next() {
            Element ret = current.element;
            current = current.prev;
            return ret;
          }
        };
      }

      @Override
      public String toString() {
        return stream().map(Object::toString).collect(Collectors.joining(", ", "[", "]"));
      }
    };
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

  @Override
  public String toString() {
    return stream().map(Object::toString).collect(Collectors.joining(", ", "[", "]"));
  }
}
