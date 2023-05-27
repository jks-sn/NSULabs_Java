package srs.calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;
import calculator.operations.Operation;
import calculator.operations.Multiply;

import java.util.ArrayList;

public class MultiplyTest {
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
    public void MultiplyTest1() {
        args.add("abacaba");
        args.add("3");
        context.push(5);
        context.push(9);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void MultiplyTest2() {
        context.push(3);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void MultiplyTest3() {
        context.push(300);
        context.push(0);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.assertEquals(context.peek(), 0);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void MultiplyTest4() {
        context.push(3);
        context.push(1);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.assertEquals(context.peek(), 3);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void MultiplyTest5() {
        context.push(55);
        context.push(11);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.assertEquals(context.peek(), 605);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void MultiplyTest6() {
        context.push(-60);
        context.push(5);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.assertEquals(context.peek(), -300);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void MultiplyTest7() {
        context.push(-0.6);
        context.push(2);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.assertEquals(context.peek(), -1.2);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }
    @Test
    public void MultiplyTest8() {
        context.push(0.6);
        context.push(2);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.assertEquals(context.peek(), 1.2);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }
    @Test
    public void MultiplyTest9() {
        context.push(Double.MAX_VALUE);
        context.push(5);
        Operation multiply = new Multiply(context, args.toArray(new Object[0]));
        try {
            multiply.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }
}
