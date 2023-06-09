package com.example.op_sch.tests;

import com.example.op_sch.dataStructures.CustomLinkedList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomLinkedListTest {

    private CustomLinkedList<String> linkedList;

    @Before
    public void setUp() {
        linkedList = new CustomLinkedList<>();
    }

    @Test
    public void testInsertFirst() {
        // test inserting an element at the beginning of the list
        linkedList.insertFirst("A");
        assertEquals(1, linkedList.getSize());
        assertEquals("A", linkedList.getFirst());

        linkedList.insertFirst("B");
        assertEquals(2, linkedList.getSize());
        assertEquals("B", linkedList.getFirst());

    }

    @Test
    public void testInsertLast() {
        // test inserting an element at the end of the list
        linkedList.insertLast("A");
        assertEquals(1, linkedList.getSize());
        assertEquals("A", linkedList.getFirst());
        linkedList.insertLast("B");
        assertEquals(2, linkedList.getSize());
    }

    @Test
    public void testDeleteFirst() {
        // test deleting the first element from the list
        linkedList.insertFirst("A");
        linkedList.insertFirst("B");
        linkedList.insertFirst("C");
        linkedList.deleteFirst();
        assertEquals(2, linkedList.getSize());
        assertEquals("B", linkedList.getFirst());
        linkedList.deleteFirst();
        assertEquals(1, linkedList.getSize());
    }

    @Test
    public void testGetFirst() {
        // Test getting the first element from the list
        linkedList.insertFirst("A");
        linkedList.insertFirst("B");
        linkedList.insertFirst("C");
        assertEquals("C", linkedList.getFirst());

    }


}
