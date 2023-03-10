package srs.calculator.main;

import srs.calculator.logic.InteractiveCalc;
import srs.calculator.logic.OfflineCalc;

public class Main {
    public static void main(String[] args)  {
        if(args.length > 0) {
            OfflineCalc calc = new OfflineCalc(args[0]);
            calc.execute();
        }
        else {
            System.out.println("Hello, World!");
            InteractiveCalc calc = new InteractiveCalc();
            calc.execute();
        }
    }
}
