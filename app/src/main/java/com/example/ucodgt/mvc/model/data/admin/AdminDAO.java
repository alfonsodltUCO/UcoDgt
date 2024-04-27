package com.example.ucodgt.mvc.model.data.admin;

import android.annotation.SuppressLint;
import android.content.Context;

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

import com.example.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.example.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.example.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.example.ucodgt.mvc.model.data.UserCallback;

/**
 * Data Access Object (DAO) class for admin users. Handles operations related to admin user data retrieval and verification.
 * @author Alfonso de la torre
 */
public class AdminDAO {

    RequestQueue requestQueue;

    /**
     * Checks the login credentials of an admin user.
     *
     * @param userToFind The AdminDTO object containing the login credentials to be checked.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the login check.
     */
    public void checkLogInAdmin(AdminDTO userToFind, Context applicationContext, UserCallback callback){


        requestQueue= Volley.newRequestQueue(applicationContext);
        checkAdmin(userToFind, new UserCallback() {
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

    /**
     * Sends a request to the server to check if the provided user credentials belong to an admin user.
     *
     * @param userToFind The AdminDTO object containing the user credentials to be checked.
     * @param callback The callback to handle the result of the user check.
     */
    private void checkAdmin(final AdminDTO userToFind,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/checkLoginAdmin.php?email="+userToFind.getEmail();

        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {

                    try {
                        String name = response.getString("name");
                        String surname = response.getString("surname");
                        String email1 = response.getString("email");
                        String passwordhashed = response.getString("password");

                        if (!BCrypt.checkpw(userToFind.getPassword(), passwordhashed)) {
                            callback.onError(new VolleyError());
                        } else {
                            String age = response.getString("age");
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date dateBirth;
                            try {
                                dateBirth = format.parse(age);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            AdminDTO user = new AdminDTO(null, userToFind.getPassword(), name, surname, dateBirth, email1);
                            callback.onAdminReceived(user);
                        }

                    } catch (JSONException e) {
                        AdminDTO user = new AdminDTO(null, null, null, null, null, null);

                        callback.onAdminReceived(user);
                    }
                },
                callback::onError
        );

        requestQueue.add(JsonObjectRequest);
    }

    /**
     * Checks if the provided email belongs to an admin user.
     *
     * @param userToFind The AdminDTO object containing the email to be checked.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the email check.
     */

    public void checkEmailAdmin(AdminDTO userToFind, Context applicationContext, UserCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        checkAdminEmail(userToFind, new UserCallback() {
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

    /**
     * Sends a request to the server to check if the provided email belongs to an admin user.
     *
     * @param userToFind The AdminDTO object containing the email to be checked.
     * @param callback The callback to handle the result of the email check.
     */
    private void checkAdminEmail(final AdminDTO userToFind,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/checkLoginAdmin.php?email="+userToFind.getEmail();

        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {

                    try {
                        String name = response.getString("name");
                        String surname = response.getString("surname");
                        String email1 = response.getString("email");
                        String age = response.getString("age");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirth;
                        try {
                            dateBirth = format.parse(age);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        AdminDTO user = new AdminDTO(null, null, name, surname, dateBirth, email1);
                        callback.onAdminReceived(user);
                    } catch (JSONException e) {
                        AdminDTO user = new AdminDTO(null, null, null, null, null, null);
                        callback.onAdminReceived(user);
                    }
                },
                callback::onError
        );

        requestQueue.add(JsonObjectRequest);
    }
}