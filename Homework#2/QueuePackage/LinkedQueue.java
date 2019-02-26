package QueuePackage;

import java.util.EmptyStackException;

public class LinkedQueue<T> implements QueueInterface<T> {

    private int numEntries = 0;
    private Node firstNode;
    private Node lastNode;

    public LinkedQueue() {
        firstNode = null;
        lastNode = null;
    }

    private class Node {

        private T data;
        private Node next;

        private Node(T d, Node n) {
            next = n;
            data = d;
        }

        public void setNextNode(Node n) {
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setData(T d) {
            data = d;
        }

        public Node getNextNode() {
            return next;
        }
    }

    @Override
    public void enqueue(T newEntry) {
        Node newNode = new Node(newEntry, null);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.setNextNode(newNode);
        }
        lastNode = newNode;
        numEntries++;
    }

    @Override
    public T dequeue() {
        T front = getFront();
        if (front == null) {
            throw new EmptyStackException();
        }
        firstNode.setData(null);
        firstNode = firstNode.getNextNode();
        if (firstNode == null) {
            lastNode = null;
        }
        numEntries--;
        return front;
    }

    @Override
    public T getFront() {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return firstNode.getData();
        }
    }

    public T getBack() {
        return lastNode.getData();
    }

    @Override
    public boolean isEmpty() {
        return (firstNode == null) && (lastNode == null);
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
    }

    public int getNumEntries() {
        return numEntries;
    }
}
