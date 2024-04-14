package mvc.controller.commonFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mvc.model.business.vehicle.typeof;
import mvc.model.business.vehicle.typeofColor;

public class ForCheckVehicle {
    public static boolean checkPlate(String plate) {
        String regex = "\\d{4}[A-Z]{3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(plate);
        return matcher.find();
    }
    public static boolean checkDates(String from,String to){
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
                if (cal.compareTo(cal2) < 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }
    public static boolean checkColor(String color) {
        for (typeofColor col : typeofColor.values()) {
            if (col.name().equalsIgnoreCase(color)) {
                return true;
            }
        }
        return false;
    }

    public static typeof getTypeOf(String type){
        for (typeof tp : typeof.values()) {
            if (tp.name().equalsIgnoreCase(type)) {
                return tp;
            }
        }
        return null;
    }

    public static typeofColor getTypeOfColor(String color){
        for (typeofColor col : typeofColor.values()) {
            if (col.name().equalsIgnoreCase(color)) {
                return col;
            }
        }
        return null;
    }

    public static boolean checkType(String type){
        for (typeof tp : typeof.values()) {
            if (tp.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
