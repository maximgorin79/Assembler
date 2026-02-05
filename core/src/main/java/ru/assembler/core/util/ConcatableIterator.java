package ru.assembler.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import lombok.NonNull;

/**
 * ConcatableIterator.
 *
 * @author Maxim Gorin
 */
public class ConcatableIterator<E> implements Iterator<E> {

  private final LinkedList<Iterator<E>> iterators = new LinkedList<>();

  private Iterator<E> current;

  public ConcatableIterator(@NonNull Iterator<E> iter) {
    this(Arrays.asList(iter));
  }

  public ConcatableIterator(@NonNull Collection<Iterator<E>> col) {
    if (!col.isEmpty()) {
      if (col.size() > 1) {
        LinkedList<Iterator<E>> chunk = new LinkedList<>();
        chunk.addAll(col);
        Collections.reverse(chunk);
        this.iterators.addAll(chunk);
      } else {
        this.iterators.addAll(col);
      }
      current = iterators.pop();
    }
  }

  @Override
  public boolean hasNext() {
    if (current == null) {
      return false;
    }
    if (current.hasNext()) {
      return true;
    }
    try {
      current = iterators.pop();
    } catch (NoSuchElementException e) {
      current = null;
      return false;
    }
    return hasNext();
  }

  @Override
  public E next() {
    if (current == null) {
      throw new NoSuchElementException();
    }
    if (current.hasNext()) {
      return current.next();
    } else {
      current = null;
      if (!iterators.isEmpty()) {
        current = iterators.pop();
      }
    }
    return next();
  }

  public void concat(@NonNull Iterator<E> iter) {
    if (current != null) {
      iterators.push(current);
    }
    current = iter;
  }
}
