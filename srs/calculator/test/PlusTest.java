package srs.calculator.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import srs.calculator.logic.CalculatorStack;

import java.util.ArrayList;

public class PlusTest {
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
}
