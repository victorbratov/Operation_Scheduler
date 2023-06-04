package com.example.op_sch.dataStructures;


public class LinkedListNode<T> {
    T value;
    LinkedListNode<T> next;

    public LinkedListNode(T value, LinkedListNode<T> next) {
        this.value = value;
        this.next = next;
    }

    public LinkedListNode(T value) {
        this.value = value;
    }
}
