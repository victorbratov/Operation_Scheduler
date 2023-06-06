package com.example.op_sch.dataStructures;

public class CustomLinkedList<T> {

    LinkedListNode<T> head;
    LinkedListNode<T> tail;
    int size;

    public CustomLinkedList() {
        this.size = 0;
    }

    public int getSize(){
        return size;
    }



    public void insertFirst(T val) {
        // inserting into the head of the list
        LinkedListNode<T> node = new LinkedListNode<>(val);
        node.next = head;
        head = node;

        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void insertLast(T val) {
//        inserting in the last of the list
        if (tail == null) {
            insertFirst(val);
            return;
        }
        LinkedListNode<T> node = new LinkedListNode<>(val);
        tail.next = node;
        tail = node;
        size++;
    }

    public void deleteFirst() {
        // deleting the head and moving it further
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
    }

    public T getFirst() {
        // returns the value of first element
        return head.value;
    }

    public void display() {
        // prints out the entire list
        LinkedListNode<T> temp = head;
        while (temp != null) {
            System.out.print(temp.value + " -> ");
            temp = temp.next;
        }
        System.out.println("END");
    }

}
