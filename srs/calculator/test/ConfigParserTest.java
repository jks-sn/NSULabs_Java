package srs.calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import srs.calculator.exceptions.FactoryException;
import srs.calculator.exceptions.OperatorException;
import srs.calculator.factory.ConfigParser;
import srs.calculator.logic.CalculatorStack;
import srs.calculator.operations.Operation;
import srs.calculator.operations.Sqrt;

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
