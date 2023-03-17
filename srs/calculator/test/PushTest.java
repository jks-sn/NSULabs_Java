package srs.calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import srs.calculator.exceptions.OperatorException;
import srs.calculator.logic.CalculatorStack;
import srs.calculator.operations.Operation;
import srs.calculator.operations.Push;

import java.util.ArrayList;

public class PushTest {
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
    public void PushTest1() {
        args.add("abacaba");
        args.add("3");
        context.push(5);
        context.push(9);
        Operation push = new Push(context, args.toArray(new Object[0]));
        try {
            push.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void PushTest2() {
        args.add("3");
        Operation push = new Push(context, args.toArray(new Object[0]));
        try {
            push.exec();
            Assertions.assertEquals(context.getStackLength(), 1);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void PushTest3() {
        context.push(300);
        args.add("3");
        Operation push = new Push(context, args.toArray(new Object[0]));
        try {
            push.exec();
            Assertions.assertEquals(context.getStackLength(), 2);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void PushTest4() {
        Operation push = new Push(context, args.toArray(new Object[0]));
        try {
            push.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }
}
