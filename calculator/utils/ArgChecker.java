package calculator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ArgChecker {
    public static boolean isDouble(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e)
        {
            return false;
        }
    }
    public static void regularSpecialSymbols(String arg) throws RegularSpecialSymbolsException {
        Pattern special = Pattern.compile("[!@$%&()_=|<>?{}\\[\\]~]");
        Matcher hasSpecial = special.matcher(arg);

        if (hasSpecial.find())
        {
            throw new RegularSpecialSymbolsException();
        }
    }
}
