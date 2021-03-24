package base;

import java.util.Iterator;


public class Bag<Item> implements Iterable<Item> {
    private Node first;
    private int size;

    private class Node {
        Item item;
        Node next;

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }


    public void add(Item item) {
        if (item == null)
            throw new IllegalArgumentException("argument to add() is null");
        Node oldFirst = first;
        first = new Node(item,oldFirst);
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return first != null;
    }

    public Iterator<Item> iterator() {
        return null;
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext(){
            return current != null;
        }
        public void remove(){ }
        public Item next(){
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


}
