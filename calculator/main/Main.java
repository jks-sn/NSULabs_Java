package calculator.main;

import calculator.logic.InteractiveCalc;
import calculator.logic.OfflineCalc;

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
