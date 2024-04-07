package mvc.model.data.admin;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;

public class AdminDAO {

    RequestQueue requestQueue;


    public void checkLogInAdmin(AdminDTO userToFind, Context applicationContext, UserCallback callback){
        String email=userToFind.getEmail();
        String password= userToFind.getPassword();
        requestQueue= Volley.newRequestQueue(applicationContext);
        checkAdmin(email,password, new UserCallback() {
            @Override
            public void onAdminReceived(AdminDTO user) {

                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setDni(user.getDni());
                callback.onAdminReceived(userToFind);

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }

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
        });
    }
    private void checkAdmin(final String email,final String password,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/checkLoginAdmin.php?email="+email;

        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {

                    try {
                        String name=response.getString("name");
                        String surname=response.getString("surname");
                        String email1 =response.getString("email");
                        String passwordhashed=response.getString("password");
                        if(!BCrypt.checkpw(password,passwordhashed)){
                            callback.onError(new VolleyError());
                        }else {
                            String age=response.getString("age");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date dateBirth;
                            try {
                                dateBirth = format.parse(age);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            AdminDTO user=new AdminDTO(null,password,name,surname,dateBirth, email1);
                            callback.onAdminReceived(user);
                        }

                    } catch (JSONException e) {
                        AdminDTO user=new AdminDTO(null,null,null,null,null,null);

                        callback.onAdminReceived(user);
                    }


                },
                error -> {
                    callback.onError(error);
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void checkEmailAdmin(AdminDTO userToFind, Context applicationContext, UserCallback callback){
        String email= userToFind.getEmail();
        requestQueue= Volley.newRequestQueue(applicationContext);
        checkAdminEmail(email, new UserCallback() {
            @Override
            public void onAdminReceived(AdminDTO user) {

                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setDni(user.getDni());
                callback.onAdminReceived(userToFind);

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }

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
        });
    }
    private void checkAdminEmail(final String email,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/checkLoginAdmin.php?email="+email;

        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {

                    try {
                        String name=response.getString("name");
                        String surname=response.getString("surname");
                        String email1 =response.getString("email");
                        String age=response.getString("age");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirth;
                        try {
                            dateBirth = format.parse(age);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        AdminDTO user=new AdminDTO(null,null,name,surname,dateBirth, email1);
                        callback.onAdminReceived(user);
                    } catch (JSONException e) {
                        AdminDTO user=new AdminDTO(null,null,null,null,null,null);
                        callback.onAdminReceived(user);
                    }
                },
                error ->{
                    callback.onError(error);
                }
        );

        requestQueue.add(JsonObjectRequest);
    }
}