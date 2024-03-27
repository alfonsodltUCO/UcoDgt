package mvc.controller.commonFunctions;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.admin.ManagerAdmin;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;

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
        String regex = "^[a-zA-Z ]+$";
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

    public static void checkAdminEmailNotExists(String email, Context context, UserCallback callback){
        ManagerAdmin mngAd = new ManagerAdmin();
        Log.d("AAAAA","entro admin");

        AdminDTO admin = new AdminDTO(null,null,null,null,null,email);
        mngAd.checkEmailNotExists(admin, context, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {
                callback.onAdminReceived(user);
            }
        });
    }
    public static void checkClientEmailNotExists(String email, Context context, UserCallback callback){
        ManagerClient mngCl = new ManagerClient();
        Log.d("AAAAA","entro client");

        ClientDTO client = new ClientDTO(null,null,null,null,null,email,null);
        mngCl.checkEmailNotExists(client, context, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
            }
            @Override
            public void onError(VolleyError error) {}
            @Override
            public void onWorkerReceived(WorkerDTO user) {}
            @Override
            public void onAdminReceived(AdminDTO user) {}
        });
    }

    public static void checkWorkerEmailNotExists(String email, Context context, UserCallback callback){
        ManagerWorker mngWk = new ManagerWorker();
        Log.d("AAAAA","entro worker");

        WorkerDTO worker = new WorkerDTO(null,null,null,null,null,email,null);
        mngWk.checkEmailNotExists(worker, context, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {}
            @Override
            public void onError(VolleyError error) {}
            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
            }
            @Override
            public void onAdminReceived(AdminDTO user) {}
        });
    }

    public static boolean checkValidEmail(String email){
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
                "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean checkDateOfBirth(String age){
        Log.d("AAAAA",age);
        String patternofdate = "^\\d{4}-\\d{2}-\\d{2}$";

        Pattern pattern = Pattern.compile(patternofdate);

        Matcher matcher = pattern.matcher(age);

        if (matcher.matches()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birth;
            try {
                birth = sdf.parse(age);
                Date now = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(now);
                cal.add(Calendar.YEAR, -18);
                Date older = cal.getTime();

                if (birth.before(older)) {
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

    public static boolean checkPassword(String password){

        if (password.length() > 8) {
            boolean upper = false;
            boolean number = false;
            boolean letterorsymbol = false;
            boolean special = false;

            Pattern specialCharacter = Pattern.compile("[?!¡@¿.,´)]");
            Matcher hasSpecial = specialCharacter.matcher(password);

            int i;
            char l;

            for (i = 0; i < password.length(); i++) {
                l = password.charAt(i);

                if (Character.isDigit(l)) {
                    number = true;
                }
                if (Character.isLetter(l)) {
                    letterorsymbol = true;
                }
                if (Character.isUpperCase(l)) {
                    upper = true;
                }
                if (hasSpecial.find()) {
                    special = true;
                }
            }

            if (number == true && upper == true && letterorsymbol == true && special == true) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
