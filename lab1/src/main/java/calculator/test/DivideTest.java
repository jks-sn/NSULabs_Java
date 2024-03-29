package calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;
import calculator.operations.Divide;
import calculator.operations.Operation;

import java.util.ArrayList;

public class DivideTest {
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
    public void DivideTest1() {
        args.add("abacaba");
        args.add("3");
        context.push(5);
        context.push(9);
        Operation divide = new Divide(context, args.toArray(new Object[0]));
        try {
            divide.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void DivideTest2() {
        context.push(3);
        Operation divide = new Divide(context, args.toArray(new Object[0]));
        try {
            divide.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void DivideTest3() {
        context.push(300);
        context.push(0);
        Operation divide = new Divide(context, args.toArray(new Object[0]));
        try {
            divide.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void DivideTest4() {
        context.push(3);
        context.push(1);
        Operation divide = new Divide(context, args.toArray(new Object[0]));
        try {
            divide.exec();
            Assertions.assertEquals(context.peek(), 3);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void DivideTest5() {
        context.push(55);
        context.push(11);
        Operation divide = new Divide(context, args.toArray(new Object[0]));
        try {
            divide.exec();
            Assertions.assertEquals(context.peek(), 5);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void DivideTest6() {
        context.push(60);
        context.push(600);
        Operation divide = new Divide(context, args.toArray(new Object[0]));
        try {
            divide.exec();
            Assertions.assertEquals(context.peek(), 0.1);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void DivideTest7() {
        context.push(0);
        context.push(699);
        Operation divide = new Divide(context, args.toArray(new Object[0]));
        try {
            divide.exec();
            Assertions.assertEquals(context.peek(), 0.0);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void DivideTest8() {
        context.push(Double.MAX_VALUE);
        context.push(0.5);
        Operation divide = new Divide(context, args.toArray(new Object[0]));
        try {
            divide.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }
}
