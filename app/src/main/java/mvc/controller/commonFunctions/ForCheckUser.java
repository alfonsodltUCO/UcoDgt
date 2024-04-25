package mvc.controller.commonFunctions;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.VolleyError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.admin.ManagerAdmin;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;

/**
 * This class provides various utility methods for checking user-related information, such as DNI, name, surname, email, date of birth, and password.
 * @author Alfonso de la torre
 */

public class ForCheckUser {
    /**
     * Checks if the provided DNI (National Identity Document) is valid.
     *
     * @param dni The DNI to check.
     * @return True if the DNI is valid, false otherwise.
     */
    public static boolean checkDni(String dni){//used in CheckUserToadd
        Pattern pattern = Pattern.compile("[0-9]{8}[A-Z]");
        Matcher mat = pattern.matcher(dni);
        return mat.matches();
    }
    /**
     * Checks if the provided name and surname are valid.
     *
     * @param name    The name to check.
     * @param surname The surname to check.
     * @return True if both name and surname are valid, false otherwise.
     */
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
        return matcher.matches();
    }
    /**
     * Checks if the provided license points are valid.
     *
     * @param licencePoints The license points to check.
     * @return True if the license points are valid, false otherwise.
     */
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
        int number=Integer.parseInt(licencePoints);
        return number == 8;
    }

    /**
     * Checks if the provided email address for an admin user does not already exist in the database.
     *
     * @param email    The email address to check.
     * @param context  The context of the application.
     * @param callback The callback interface for handling the result of the email existence check.
     */
    public static void checkAdminEmailNotExists(String email, Context context, UserCallback callback){
        ManagerAdmin mngAd = new ManagerAdmin();

        AdminDTO admin = new AdminDTO(null,null,null,null,null,email);
        mngAd.checkEmailNotExists(admin, context, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {
                callback.onAdminReceived(user);
            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }
    /**
     * Checks if the provided email address for a client user does not already exist in the database.
     *
     * @param email    The email address to check.
     * @param context  The context of the application.
     * @param callback The callback interface for handling the result of the email existence check.
     */
    public static void checkClientEmailNotExists(String email, Context context, UserCallback callback){
        ManagerClient mngCl = new ManagerClient();

        ClientDTO client = new ClientDTO(null,null,null,null,null,email,null);
        mngCl.checkEmailNotExists(client, context, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
            }
            @Override
            public void onError(VolleyError error) {callback.onError(error);
            }
            @Override
            public void onWorkerReceived(WorkerDTO user) {}
            @Override
            public void onAdminReceived(AdminDTO user) {}

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }
    /**
     * Checks if the provided email address for a worker user does not already exist in the database.
     *
     * @param email    The email address to check.
     * @param context  The context of the application.
     * @param callback The callback interface for handling the result of the email existence check.
     */
    public static void checkWorkerEmailNotExists(String email, Context context, UserCallback callback){
        ManagerWorker mngWk = new ManagerWorker();

        WorkerDTO worker = new WorkerDTO(null,null,null,null,null,email,null);
        mngWk.checkEmailNotExists(worker, context, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {}
            @Override
            public void onError(VolleyError error) {callback.onError(error);
            }
            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
            }
            @Override
            public void onAdminReceived(AdminDTO user) {}

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }

    /**
     * Checks if the provided email address is valid.
     *
     * @param email The email address to check.
     * @return True if the email address is valid, false otherwise.
     */
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
    /**
     * Checks if the provided date of birth indicates that the user is at least 18 years old.
     *
     * @param age The date of birth to check.
     * @return True if the user is at least 18 years old, false otherwise.
     */
    public static boolean checkDateOfBirth(String age){
        String patternofdate = "^\\d{4}-\\d{2}-\\d{2}$";

        Pattern pattern = Pattern.compile(patternofdate);

        Matcher matcher = pattern.matcher(age);

        if (matcher.matches()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

    /**
     * Checks if the provided password meets certain complexity criteria.
     *
     * @param password The password to check.
     * @return True if the password meets the complexity criteria, false otherwise.
     */
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
            if (number && upper && letterorsymbol && special) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
