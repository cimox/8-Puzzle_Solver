package edu.cimo.test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by cimo on 10/03/15.
 */
public class TestJUnit {
    @Test
    public void testAdd() {
        String str = "JUnit is working!";
        assertEquals("JUnit is working!", str);
        assertEquals("Junit is not working", str);
    }
}
