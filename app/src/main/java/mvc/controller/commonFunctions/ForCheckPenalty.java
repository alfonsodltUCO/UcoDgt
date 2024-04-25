package mvc.controller.commonFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mvc.model.business.penalty.stateof;
import mvc.model.business.penalty.typeof;

/**
 * This class provides common functions for checking penalty-related information.
 * @author Alfonso de la torre
 */
public class ForCheckPenalty {

    /**
     * Checks if the provided string represents a numeric value.
     *
     * @param num The string to check.
     * @return True if the string represents a numeric value, false otherwise.
     */
    public static boolean checkNumeric(String num){
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        return pattern.matcher(num).matches();
    }
    /**
     * Checks if the provided string represents a valid date in the format "yyyy-MM-dd".
     *
     * @param date The string representing the date.
     * @return True if the string represents a valid date, false otherwise.
     */
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
    /**
     * Checks if the provided start and end dates are valid.
     *
     * @param from The start date.
     * @param to   The end date.
     * @return True if the start date is before the end date, false otherwise.
     */
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
    /**
     * Checks if the provided state string corresponds to a valid state of penalty.
     *
     * @param state The state string to check.
     * @return True if the state is valid, false otherwise.
     */
    public static boolean checkStateOf(String state){
        for (stateof tp : stateof.values()) {
            if (tp.name().equalsIgnoreCase(state)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the provided string represents a non-negative integer.
     *
     * @param points The string to check.
     * @return True if the string represents a non-negative integer, false otherwise.
     */
    public static boolean checkPoints(String points) {
        try {
            int num = Integer.parseInt(points);
            return num >=0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Checks if the provided string represents a non-negative float number.
     *
     * @param quantity The string to check.
     * @return True if the string represents a non-negative float number, false otherwise.
     */
    public static boolean checkQuantity(String quantity) {
        try {
            float num = Float.parseFloat(quantity);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Checks if the provided reason string corresponds to a valid reason of penalty.
     *
     * @param reason The reason string to check.
     * @return True if the reason is valid, false otherwise.
     */
    public static boolean checkReasonOf(String reason){
        for (typeof tp : typeof.values()) {
            if (tp.name().equalsIgnoreCase(reason)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the provided CVV, card number, and expiration date form a valid card data.
     *
     * @param cvv       The CVV (Card Verification Value).
     * @param number    The card number.
     * @param caducity  The expiration date of the card.
     * @return True if the card data is valid, false otherwise.
     */
    public static boolean checkCardData(String cvv, String number, String caducity) {
        if (!number.matches("\\d{16}")) {
            return false;
        }

        if (!caducity.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        String[] parts = caducity.split("/");
        int expMonth = Integer.parseInt(parts[0]);
        int expYear = Integer.parseInt(parts[1]);

        if (expYear < calendar.get(Calendar.YEAR) % 100) {
            return false;
        }
        if (expYear == calendar.get(Calendar.YEAR) % 100 && expMonth < calendar.get(Calendar.MONTH) + 1) {
            return false;
        }

        if (!cvv.matches("\\d{3}")) {
            return false;
        }
        return true;
    }
}
