package srs.calculator.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import srs.calculator.exceptions.OperatorException;
import srs.calculator.exceptions.StackException;
import srs.calculator.logic.CalculatorStack;


public class CalculatorStackTest {
    static CalculatorStack context;

    @BeforeAll
    static void createStack() {
        context = new CalculatorStack();
    }

    @AfterEach
    void cleanStack() {
        context.clear();
    }

    @Test
    public void getStackLength1() {
        context.push(9);
        context.clear();
        Assertions.assertEquals(context.getStackLength(), 0);
    }

    @Test
    public void getStackLength2() {
        context.push(33);
        context.push(33);
        Assertions.assertEquals(context.getStackLength(), 2);
    }

    @Test
    public void Push1() {
        try {
            context.push("abacaba");
            Assertions.fail();
        } catch (StackException ignored) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void Push2() {
        context.push(99);
        Assertions.assertEquals(context.getStackLength(), 1);
    }

    @Test
    public void Push3() {
        try {
            context.push("!@#$");
            Assertions.assertEquals(0, 1);
        } catch (StackException ignored) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void Push4() {
        try {
            context.createVariable("abacaba", 4);
            context.push("abacaba");
            Assertions.assertEquals(context.getStackLength(), 1);
        } catch (StackException e) {
            Assertions.assertEquals(0, 1);
        }
    }

    @Test
    public void Push5() {
        try {
            context.createVariable("!@#$", 4);
            context.push("!@#$");
            Assertions.assertEquals(1, 1);
        } catch (StackException e) {
            Assertions.assertEquals(0, 1);
        }
    }

    @Test
    public void Pop1() throws OperatorException {
        context.push(33);
        context.pop();
        Assertions.assertEquals(context.getStackLength(), 0);
    }

    @Test
    public void Pop2() throws OperatorException {
        context.push(33);
        context.push(33);
        context.pop();
        Assertions.assertEquals(context.getStackLength(), 1);
    }

    @Test
    public void Pop3() throws StackException {
        try {
            context.pop();
            Assertions.fail();
        }
        catch(StackException e)
        {
            Assertions.assertEquals(1, 1);
        }
    }

    @Test
    public void Peek1() {
        try {
            context.peek();
            Assertions.assertEquals(0, 1);
        } catch (StackException e) {
            Assertions.assertEquals(0, 0);
        }
    }

}
