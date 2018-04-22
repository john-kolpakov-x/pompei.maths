package pompei.maths.utils.collections;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

class LinkedArrayImpl_optimum<Element> implements LinkedArray<Element> {
  private final ReentrantLock lock = new ReentrantLock();

  static class Node<Element> {
    final Element element;
    Node<Element> next = null, prev = null;

    Node(Element element) {
      this.element = element;
    }
  }

  Node<Element> first = null, last = null;
  final AtomicInteger count = new AtomicInteger(0);
  int maxCount = 0;

  @Override
  public LinkedArray<Element> putLast(Element element) {
    Node<Element> node = new Node<>(element);

    lock.lock();
    try {
      if (last == null) {
        first = last = node;
      } else {
        node.prev = last;
        last.next = node;
        last = node;
      }
      count.incrementAndGet();
      checkMaxCount();
    } finally {
      lock.unlock();
    }

    return this;

  }

  private void checkMaxCount() {
    int count = this.count.get();
    if (maxCount < count) maxCount = count;
  }

  @Override
  public LinkedArray<Element> putFirst(Element element) {
    Node<Element> node = new Node<>(element);

    lock.lock();
    try {
      Node<Element> F = first;
      if (F == null) {
        first = last = node;
      } else {
        node.next = F;
        F.prev = node;
        first = node;
      }
      count.incrementAndGet();
      checkMaxCount();
    } finally {
      lock.unlock();
    }

    return this;
  }

  @Override
  @SuppressWarnings("Duplicates")
  public Element getAndRemoveFirst() {
    lock.lock();
    try {
      Node<Element> F = this.first;
      if (F == null) return null;
      Element ret = F.element;
      F = first = F.next;
      if (F == null) {
        last = null;
      } else {
        F.prev = null;
      }
      count.decrementAndGet();
      return ret;
    } finally {
      lock.unlock();
    }
  }

  @Override
  @SuppressWarnings("Duplicates")
  public Element getAndRemoveLast() {
    lock.lock();
    try {
      Node<Element> L = last;
      if (L == null) return null;
      Element ret = L.element;
      L = last = L.prev;
      if (L == null) {
        first = null;
      } else {
        L.next = null;
      }
      count.decrementAndGet();
      return ret;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public int count() {
    return count.get();
  }

  @Override
  public int maxCount() {
    return maxCount;
  }

  @Override
  public LinkedArray<Element> reverse() {
    return new LinkedArray<Element>() {
      @Override
      public LinkedArray<Element> putLast(Element element) {
        LinkedArrayImpl_optimum.this.putFirst(element);
        return this;
      }

      @Override
      public LinkedArray<Element> putFirst(Element element) {
        LinkedArrayImpl_optimum.this.putLast(element);
        return this;
      }

      @Override
      public Element getAndRemoveFirst() {
        return LinkedArrayImpl_optimum.this.getAndRemoveLast();
      }

      @Override
      public Element getAndRemoveLast() {
        return LinkedArrayImpl_optimum.this.getAndRemoveFirst();
      }

      @Override
      public int count() {
        return count.get();
      }

      @Override
      public int maxCount() {
        return maxCount;
      }

      @Override
      public LinkedArray<Element> reverse() {
        return LinkedArrayImpl_optimum.this;
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
