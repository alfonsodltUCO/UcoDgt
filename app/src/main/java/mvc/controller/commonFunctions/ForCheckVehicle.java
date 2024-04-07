package mvc.controller.commonFunctions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForCheckVehicle {
    public static boolean checkPlate(String plate) {
        String regex = "\\d{4}[A-Z]{3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(plate);
        return matcher.find();
    }
}
