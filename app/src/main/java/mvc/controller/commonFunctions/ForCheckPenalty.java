package mvc.controller.commonFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mvc.model.business.penalty.stateof;
import mvc.model.business.penalty.typeof;

public class ForCheckPenalty {

    public static boolean checkNumeric(String num){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        return pattern.matcher(num).matches();
    }
    public static boolean checkDate(String date){
        String patternofdate = "^\\d{4}-\\d{2}-\\d{2}$";

        Pattern pattern = Pattern.compile(patternofdate);

        Matcher matcher = pattern.matcher(date);

        if (matcher.matches()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date penalty;
            try {
                penalty = sdf.parse(date);
                Date now = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(now);
                cal.add(Calendar.HOUR, -25);
                Date older = cal.getTime();
                cal.setTime(now);
                cal.add(Calendar.HOUR, 0);
                Date newer=cal.getTime();
                assert penalty != null;
                if (penalty.before(older)) {
                    return false;
                } else {
                    if(newer.before(penalty)){
                       return false;
                    }else{
                        return true;
                    }
                }
            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }


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

    public static boolean checkStateOf(String state){
        for (stateof tp : stateof.values()) {
            if (tp.name().equalsIgnoreCase(state)) {
                return true;
            }
        }
        return false;
    }
    public static boolean checkPoints(String points) {
        try {
            int num = Integer.parseInt(points);
            return num >=0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkQuantity(String quantity) {
        try {
            float num = Float.parseFloat(quantity);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkReasonOf(String reason){
        for (typeof tp : typeof.values()) {
            if (tp.name().equalsIgnoreCase(reason)) {
                return true;
            }
        }
        return false;
    }
}
