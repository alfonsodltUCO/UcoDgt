package mvc.controller.commonFunctions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForCheckUsertoAdd {
    public static boolean checkDni(String dni){//used in CheckUserToadd
        Pattern pattern = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
        Matcher mat = pattern.matcher(dni);
        return mat.matches();
    }
    public static boolean checkNameAndSUrname(String name,String surname){
        if(name.length()<3){
            return false;
        }
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()){
            return false;
        }
        matcher=pattern.matcher(surname);
        if(!matcher.matches()){
            return false;
        }
        return true;
    }

    public static boolean checkLicencePoints(String licencePoints){
        if (licencePoints.length()>2){
            return false;
        }
        for(int i=0;i<licencePoints.length();i++){
            char num=licencePoints.charAt(0);
            if(!Character.isDigit(num)){
                return false;
            }
        }
        Integer number=Integer.parseInt(licencePoints);
        if(number!=8){
            return false;
        }
        return true;
    }
}
