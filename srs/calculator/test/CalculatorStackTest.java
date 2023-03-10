package srs.calculator.test;

import srs.calculator.exceptions.OperatorException;
import srs.calculator.exceptions.StackException;
import srs.calculator.logic.CalculatorStack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class CalculatorStackTest {
    static CalculatorStack context;

    @BeforeAll
    static void createStack() {
        context = new CalculatorStack();
    }

    @Test
    public void getStackLength1() {
        context.push(9);
        context.clear();
        Assert.assertEquals(context.getStackLength(), 0);
    }

    public void getStackLength2() {
        context.push(33);
        context.push(33);
        Assert.assertEquals(context.getStackLength(), 2);
    }

    public void Push1() {
        try {
            context.push("abacaba");
            Assert.assertEquals(0, 1);
        } catch (StackException ignored) {
            Assert.assertEquals(0, 0);
        }
    }

    public void Push2()  {
        context.push(99);
        Assert.assertEquals(context.getStackLength(), 1);
    }
    public void Push3()  {
        try {
            context.push("!@#$");
            Assert.assertEquals(0, 1);
        } catch (StackException ignored) {
            Assert.assertEquals(0, 0);
        }
    }
    public void Push4() {
        try {
            context.createVariable("abacaba",4);
            context.push("abacaba");
            Assert.assertEquals(context.getStackLength(), 1);
        } catch (StackException e) {
            Assert.assertEquals(0, 1);
        }
    }
    public void Push5() {
        try {
            context.createVariable("!@#$",4);
            context.push("!@#$");
            Assert.assertEquals(0, 1);
        } catch (StackException e) {
            Assert.assertEquals(1, 1);
        }
    }

    public void Pop1() throws OperatorException {
        context.push(33);
        context.pop();
        Assert.assertEquals(context.getStackLength(), 0);
    }
    public void Pop2() throws OperatorException {
        context.push(33);
        context.push(33);
        context.pop();
        Assert.assertEquals(context.getStackLength(), 1);
    }
    public void Pop3() throws StackException {
        context.pop();
    }
    public void Peek1() {
        try {
            context.peek();
            Assert.assertEquals(0, 1);
        }
        catch(StackException e) {
            Assert.assertEquals(0, 0);
        }
    }

    @AfterEach
    void cleanStack() {
        context.clear();
    }
}
