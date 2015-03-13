package edu.cimo;

import edu.cimo.test.NodeTest;
import edu.cimo.test.TestJUnit;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by cimo on 10/03/15.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(NodeTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
