package com.example.op_sch.dataStructures;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

public class CustomSet<T> {

    private LinkedListNode<T> head;
    private int size;

    public CustomSet() {

    }

    // Add an element to the set
    public boolean add(T element) {
        LinkedListNode<T> node = new LinkedListNode<>(element);
        node.next = head;
        head = node;
        size++;
        return true;
    }

    public Set<T> toSet() {
        Set<T> set = new HashSet<>();
        LinkedListNode<T> current = head;
        while (current != null) {
            set.add(current.value);
            current = current.next;
        }
        return set;
    }

    // Remove an element from the set
    public boolean remove(T element) {
        if (isEmpty()) {
            return false;
        }
        LinkedListNode<T> current = head;
        LinkedListNode<T> prev = null;

        while (current != null && !current.value.equals(element)) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            if (prev == null) {
                head = current.next;
            } else {
                prev.next = current.next;
            }
            size--;
            return true;
        }

        return false;
    }

    // Check if an element is present in the set
    public boolean contains(T element) {
        LinkedListNode<T> current = head;
        while (current != null) {
            if (current.value.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public CustomSet<T> unionOrdered(CustomSet<T> other) {
        CustomSet<T> unionSet = new CustomSet<>();
        LinkedListNode<T> currentNode = other.head;
        while (currentNode != null) {
            if (!unionSet.contains(currentNode.value)) {
                unionSet.add(currentNode.value);
            }
            currentNode = currentNode.next;
        }
        currentNode = head;
        while (currentNode != null) {
            if (!unionSet.contains(currentNode.value)) {
                unionSet.add(currentNode.value);
            }
            currentNode = currentNode.next;
        }
        unionSet.sortList();
        return unionSet;
    }

    public void insert(T val, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (index == 0) {
            add(val);
            return;
        }

        if (index == size) {
            add(val);
            return;
        }

        LinkedListNode<T> temp = getNode(index - 1);
        LinkedListNode<T> node = new LinkedListNode<>(val, temp.next);
        temp.next = node;
        size++;
    }

    public void display() {
        LinkedListNode<T> temp = head;
        while (temp != null) {
            System.out.print(temp.value + " -> ");
            temp = temp.next;
        }
        System.out.println("END");
    }

    public void sortList() {
        LinkedListNode<T> currentNode = head;
        LinkedListNode<T> index = null;
        T temp;

        if (head == null) {
            return;
        } else {
            while (currentNode != null) {
                index = currentNode.next;
                while (index != null) {
                    if (((Comparable<T>) currentNode.value).compareTo(index.value) > 0) {
                        temp = currentNode.value;
                        currentNode.value = index.value;
                        index.value = temp;
                    }
                    index = index.next;
                }
                currentNode = currentNode.next;
            }
        }
    }

    public LinkedListNode<T> getNode(int index) {
        LinkedListNode<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    public int size() {
        return size;
    }

    // Check if the set is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get an array of the elements in the set
    public T[] toArray() {
        T[] result = (T[]) Array.newInstance(Object.class, size);
        LinkedListNode<T> current = head;
        int i = 0;
        while (current != null) {
            result[i] = current.value;
            current = current.next;
            i++;
        }
        return result;
    }

    static void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }
}
