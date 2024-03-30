package mvc.model.data.client;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;

public class ClientDAO {

    RequestQueue requestQueue;

    public void checkLogInClient(ClientDTO userToFind, Context applicationContext, UserCallback callback){

        String email=userToFind.getEmail().toString();
        String password= userToFind.getPassword().toString();
        requestQueue= Volley.newRequestQueue(applicationContext);
        ClientDTO usr = userToFind;
        checkClient(email,password, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                usr.setEmail(user.getEmail());
                usr.setAge(user.getAge());
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setPassword(user.getPassword());
                usr.setLicencepoints(user.getLicencepoints());
                usr.setDni(user.getDni());
                callback.onUserReceived(usr);

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

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void checkClient(final String email,final String password,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/checkLoginClient.php?email="+email;

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
                            Integer licencep=Integer.parseInt(response.getString("licencepoints"));
                            ClientDTO user=new ClientDTO(null,password,name,surname,dateBirth, email1,licencep);
                            callback.onUserReceived(user);
                        }
                    } catch (JSONException e) {
                        ClientDTO user=new ClientDTO(null,null,null,null,null,null,null);

                        callback.onUserReceived(user);
                    }


                },
                error -> {
                    callback.onError(error);
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void checkEmailClient(ClientDTO userToFind, Context applicationContext, UserCallback callback){

        String email=userToFind.getEmail().toString();
        requestQueue= Volley.newRequestQueue(applicationContext);
        ClientDTO usr = userToFind;
        checkClientEmail(email, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                usr.setEmail(user.getEmail());
                usr.setAge(user.getAge());
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setPassword(user.getPassword());
                usr.setLicencepoints(user.getLicencepoints());
                usr.setDni(user.getDni());
                callback.onUserReceived(usr);

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

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void checkClientEmail(final String email,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/checkLoginClient.php?email="+email;

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
                        Integer licencep=Integer.parseInt(response.getString("licencepoints"));
                        ClientDTO user=new ClientDTO(null,null,name,surname,dateBirth, email1,licencep);
                        callback.onUserReceived(user);

                    } catch (JSONException e) {
                        ClientDTO user=new ClientDTO(null,null,null,null,null,null,null);
                        callback.onUserReceived(user);
                    }


                },
                error ->{
                    callback.onError(error);
                }

        );

        requestQueue.add(JsonObjectRequest);
    }

    public void addUser(ClientDTO userToFind, Context applicationContext, UserCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        ClientDTO usr = userToFind;
        addToDb(userToFind,applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                usr.setEmail(user.getEmail());
                usr.setAge(user.getAge());
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setPassword(user.getPassword());
                usr.setLicencepoints(user.getLicencepoints());
                usr.setDni(user.getDni());
                callback.onUserReceived(usr);

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

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void addToDb(final ClientDTO client,final Context applicationContext, final UserCallback callback) {
        String URL = "http://192.168.1.19/api/ucodgt/user/addClient.php";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(client.getAge());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    if(!response.isEmpty()){
                        callback.onUserReceived(client);
                    }else{
                        callback.onUserReceived(new ClientDTO(null,null,null,null,null,null,null));
                    }
                },
                error -> {
                    // Manejar el error de la solicitud
                    if(error.networkResponse.statusCode==500){
                        Log.e("Error", "Error en la solicitud", error);
                        Toast.makeText(applicationContext,"The DNI exists", Toast.LENGTH_LONG).show();
                        callback.onError(error);
                    }

                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", client.getName());
                params.put("surname", client.getSurname());
                params.put("dni", client.getDni());
                params.put("age", strDate);
                params.put("licencePoints", String.valueOf(client.getLicencepoints()));
                params.put("password", client.getPassword());
                params.put("email", client.getEmail());
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void getUser(ClientDTO userToFind, Context applicationContext, UserCallback callback){

        String dni=userToFind.getDni();
        requestQueue= Volley.newRequestQueue(applicationContext);
        ClientDTO usr = userToFind;
        getUserToFind(dni, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                usr.setEmail(user.getEmail());
                usr.setAge(user.getAge());
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setPassword(user.getPassword());
                usr.setLicencepoints(user.getLicencepoints());
                usr.setDni(user.getDni());
                callback.onUserReceived(usr);

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

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void getUserToFind(final String dni,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/getClient.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String name = jsonResponse.getString("name");
                        String surname = jsonResponse.getString("surname");
                        String email = jsonResponse.getString("email");
                        String dni1 = jsonResponse.getString("dni_client");
                        String age = jsonResponse.getString("age");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirth;
                        try {
                            dateBirth = format.parse(age);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Integer licencep = Integer.parseInt(jsonResponse.getString("licencepoints"));
                        ClientDTO user = new ClientDTO(dni1, null, name, surname, dateBirth, email, licencep);
                        callback.onUserReceived(user);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    callback.onError(error);
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dni", dni);
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void deleteUser(ClientDTO userToFind, Context applicationContext, UserCallback callback){

        String dni=userToFind.getDni();
        requestQueue= Volley.newRequestQueue(applicationContext);
        ClientDTO usr = userToFind;
        deleteUserFromBd(dni, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                usr.setEmail(user.getEmail());
                usr.setAge(user.getAge());
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setPassword(user.getPassword());
                usr.setLicencepoints(user.getLicencepoints());
                usr.setDni(user.getDni());
                callback.onUserReceived(usr);

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

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void deleteUserFromBd(final String dni,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/deleteClient.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String name = jsonResponse.getString("name");
                        String surname = jsonResponse.getString("surname");
                        String email = jsonResponse.getString("email");
                        String dni1 = jsonResponse.getString("dni_client");
                        String age = jsonResponse.getString("age");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirth;
                        try {
                            dateBirth = format.parse(age);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Integer licencep = Integer.parseInt(jsonResponse.getString("licencepoints"));
                        ClientDTO user = new ClientDTO(dni1, null, name, surname, dateBirth, email, licencep);
                        callback.onUserReceived(user);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    callback.onError(error);
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dni", dni);
                return params;
            }
        };
        requestQueue.add(request);
    }

}