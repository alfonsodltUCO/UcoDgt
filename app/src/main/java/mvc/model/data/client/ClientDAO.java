package mvc.model.data.client;

import android.annotation.SuppressLint;
import android.content.Context;
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
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.UserCallback;


/**
 * Data Access Object (DAO) class for client users. Handles operations related to client user data retrieval and verification.
 * @author Alfonso de la torre
 */
public class ClientDAO {

    RequestQueue requestQueue;

    /**
     * Checks the login credentials of a client user.
     *
     * @param userToFind The ClientDTO object containing the login credentials to be checked.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the login check.
     */
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

    /**
     * Checks if the provided login credentials belong to a client user.
     *
     * @param userToFind The ClientDTO object containing the login credentials to be checked.
     * @param callback The callback to handle the result of the login check.
     */    private void checkClient(final ClientDTO userToFind,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/user/checkLoginClient.php?email="+userToFind.getEmail();

        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {

                    try {
                        String name = response.getString("name");
                        String surname = response.getString("surname");
                        String email1 = response.getString("email");
                        String dni = response.getString("dni_client");
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
                            Integer licencep = Integer.parseInt(response.getString("licencepoints"));
                            ClientDTO user = new ClientDTO(dni, userToFind.getPassword(), name, surname, dateBirth, email1, licencep);
                            callback.onUserReceived(user);
                        }
                    } catch (JSONException e) {
                        ClientDTO user = new ClientDTO(null, null, null, null, null, null, null);
                        callback.onUserReceived(user);
                    }
                },
                callback::onError
        );

        requestQueue.add(JsonObjectRequest);
    }

    /**
     * Checks if the provided email belongs to a client user.
     *
     * @param userToFind The ClientDTO object containing the email to be checked.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the email check.
     */

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

    /**
     * Checks if the provided email belongs to a client user.
     *
     * @param userToFind The ClientDTO object containing the email to be checked.
     * @param callback The callback to handle the result of the email check.
     */    private void checkClientEmail(final ClientDTO userToFind,final UserCallback callback){
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
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        );

        requestQueue.add(JsonObjectRequest);
    }

    /**
     * Adds a new client user to the database.
     *
     * @param userToFind The ClientDTO object representing the user to be added.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the addition operation.
     */
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

    /**
     * Adds a new client user to the database.
     *
     * @param client The ClientDTO object representing the user to be added.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the addition operation.
     */
    private void addToDb(final ClientDTO client,final Context applicationContext, final UserCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/user/addClient.php";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
                    if(error.networkResponse.statusCode==500){
                        Toast.makeText(applicationContext,"The DNI exists", Toast.LENGTH_LONG).show();
                        callback.onError(error);
                    }

                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                String strDate2= formatter2.format(client.getDateLicenceObtaining());
                String strDate3= formatter2.format(client.getDateLastUpdate());
                Map<String, String> params = new HashMap<>();
                params.put("name", client.getName());
                params.put("surname", client.getSurname());
                params.put("dni", client.getDni());
                params.put("age", strDate);
                params.put("licencePoints", String.valueOf(client.getLicencepoints()));
                params.put("password", client.getPassword());
                params.put("email", client.getEmail());
                params.put("dateObtaining",strDate2);
                params.put("dateLastUpdate",strDate3);
                return params;
            }
        };

        requestQueue.add(request);
    }

    /**
     * Retrieves a client user from the database.
     *
     * @param userToFind The ClientDTO object containing the unique identifier (DNI) of the user to retrieve.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the retrieval operation.
     */

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
                userToFind.setDateLicenceObtaining(user.getDateLicenceObtaining());
                userToFind.setDateLastUpdate(user.getDateLastUpdate());
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

    /**
     * Retrieves a client user from the database.
     *
     * @param userToFind The ClientDTO object containing the unique identifier (DNI) of the user to retrieve.
     * @param callback The callback to handle the result of the retrieval operation.
     */
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
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirth;
                        Date obtaining;
                        Date last;
                        String obtFromJson = jsonResponse.getString("dateLicenceObtaining");

                        try {
                            dateBirth = format.parse(age);
                            obtaining=format.parse(obtFromJson);
                            last=format.parse(jsonResponse.getString("dateLastUpdate"));
                            Integer licencep = Integer.parseInt(jsonResponse.getString("licencepoints"));
                            ClientDTO user = new ClientDTO(dni1, null, name, surname, dateBirth, email, licencep);
                            user.setDateLicenceObtaining(obtaining);
                            user.setDateLastUpdate(last);
                            callback.onUserReceived(user);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                callback::onError
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

    /**
     * Deletes a client user from the database.
     *
     * @param userToFind The ClientDTO object containing the unique identifier (DNI) of the user to delete.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the deletion operation.
     */
    public void deleteUser(ClientDTO userToFind, Context applicationContext, UserCallback callback){

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

    /**
     * Deletes a client user from the database.
     *
     * @param userToFind The ClientDTO object containing the unique identifier (DNI) of the user to delete.
     * @param callback The callback to handle the result of the deletion operation.
     */
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
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
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

    /**
     * Retrieves all client users from the database.
     *
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the retrieval operation.
     */
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

    /**
     * Retrieves all client users from the database.
     *
     * @param callback The callback to handle the result of the retrieval operation.
     */
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
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                error -> callback.onClientsReceived(new ArrayList<ClientDTO>())
        );

        requestQueue.add(JsonObjectRequest);
    }

    /**
     * Updates the license points of a client user in the database.
     *
     * @param userToFind The ClientDTO object containing the unique identifier (DNI) of the user and the updated license points.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the update operation.
     */
    public void updatePoints(ClientDTO userToFind, Context applicationContext, UserCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        updatePointsBd(userToFind, new UserCallback() {
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

    /**
     * Updates the license points of a client user in the database.
     *
     * @param client The ClientDTO object containing the unique identifier (DNI) of the user and the updated license points.
     * @param callback The callback to handle the result of the update operation.
     */
    private void updatePointsBd(final ClientDTO client, final UserCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/user/updatePoints.php";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dni", client.getDni());
                params.put("licencePoints", String.valueOf(client.getLicencepoints()));
                params.put("dateLastUpdate",formatter.format(new Date()));
                return params;
            }
        };

        requestQueue.add(request);
    }

    /**
     * Retrieves a client user from the database by a vehicle.
     *
     * @param vehicle The Vehicle object that represent the owner we are trying to obtain.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the retrieval operation.
     */

    public void getOwner(VehicleDTO vehicle, Context applicationContext, UserCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        getOwner(vehicle, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

                callback.onUserReceived(user);

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

    /**
     * Retrieves a vehicle from DB by a given vehicle.
     *
     * @param vehicle The Vehicle object that represent the owner we are trying to obtain info.
     * @param callback The callback to handle the result of the retrieval operation.
     */
    private void getOwner(final VehicleDTO vehicle,final UserCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/vehicle/getClient.php";
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
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirth;
                        Date obtaining;
                        Date last;
                        String obtFromJson = jsonResponse.getString("dateLicenceObtaining");

                        try {
                            dateBirth = format.parse(age);
                            obtaining=format.parse(obtFromJson);
                            last=format.parse(jsonResponse.getString("dateLastUpdate"));
                            Integer licencep = Integer.parseInt(jsonResponse.getString("licencepoints"));
                            ClientDTO user = new ClientDTO(dni1, null, name, surname, dateBirth, email, licencep);
                            user.setDateLicenceObtaining(obtaining);
                            user.setDateLastUpdate(last);
                            callback.onUserReceived(user);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("plate", vehicle.getLicencePlate());
                return params;
            }
        };

        requestQueue.add(request);
    }


}