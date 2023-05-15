package calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import calculator.exceptions.FactoryException;
import calculator.exceptions.OperatorException;
import calculator.factory.ConfigParser;
import calculator.logic.CalculatorStack;
import calculator.operations.Operation;
import calculator.operations.Sqrt;

import java.util.ArrayList;

public class ConfigParserTest {
    static CalculatorStack context;
    static private ArrayList<Object> args;
    static ConfigParser parser;
    @BeforeAll
    static void createStack() {
        context = new CalculatorStack();
        args = new ArrayList<>();
        parser = new ConfigParser();
    }
    @AfterEach
    void cleanStack() {
        context.clear();
        args.clear();
    }
    @Test
    public void ConfigParserTest1() {
        try {
            parser.getOperationClass(context,"abacaba",args);
            Assertions.fail();
        } catch (FactoryException e) {
            Assertions.assertEquals(0, 0);
        }
    }
}
