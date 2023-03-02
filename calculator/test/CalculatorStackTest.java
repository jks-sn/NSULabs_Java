package calculator.test;

import calculator.logic.CalculatorStack;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorStackTest {
@Test
    public long getStackLength() {
    CalculatorStack context = new CalculatorStack();
    context.push(9);
    context.clear();
    Assert.assertEquals(context.getStackLength(),0);

    return 0;
}
}
