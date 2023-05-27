package calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import calculator.exceptions.OperatorException;
import calculator.logic.CalculatorStack;
import calculator.operations.Operation;
import calculator.operations.Print;

import java.util.ArrayList;

public class PrintTest {
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
    public void PrintTest1() {
        args.add("abacaba");
        args.add("3");
        context.push(5);
        context.push(9);
        Operation print = new Print(context, args.toArray(new Object[0]));
        try {
            print.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }
    @Test
    public void PrintTest2() {
        Operation print = new Print(context, args.toArray(new Object[0]));
        try {
            print.exec();
            Assertions.fail();
        } catch (OperatorException e) {
            Assertions.assertEquals(0, 0);
        }
    }
}
