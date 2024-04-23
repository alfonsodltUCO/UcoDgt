package mvc.model.data.client;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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


        requestQueue= Volley.newRequestQueue(applicationContext);
        checkClient(userToFind, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setLicencepoints(user.getLicencepoints());
                userToFind.setDni(user.getDni());
                callback.onUserReceived(userToFind);

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

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void checkClient(final ClientDTO userToFind,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/checkLoginClient.php?email="+userToFind.getEmail();

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
                        String dni=response.getString("dni_client");
                        String passwordhashed=response.getString("password");
                        if(!BCrypt.checkpw(userToFind.getPassword(),passwordhashed)){
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
                            ClientDTO user=new ClientDTO(dni, userToFind.getPassword(), name,surname,dateBirth, email1,licencep);
                            callback.onUserReceived(user);
                        }
                    } catch (JSONException e) {
                        ClientDTO user=new ClientDTO(null,null,null,null,null,null,null);
                        callback.onUserReceived(user);
                    }
                },
                error -> {
                    callback.onError(error);

                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void checkEmailClient(ClientDTO userToFind, Context applicationContext, UserCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        checkClientEmail(userToFind, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setLicencepoints(user.getLicencepoints());
                userToFind.setDni(user.getDni());
                callback.onUserReceived(userToFind);

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

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void checkClientEmail(final ClientDTO userToFind,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/checkLoginClient.php?email="+userToFind.getEmail();

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
        addToDb(userToFind,applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setLicencepoints(user.getLicencepoints());
                userToFind.setDni(user.getDni());
                callback.onUserReceived(userToFind);

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

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void addToDb(final ClientDTO client,final Context applicationContext, final UserCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/user/addClient.php";
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
            protected Map<String, String> getParams() {
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

        requestQueue= Volley.newRequestQueue(applicationContext);
        getUserToFind(userToFind, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setLicencepoints(user.getLicencepoints());
                userToFind.setDni(user.getDni());
                callback.onUserReceived(userToFind);

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

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void getUserToFind(final ClientDTO userToFind,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/getClient.php";
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
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("dni", userToFind.getDni());
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void deleteUser(ClientDTO userToFind, Context applicationContext, UserCallback callback){

        String dni=userToFind.getDni();
        requestQueue= Volley.newRequestQueue(applicationContext);
        deleteUserFromBd(userToFind, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setLicencepoints(user.getLicencepoints());
                userToFind.setDni(user.getDni());
                callback.onUserReceived(userToFind);

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

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void deleteUserFromBd(final ClientDTO userToFind,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/deleteClient.php";
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
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("dni", userToFind.getDni());
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void getUsers(Context applicationContext, UserCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getUsersFromBd(new UserCallback() {
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

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {
            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {
                callback.onClientsReceived(clients);
            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void getUsersFromBd(final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/getAllClients.php";

        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {
                    if(response.length()>0){
                        try {
                            JSONArray listofclients=response.getJSONArray("clients");
                            List<ClientDTO> clientsToSend=new ArrayList<ClientDTO>();
                            for(int i=0;i<listofclients.length();i++){
                                ClientDTO client=new ClientDTO();
                                JSONObject clientJson=listofclients.getJSONObject(i);

                                client.setEmail(clientJson.getString("email"));
                                client.setName(clientJson.getString("name"));
                                client.setSurname(clientJson.getString("surname"));
                                client.setDni(clientJson.getString("dni_client"));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateBirth;
                                try {
                                    dateBirth = format.parse(clientJson.getString("age"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                client.setAge(dateBirth);
                                client.setLicencepoints(clientJson.getInt("licencepoints"));
                                clientsToSend.add(client);
                            }
                            callback.onClientsReceived(clientsToSend);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }else{
                        callback.onClientsReceived(new ArrayList<ClientDTO>());
                    }
                },
                error -> {
                    callback.onClientsReceived(new ArrayList<ClientDTO>());
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        );

        requestQueue.add(JsonObjectRequest);
    }


}