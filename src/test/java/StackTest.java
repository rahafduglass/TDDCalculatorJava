
import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class StackTest {

    private Stack<Integer> stack;

    @Before
    public void setUp() {
        stack = new Stack<>();
    }

    @Test
    public void testSize() {
        // Initially, the stack should be empty
        assertEquals(0, stack.size());

        // Add elements to the stack
        stack.push(1);
        stack.push(2);
        stack.push(3);

        // Check the size of the stack
        assertEquals(3, stack.size());

        // Pop one element and check the size again
        stack.pop();
        assertEquals(2, stack.size());
    }


    @Test
    public void testPush() {
        stack.push(1);
        assertFalse(stack.isEmpty());
        assertEquals(1, (int) stack.peek());
    }

    @Test
    public void testPop() {
        stack.push(1);
        stack.push(2);
        int poppedValue = stack.pop();
        assertEquals(2, poppedValue);
        assertEquals(1, (int) stack.peek());
    }

    @Test
    public void testPeek() {
        stack.push(1);
        stack.push(2);
        int peekedValue = stack.peek();
        assertEquals(2, peekedValue);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testSearch() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        int position = stack.search(2);
        assertEquals(2, position); // 2 is the second element from the top
        position = stack.search(4);
        assertEquals(-1, position); // 4 is not in the stack
    }

    @Test(expected = java.util.EmptyStackException.class)
    public void testPopEmptyStack() {
        stack.pop();
    }

    @Test(expected = java.util.EmptyStackException.class)
    public void testPeekEmptyStack() {
        stack.peek();
    }
}


