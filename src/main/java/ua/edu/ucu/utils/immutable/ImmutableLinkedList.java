package ua.edu.ucu.utils.immutable;

public final class ImmutableLinkedList implements ImmutableList {

    private static class Node {
        private Object data;
        private Node next;
        private Node prev;

        //NODE INITIALISATION
        Node(Object elem, Node nex, Node previous) {
            data = elem;
            next = nex;
            prev = previous;
        }

        Node(Object elem) {
            data = elem;
            next = null;
            prev = null;
        }
    }


    private Node head;
    private int len;
    private Node tail;

    //LINKEDLIST INITIALISATION
    private ImmutableLinkedList(ImmutableLinkedList element) {

        head = null;
        tail = null;
        Node copyFrom = element.head;
        len = element.len;
        while (copyFrom != null) {
            if (head == null) {
                head = new Node(copyFrom.data);
                tail = head;
            } else {
                tail.next = new Node(copyFrom.data);
                tail.next.prev = tail;
                tail = tail.next;
            }
            copyFrom = copyFrom.next;
        }
    }

    public ImmutableLinkedList(Object[] lst) {
        if (lst.length > 0) {
            head = new Node(lst[0]);
            tail = head;
            len = 0;
            int toStart = 0;
            for (Object el : lst) {
                if (toStart == 1) {
                    tail.next = new Node(el);
                    tail = tail.next;
                } else {
                    toStart = 1;
                }
                len += 1;
            }
        } else {
            head = null;
            len = 0;
        }

    }

    public ImmutableLinkedList(Object elem) {
        head = new Node(elem);
        tail = head;
        len = 1;
    }

    public ImmutableLinkedList() {
        head = null;
        len = 0;
        tail = null;
    }


    //START OF FUNCTIONS
    private ImmutableLinkedList copyOf() {
        return new ImmutableLinkedList(this);
    }

    public ImmutableLinkedList addFirst(Object e) {
        ImmutableLinkedList newone = copyOf();
        newone.head = new Node(e);
        newone.tail = newone.head;
        newone.len += 1;
        return newone;
    }

    public ImmutableLinkedList addLast(Object e) {
        ImmutableLinkedList newone = copyOf();
        newone.tail.next = new Node(e);
        newone.tail.next.prev = newone.tail;
        newone.tail = newone.tail.next;
        newone.len += 1;
        return newone;
    }

    @Override
    public ImmutableLinkedList add(Object e) {

        ImmutableLinkedList newone = copyOf();

        if (newone.size() == 0) {
            return addFirst(e);
        } else {
            return addLast(e);
        }
    }

    private void checkIndex(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getNode(ImmutableLinkedList lst, int index) {
        checkIndex(index);
        Node el = lst.head;
        int i = 0;

        while (i != index) {
            el = el.next;
            i++;
        }
        return el;
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        if (index == size()) {
            return addLast(e);
        }
        checkIndex(index);
        ImmutableLinkedList newone = copyOf();
        Node change = getNode(newone, index);

        change.next = new Node(change.data, change.next, change);
        change.data = e;
        newone.len += 1;
        return newone;
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        Object[] copyFrom = toArray();
        Object[] createFrom = new Object[copyFrom.length + c.length];
        System.arraycopy(copyFrom, 0, createFrom, 0, len);
        System.arraycopy(c, 0, createFrom, len, c.length);

        return new ImmutableLinkedList(createFrom);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index == size()) {
            return addAll(c);
        }
        checkIndex(index);
        Object[] toCreate = new Object[c.length + size()];
        Object[] copyFrom = toArray();
        System.arraycopy(copyFrom, 0, toCreate, 0, index);
        System.arraycopy(c, 0, toCreate, index, c.length);
        System.arraycopy(copyFrom, index, toCreate, index + c.length,
                copyFrom.length - index);
        return new ImmutableLinkedList(toCreate);
    }


    public Object getFirst() {
        checkIndex(0);
        return head.data;
    }

    public Object getLast() {
        checkIndex(0);
        return tail.data;
    }

    @Override
    public Object get(int index) {
        return getNode(this, index).data;
    }


    @Override
    public ImmutableLinkedList remove(int index) {
        checkIndex(index);
        Object[] copyFrom = toArray();
        Object[] creatFrom = new Object[len - 1];
        System.arraycopy(copyFrom, 0, creatFrom, 0, index);
        //Don't know why, but if do through syscopy i will get error.
        for (int i = index + 1; i < len; i++) {
            creatFrom[i - 1] = copyFrom[i];
        }
        return new ImmutableLinkedList(creatFrom);
    }

    public ImmutableLinkedList removeFirst() {
        ImmutableLinkedList newone = copyOf();
        newone.head = newone.head.next;
        newone.len -= 1;
        if (newone.head != null) {
            newone.head.prev = null;
        } else {
            newone.tail = null;
        }
        return newone;
    }

    public ImmutableLinkedList removeLast() {
        ImmutableLinkedList newone = copyOf();
        newone.tail = newone.tail.prev;
        newone.len -= 1;
        if (newone.tail != null) {
            newone.tail.next = null;
        } else {
            newone.head = null;
        }

        return newone;
    }


    @Override
    public ImmutableLinkedList set(int index, Object e) {
        checkIndex(index);
        ImmutableLinkedList newone = copyOf();
        Node el = getNode(newone, index);
        el.data = e;
        return newone;
    }

    @Override
    public int indexOf(Object e) {
        Node getting = head;
        int i = 0;
        while (getting != null) {
            if (getting.data.equals(e)) {
                return i;
            }
            i++;
            getting = getting.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return len;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Object[] toArray() {
        if (size() == 0) {
            return new Object[]{};
        }
        Object[] toReturn = new Object[size()];

        Node from = head;

        for (int i = 0; i < size(); i++) {
            toReturn[i] = from.data;
            from = from.next;
        }
        return toReturn;
    }

    @Override
    public String toString() {
        Node a = head;
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < size() - 1; i++) {
            toReturn.append(a.data);
            toReturn.append(", ");
            a = a.next;
        }
        if (size() > 0) {
            toReturn.append(getLast());
        }
        return toReturn.toString();
    }

}
