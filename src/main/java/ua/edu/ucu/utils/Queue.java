package ua.edu.ucu.utils;


import ua.edu.ucu.utils.immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList data;

    Queue(Object[] data) {
        this.data = new ImmutableLinkedList(data);
    }

    Queue(Object elem) {
        this.data = new ImmutableLinkedList(elem);
    }

    public Queue() {
        this.data = new ImmutableLinkedList();
    }

    private Object getFirst() {

        return this.data.getFirst();
    }

    public Object peek() {
        return this.getFirst();
    }

    private void removeFirst() {
        this.data = this.data.removeFirst();

    }

    public Object dequeue() {
        Object ret = peek();
        this.removeFirst();
        return ret;
    }

    private void add(Object e) {
        this.data = this.data.add(e);
    }

    public void enqueue(Object e) {
        this.add(e);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public Object[] toArray() {
        return data.toArray();
    }
}
