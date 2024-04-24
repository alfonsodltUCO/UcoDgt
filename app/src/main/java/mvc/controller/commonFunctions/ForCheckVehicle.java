package mvc.controller.commonFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mvc.model.business.vehicle.typeof;
import mvc.model.business.vehicle.typeofColor;

/**
 * A utility class providing common functions for checking vehicle-related data.
 * @author Alfonso de la torre
 */
public class ForCheckVehicle {

    /**
     * Checks if the provided plate number matches the standard format.
     * @param plate The plate number to be checked.
     * @return True if the plate number matches the standard format, otherwise false.
     */
    public static boolean checkPlate(String plate) {
        String regex = "\\d{4}[A-Z]{3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(plate);
        return matcher.find();
    }

    /**
     * Checks if the provided dates are in the correct format and if the end date is after the start date.
     * @param from The start date.
     * @param to The end date.
     * @return True if both dates are in the correct format and the end date is after the start date, otherwise false.
     */
    public static boolean checkDates(String from, String to){
        String patternofdate = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(patternofdate);
        Matcher matcher = pattern.matcher(from);
        Matcher matcher2 = pattern.matcher(to);

        if (matcher.matches() && matcher2.matches()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date itvFrom = sdf.parse(from);
                Date itvTo = sdf.parse(to);

                Calendar cal = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                cal.setTime(itvFrom);
                cal2.setTime(itvTo);

                Calendar currentDate = Calendar.getInstance();

                if (cal.compareTo(currentDate) < 0) {
                    return false;
                }
                return cal.compareTo(cal2) < 0;

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }

    /**
     * Checks if the provided color matches any predefined vehicle color.
     * @param color The color to be checked.
     * @return True if the color matches any predefined vehicle color, otherwise false.
     */
    public static boolean checkColor(String color) {
        for (typeofColor col : typeofColor.values()) {
            if (col.name().equalsIgnoreCase(color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the vehicle type (model) based on the provided type string.
     * @param type The type string representing the vehicle type.
     * @return The corresponding typeof enum value if found, otherwise null.
     */
    public static typeof getTypeOf(String type){
        for (typeof tp : typeof.values()) {
            if (tp.name().equalsIgnoreCase(type)) {
                return tp;
            }
        }
        return null;
    }

    /**
     * Retrieves the vehicle color type based on the provided color string.
     * @param color The color string representing the vehicle color.
     * @return The corresponding typeofColor enum value if found, otherwise null.
     */
    public static typeofColor getTypeOfColor(String color){
        for (typeofColor col : typeofColor.values()) {
            if (col.name().equalsIgnoreCase(color)) {
                return col;
            }
        }
        return null;
    }

    /**
     * Checks if the provided vehicle type matches any predefined vehicle type.
     * @param type The type string representing the vehicle type.
     * @return True if the type matches any predefined vehicle type, otherwise false.
     */
    public static boolean checkType(String type){
        for (typeof tp : typeof.values()) {
            if (tp.name().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
