package calculator.test;

import calculator.exceptions.OperatorException;
import calculator.exceptions.StackException;
import calculator.logic.CalculatorStack;
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
    public void getStackLength1() throws OperatorException {
        context.push(9);
        context.clear();
        Assert.assertEquals(context.getStackLength(), 0);
    }
    public void getStackLength2() throws OperatorException {
        context.push(33);
        context.push(33);
        Assert.assertEquals(context.getStackLength(), 2);
    }
        public void Push1() throws OperatorException {
            try {
                context.push("abacaba");
                Assert.assertEquals(0, 1);
            } catch (StackException ignored) {
                Assert.assertEquals(0, 0);
            }
        }
        public void Pop1() throws OperatorException{
        context.push(33);
        context.pop();
        Assert.assertEquals(context.getStackLength(), 0);
    }

    @AfterEach
    void cleanStack() {
        context.clear();
    }
}
