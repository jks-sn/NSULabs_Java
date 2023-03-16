package srs.calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import srs.calculator.exceptions.OperatorException;
import srs.calculator.logic.CalculatorStack;
import srs.calculator.operations.Define;
import srs.calculator.operations.Operation;

import java.util.ArrayList;

public class DefineTest {
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
    public void DefineTest1() {
        args.add("abacaba");
        args.add("3");
        Operation define = new Define(context, args.toArray(new Object[0]));
        try {
            define.exec();
            context.push("abacaba");
            Assertions.assertEquals(context.peek(), 3);
        } catch (OperatorException e) {
            Assertions.fail();
        }
    }

    @Test
    public void DefineTest2() {
        args.add("!@#$#$");
        args.add(33);
        Operation define = new Define(context, args.toArray(new Object[0]));
        try {
            define.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void DefineTest3() {
        args.add(33);
        args.add("33");
        Operation define = new Define(context, args.toArray(new Object[0]));
        try {
            define.exec();
            Assertions.fail();
        } catch (Throwable e) {
            Assertions.assertEquals(0, 0);
        }
    }
}
