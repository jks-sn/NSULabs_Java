package calculator.test;

import calculator.exceptions.OperatorException;
import calculator.exceptions.StackException;
import calculator.logic.CalculatorStack;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorStackTest {
    @Test
    public void getStackLength() throws OperatorException {
        CalculatorStack context = new CalculatorStack();
        context.push(9);
        context.clear();
        Assert.assertEquals(context.getStackLength(), 0);
        context.clear();

        context.push(33);
        context.push(33);
        Assert.assertEquals(context.getStackLength(), 2);
        context.clear();
        try{
            context.push("abacaba");
            Assert
        }
        catch(StackException ignored){

        }

        context.push(33);
        context.pop();
        Assert.assertEquals(context.getStackLength(),0);


    }
}
