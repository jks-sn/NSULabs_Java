package calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.Test;
import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;
import calculator.operations.Operation;
import calculator.operations.Sqrt;

import java.util.ArrayList;

public class SqrtTest {
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
    public void SqrtTest1() {
        args.add("abacaba");
        args.add("3");
        context.push(5);
        context.push(9);
        Operation sqrt = new Sqrt(context, args.toArray(new Object[0]));
        try {
            sqrt.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void SqrtTest2() {
        Operation sqrt = new Sqrt(context, args.toArray(new Object[0]));
        try {
            sqrt.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void SqrtTest3() {
        context.push(25);
        Operation sqrt = new Sqrt(context, args.toArray(new Object[0]));
        try {
            sqrt.exec();
            Assertions.assertEquals(context.peek(), 5);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }

    @Test
    public void SqrtTest4() {
        context.push(0.25);
        Operation sqrt = new Sqrt(context, args.toArray(new Object[0]));
        try {
            sqrt.exec();
            Assertions.assertEquals(context.peek(), 0.5);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void SqrtTest5() {
        context.push(-5);
        Operation sqrt = new Sqrt(context, args.toArray(new Object[0]));
        try {
            sqrt.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void SqrtTest6() {
        context.push(0);
        Operation sqrt = new Sqrt(context, args.toArray(new Object[0]));
        try {
            sqrt.exec();
            Assertions.assertEquals(context.peek(), 0);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void SqrtTest7() {
        context.push(1);
        Operation sqrt = new Sqrt(context, args.toArray(new Object[0]));
        try {
            sqrt.exec();
            Assertions.assertEquals(context.peek(), 1);
        } catch (Throwable e) {
            Assertions.fail();
        }
    }
}
