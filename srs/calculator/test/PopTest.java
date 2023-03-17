package srs.calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import srs.calculator.exceptions.OperatorException;
import srs.calculator.logic.CalculatorStack;
import srs.calculator.operations.Pop;
import srs.calculator.operations.Operation;

import java.util.ArrayList;

public class PopTest {
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
    public void PopTest1() {
        args.add("abacaba");
        args.add("3");
        context.push(5);
        context.push(9);
        Operation pop = new Pop(context, args.toArray(new Object[0]));
        try {
            pop.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void PopTest2() {
        Operation pop = new Pop(context, args.toArray(new Object[0]));
        try {
            pop.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void PopTest3() {
        context.push(300);
        Operation pop = new Pop(context, args.toArray(new Object[0]));
        try {
            pop.exec();
            Assertions.assertEquals(context.getStackLength(), 0);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void PopTest4() {
        context.push(300);
        context.push(50);
        Operation pop = new Pop(context, args.toArray(new Object[0]));
        try {
            pop.exec();
            Assertions.assertEquals(context.getStackLength(), 1);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }
}
