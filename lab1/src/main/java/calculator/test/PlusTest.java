package srs.calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;
import calculator.operations.Plus;
import calculator.operations.Operation;

import java.util.ArrayList;

public class PlusTest {
    static CalculatorStack context;
    static private ArrayList<Object> args;

    @BeforeAll
    static void createStack() {
        context = new CalculatorStack();
        args = new ArrayList<>();
    }

    @AfterEach
    void cleanStack() {
        context.clear();
        args.clear();
    }

    @Test
    public void PlusTest1() {
        args.add("abacaba");
        args.add("3");
        context.push(5);
        context.push(9);
        Operation plus = new Plus(context, args.toArray(new Object[0]));
        try {
            plus.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void PlusTest2() {
        context.push(3);
        Operation plus = new Plus(context, args.toArray(new Object[0]));
        try {
            plus.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void PlusTest3() {
        context.push(300);
        context.push(0);
        Operation plus = new Plus(context, args.toArray(new Object[0]));
        try {
            plus.exec();
            Assertions.assertEquals(context.peek(), 300);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void PlusTest4() {
        context.push(3);
        context.push(1);
        Operation plus = new Plus(context, args.toArray(new Object[0]));
        try {
            plus.exec();
            Assertions.assertEquals(context.peek(), 4);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void PlusTest5() {
        context.push(55);
        context.push(11);
        Operation plus = new Plus(context, args.toArray(new Object[0]));
        try {
            plus.exec();
            Assertions.assertEquals(context.peek(), 66);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void PlusTest6() {
        context.push(-60);
        context.push(-600);
        Operation plus = new Plus(context, args.toArray(new Object[0]));
        try {
            plus.exec();
            Assertions.assertEquals(context.peek(), -660);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void PlusTest7() {
        context.push(-0.6);
        context.push(1);
        Operation plus = new Plus(context, args.toArray(new Object[0]));
        try {
            plus.exec();
            Assertions.assertEquals(context.peek(), 0.4);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void PlusTest8() {
        context.push(Double.MAX_VALUE);
        context.push(5);
        Operation plus = new Plus(context, args.toArray(new Object[0]));
        try {
            plus.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }
}
