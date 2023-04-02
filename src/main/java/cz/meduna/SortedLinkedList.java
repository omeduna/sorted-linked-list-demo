package cz.meduna;

import java.util.AbstractList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class SortedLinkedList<E extends Comparable<E>> extends AbstractList<E> {

    private transient int size = 0;

    private transient Node<E> first;
    private transient Node<E> last;

    public SortedLinkedList() {
    }

    public SortedLinkedList(Collection<? extends E> collection) {
        this();
        addAll(collection);
    }

    public boolean add(E e) {
        if (this.first == null || e.compareTo(this.first.item) <= 0) {
            linkFirst(e);
        } else if (e.compareTo(this.last.item) > 0) {
            linkLast(e);
        } else {
            Node<E> prev = this.first;
            Node<E> next = this.first.next;
            int sizeBefore = this.size;
            for (int i = 0; i < sizeBefore; i++) {
                if (e.compareTo(next.item) < 0) {
                    linkBefore(e, next);
                    break;
                } else {
                    prev = prev.next;
                    next = prev.next;
                }
            }
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> nodeToUnlink = node(index);
        return unlink(nodeToUnlink);
    }

    public E removeFirst() {
        Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException();
        }
        E element = f.item;
        Node<E> next = f.next;
        f.item = null;
        f.next = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        modCount++;
        return element;
    }

    public E removeLast() {
        Node<E> l = last;
        if (l == null) {
            throw new NoSuchElementException();
        }
        E element = l.item;
        Node<E> prev = l.prev;
        l.item = null;
        l.prev = null; // help GC
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        modCount++;
        return element;
    }

    public E getFirst() {
        Node<E> f = first;
        if (f == null) {
            throw new NoSuchElementException();
        }
        return f.item;
    }

    public E getLast() {
        Node<E> l = last;
        if (l == null) {
            throw new NoSuchElementException();
        }
        return l.item;
    }

    public boolean contains(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    return index >= 0;
                }
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    return index >= 0;
                }
                index++;
            }
        }
        return false;
    }

    public boolean containsAll(Collection c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = null;
        last = null;
        size = 0;
        modCount++;
    }

    public E get(int index) {
        Node<E> x;
        if (index < (size >> 1)) {
            x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
        }
        return x.item;
    }

    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }

    private void linkFirst(E e) {
        Node<E> f = this.first;
        Node<E> newNode = new Node<>(null, e, f);
        this.first = newNode;
        if (f == null) {
            this.last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
        modCount++;
    }

    private void linkLast(E e) {
        Node<E> l = this.last;
        Node<E> newNode = new Node<>(l, e, null);
        this.last = newNode;
        if (l == null) {
            this.first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    private void linkBefore(E e, Node<E> next) {
        Node<E> previous = next.prev;
        Node<E> newNode = new Node<>(previous, e, next);
        next.prev = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
        modCount++;
    }

    private E unlink(Node<E> x) {
        E element = x.item;
        Node<E> next = x.next;
        Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }

    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "NodeItem{" + item + "}";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SortedLinkedList (Size ").append(size).append(")");
        sb.append("\n[");
        Node<E> current = this.first;
        while (current != null) {
            sb.append(current.item.toString());
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

}
