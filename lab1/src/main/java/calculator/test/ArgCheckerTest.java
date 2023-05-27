package calculator.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import calculator.exceptions.RegularSpecialSymbolsException;
import calculator.utils.ArgChecker;

public class ArgCheckerTest {
    @org.junit.Test
    public void ArgCheckerTest1() {
        if (ArgChecker.isDouble("3"))
            Assertions.assertEquals(0, 0);
        else
            Assertions.fail();
    }

    @Test
    public void ArgCheckerTest2() {
        if (ArgChecker.isDouble("abacaba"))
            Assertions.fail();
        else
            Assertions.assertEquals(0, 0);
    }

    @Test
    public void ArgCheckerTest3() {
        try {
            ArgChecker.regularSpecialSymbols("!@#!@#@#@!");
            Assertions.fail();
        } catch (RegularSpecialSymbolsException e) {
            Assertions.assertEquals(0, 0);
        }
    }

    @Test
    public void ArgCheckerTest4() {
        try {
            ArgChecker.regularSpecialSymbols("abacaba");
            Assertions.assertEquals(0, 0);
        } catch (RegularSpecialSymbolsException e) {
            Assertions.fail();
        }
    }
}
