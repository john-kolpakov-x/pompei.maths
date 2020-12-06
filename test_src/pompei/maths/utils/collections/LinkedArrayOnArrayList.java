package pompei.maths.utils.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedArrayOnArrayList<E> implements LinkedArray<E> {

  private final List<E> list;
  private final Object mutex;

  public LinkedArrayOnArrayList() {
    list = new ArrayList<>();
    mutex = new Object();
  }

  private LinkedArrayOnArrayList(ArrayList<E> list, Object mutex) {
    this.list = list;
    this.mutex = mutex;
  }

  @Override
  public LinkedArray<E> putLast(E e) {
    synchronized (mutex) {
      list.add(e);
      return this;
    }
  }

  @Override
  public LinkedArray<E> putFirst(E e) {
    synchronized (mutex) {
      list.add(0, e);
      return this;
    }
  }

  @Override
  public E getAndRemoveFirst() {
    synchronized (mutex) {
      if (list.size() == 0) {
        return null;
      }
      return list.remove(0);
    }
  }

  @Override
  public E getAndRemoveLast() {
    synchronized (mutex) {
      if (list.size() == 0) {
        return null;
      }
      return list.remove(list.size() - 1);
    }
  }

  @Override
  public int count() {
    synchronized (mutex) {
      return list.size();
    }
  }

  @Override
  public int maxCount() {
    return 0;
  }

  @Override
  public LinkedArray<E> reverse() {
    synchronized (mutex) {
      ArrayList<E> rev = new ArrayList<>(list.size());
      for (int i = 0, c = list.size(); i < c; i++) {
        rev.add(list.get(c - i - 1));
      }
      return new LinkedArrayOnArrayList<>(rev, mutex);
    }
  }

  @Override
  public Iterator<E> iterator() {
    return list.iterator();
  }
}
