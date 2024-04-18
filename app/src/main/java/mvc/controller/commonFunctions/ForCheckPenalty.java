package mvc.controller.commonFunctions;

import java.util.regex.Pattern;

public class ForCheckPenalty {

    public static boolean checkNumeric(String num){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        return pattern.matcher(num).matches();
    }
}
