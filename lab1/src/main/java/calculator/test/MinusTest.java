package calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;
import calculator.operations.Minus;
import calculator.operations.Operation;

import java.util.ArrayList;

public class MinusTest {
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
    public void MinusTest1() {
        args.add("abacaba");
        args.add("3");
        context.push(5);
        context.push(9);
        Operation minus = new Minus(context, args.toArray(new Object[0]));
        try {
            minus.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void MinusTest2() {
        context.push(3);
        Operation minus = new Minus(context, args.toArray(new Object[0]));
        try {
            minus.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void MinusTest3() {
        context.push(300);
        context.push(0);
        Operation minus = new Minus(context, args.toArray(new Object[0]));
        try {
            minus.exec();
            Assertions.assertEquals(context.peek(), 300);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void MinusTest4() {
        context.push(3);
        context.push(1);
        Operation minus = new Minus(context, args.toArray(new Object[0]));
        try {
            minus.exec();
            Assertions.assertEquals(context.peek(), 2);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void MinusTest5() {
        context.push(55);
        context.push(11);
        Operation minus = new Minus(context, args.toArray(new Object[0]));
        try {
            minus.exec();
            Assertions.assertEquals(context.peek(), 44);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void MinusTest6() {
        context.push(60);
        context.push(600);
        Operation minus = new Minus(context, args.toArray(new Object[0]));
        try {
            minus.exec();
            Assertions.assertEquals(context.peek(), -540);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void MinusTest7() {
        context.push(0.6);
        context.push(1);
        Operation minus = new Minus(context, args.toArray(new Object[0]));
        try {
            minus.exec();
            Assertions.assertEquals(context.peek(), -0.4);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void MinusTest8() {
        context.push(-Double.MAX_VALUE);
        context.push(5);
        Operation minus = new Minus(context, args.toArray(new Object[0]));
        try {
            minus.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }
}
