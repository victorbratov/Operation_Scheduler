package tests;

import com.example.op_sch.dataStructures.CustomSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomSetTest {

    private CustomSet<String> set;

    @Before
    public void setUp() {
        set = new CustomSet<>();
    }

    @Test
    public void testAdd() {
        // test adding elements to the set
        assertTrue(set.add("A"));
        assertEquals(1, set.size());
        assertTrue(set.contains("A"));

        assertTrue(set.add("B"));
        assertEquals(2, set.size());
    }

    @Test
    public void testRemove() {
        // test removing elements from the set
        set.add("A");
        set.add("B");

        assertTrue(set.remove("A"));
        assertEquals(1, set.size());
        assertFalse(set.contains("A"));

        assertFalse(set.remove("C"));
    }

    @Test
    public void testContains() {
        // test checking if an element is present in the set
        set.add("A");
        set.add("B");

        assertTrue(set.contains("A"));
        assertTrue(set.contains("B"));
        assertFalse(set.contains("C"));
    }

    @Test
    public void testUnionOrdered() {
        // test performing the union of two sets
        CustomSet<String> set1 = new CustomSet<>();
        set1.add("A");
        set1.add("B");

        CustomSet<String> set2 = new CustomSet<>();
        set2.add("B");
        set2.add("C");

        CustomSet<String> unionSet = set1.unionOrdered(set2);

        assertEquals(3, unionSet.size());
        assertTrue(unionSet.contains("A"));
        assertTrue(unionSet.contains("B"));
        assertTrue(unionSet.contains("C"));
    }


    @Test
    public void testIsEmpty() {
        // test checking if the set is empty
        assertTrue(set.isEmpty());

        set.add("A");

        assertFalse(set.isEmpty());
    }

    @Test
    public void testSortList() {
        // test sorting the elements in the set
        set.add("D");
        set.add("B");
        set.add("C");
        set.add("A");

        set.sortList();

        assertEquals(4, set.size());
        assertEquals("A", set.getNode(0).getValue());
        assertEquals("B", set.getNode(1).getValue());
        assertEquals("C", set.getNode(2).getValue());
        assertEquals("D", set.getNode(3).getValue());
    }

    // Add more test cases as needed

}
