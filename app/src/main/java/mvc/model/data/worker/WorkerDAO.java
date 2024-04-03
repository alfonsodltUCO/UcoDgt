package mvc.model.data.worker;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;

public class WorkerDAO {

    RequestQueue requestQueue;


    public void checkLogInWorker(WorkerDTO userToFind, Context applicationContext, UserCallback callback){
        String email=userToFind.getEmail();
        String password= userToFind.getPassword();
        requestQueue= Volley.newRequestQueue(applicationContext);
        checkWorker(email,password, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {



            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setDni(user.getDni());
                userToFind.setNumberOfWorker(user.getNumberOfWorker());
                callback.onWorkerReceived(userToFind);
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
    private void checkWorker(final String email,final String password,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/checkLoginWorker.php?email="+email;

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
                            String number=response.getString("numberOfWorker");
                            WorkerDTO user=new WorkerDTO(null,password,name,surname,dateBirth, email1,Integer.parseInt(number));
                            callback.onWorkerReceived(user);
                        }
                    } catch (JSONException e) {
                        WorkerDTO user=new WorkerDTO(null,null,null,null,null,null,null);

                        callback.onWorkerReceived(user);
                    }


                },
                error -> {
                    callback.onError(error);
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void checkWorkerEmail(WorkerDTO userToFind, Context applicationContext, UserCallback callback){
        String email=userToFind.getEmail();
        requestQueue= Volley.newRequestQueue(applicationContext);
        checkEmailWorker(email, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {}

            @Override
            public void onError(VolleyError error) {callback.onError(error);}

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setDni(user.getDni());
                userToFind.setNumberOfWorker(user.getNumberOfWorker());
                callback.onWorkerReceived(userToFind);
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
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void checkEmailWorker(final String email,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/checkLoginWorker.php?email="+email;

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
                        String number=response.getString("numberOfWorker");
                        WorkerDTO user=new WorkerDTO(null,null,name,surname,dateBirth, email1,Integer.parseInt(number));
                        callback.onWorkerReceived(user);

                    } catch (JSONException e) {
                        WorkerDTO user=new WorkerDTO(null,null,null,null,null,null,null);

                        callback.onWorkerReceived(user);
                    }
                },
                error -> {
                    callback.onError(error);
                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void addUser(WorkerDTO userToFind, Context applicationContext, UserCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        addToDb(userToFind, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setNumberOfWorker(user.getNumberOfWorker());
                userToFind.setDni(user.getDni());
                callback.onWorkerReceived(userToFind);

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
    private void addToDb(final WorkerDTO worker, final UserCallback callback) {
        String URL = "http://192.168.1.19/api/ucodgt/user/addWorker.php";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(worker.getAge());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        if (!response.isEmpty()) {
                            callback.onWorkerReceived(worker);
                        } else {
                            callback.onWorkerReceived(new WorkerDTO(null, null, null, null, null, null, null));
                        }
                    } catch (Exception e) {
                        callback.onWorkerReceived(new WorkerDTO(null, null, null, null, null, null, null));
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                   if(error.networkResponse.statusCode==500){
                       callback.onError(error);
                   }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", worker.getName());
                params.put("surname", worker.getSurname());
                params.put("dni", worker.getDni());
                params.put("age", strDate);
                params.put("password", worker.getPassword());
                params.put("email", worker.getEmail());
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes
        requestQueue.add(request);
    }

    public void getUser(WorkerDTO userToFind, Context applicationContext, UserCallback callback){

        String dni=userToFind.getDni();
        requestQueue= Volley.newRequestQueue(applicationContext);
        getUserToFind(dni, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setNumberOfWorker(user.getNumberOfWorker());
                userToFind.setDni(user.getDni());
                callback.onWorkerReceived(userToFind);
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
    private void getUserToFind(final String dni,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/getWorker.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String name=jsonObject.getString("name");
                        String surname=jsonObject.getString("surname");
                        String email=jsonObject.getString("email");
                        String dni1 =jsonObject.getString("dni_worker");
                        String age=jsonObject.getString("age");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirth;
                        try {
                            dateBirth = format.parse(age);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Integer nworker=Integer.parseInt(jsonObject.getString("numberOfWorker"));
                        WorkerDTO user=new WorkerDTO(dni1,null,name,surname,dateBirth,email,nworker);

                        callback.onWorkerReceived(user);
                    } catch (JSONException e) {
                        WorkerDTO user=new WorkerDTO(null,null,null,null,null,null,null);
                        callback.onWorkerReceived(user);
                    }
                },
                error -> {
                    callback.onError(error);
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dni", dni);
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void deleteUser(WorkerDTO userToFind, Context applicationContext, UserCallback callback){

        String dni=userToFind.getDni();
        requestQueue= Volley.newRequestQueue(applicationContext);
        deleteUserFromBd(dni, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                userToFind.setEmail(user.getEmail());
                userToFind.setAge(user.getAge());
                userToFind.setName(user.getName());
                userToFind.setSurname(user.getSurname());
                userToFind.setPassword(user.getPassword());
                userToFind.setNumberOfWorker(user.getNumberOfWorker());
                userToFind.setDni(user.getDni());
                callback.onWorkerReceived(userToFind);
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
    private void deleteUserFromBd(final String dni,final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/deleteWorker.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String name=jsonObject.getString("name");
                        String surname=jsonObject.getString("surname");
                        String email=jsonObject.getString("email");
                        String dni1 =jsonObject.getString("dni_client");
                        String age=jsonObject.getString("age");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateBirth;
                        try {
                            dateBirth = format.parse(age);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Integer nworker=Integer.parseInt(jsonObject.getString("numberOfWorker"));
                        WorkerDTO user=new WorkerDTO(dni1,null,name,surname,dateBirth,email,nworker);
                        callback.onWorkerReceived(user);
                    } catch (JSONException e) {
                        WorkerDTO user=new WorkerDTO(null,null,null,null,null,null,null);
                        callback.onWorkerReceived(user);
                    }
                },
                error -> {
                    callback.onError(error);
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dni", dni);
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
                callback.onWorkersReceived(workers);
            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void getUsersFromBd(final UserCallback callback){
        String URL="http://192.168.1.19/api/ucodgt/user/getAllWorkers.php";

        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {
                    if(response.length()>0){
                        try {
                            JSONArray listofworkers=response.getJSONArray("workers");
                            List<WorkerDTO> workersToSend=new ArrayList<WorkerDTO>();
                            WorkerDTO worker=new WorkerDTO();
                            for(int i=0;i<response.length();i++){
                                JSONObject workerJson=listofworkers.getJSONObject(i);
                                worker.setEmail(workerJson.getString("email"));
                                worker.setName(workerJson.getString("name"));
                                worker.setSurname(workerJson.getString("surname"));
                                worker.setDni(workerJson.getString("dni_worker"));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateBirth;
                                try {
                                    dateBirth = format.parse(workerJson.getString("age"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                worker.setAge(dateBirth);
                                worker.setNumberOfWorker(workerJson.getInt("numberOfWorker"));
                                workersToSend.add(worker);
                            }
                            callback.onWorkersReceived(workersToSend);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }else{
                        callback.onWorkersReceived(new ArrayList<WorkerDTO>());
                    }
                },
                error -> {
                    callback.onWorkersReceived(new ArrayList<WorkerDTO>());
                    Log.d("ADebugTag", "Value: " +error.toString());

                }
        );

        requestQueue.add(JsonObjectRequest);
    }

}