package mvc.controller.commonFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForCheckPenalty {

    public static boolean checkNumeric(String num){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        return pattern.matcher(num).matches();
    }

    public static boolean checkDatesPenalties(String from,String to){
        String patternofdate = "^\\d{4}-\\d{2}-\\d{2}$";

        Pattern pattern = Pattern.compile(patternofdate);

        Matcher matcher = pattern.matcher(from);
        Matcher matcher2=pattern.matcher(to);
        if (matcher.matches() && matcher2.matches()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date itvFrom;
                Date itvTo;
                itvFrom = sdf.parse(from);
                itvTo=sdf.parse(to);

                Calendar cal = Calendar.getInstance();
                Calendar cal2= Calendar.getInstance();

                cal.setTime(itvFrom);
                cal2.setTime(itvTo);

                return cal.compareTo(cal2) < 0;

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }
}
