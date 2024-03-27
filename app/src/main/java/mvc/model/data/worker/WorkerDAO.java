package mvc.model.data.worker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mvc.controller.CheckUserToAdd;
import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;

public class WorkerDAO {

    RequestQueue requestQueue;


    public void checkLogInWorker(WorkerDTO userToFind, Context applicationContext, UserCallback callback){
        String email=userToFind.getEmail().toString();
        String password= userToFind.getPassword().toString();
        requestQueue= Volley.newRequestQueue(applicationContext);
        WorkerDTO usr = userToFind;
        checkWorker(email,password, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {



            }

            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                usr.setEmail(user.getEmail());
                usr.setAge(user.getAge());
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setPassword(user.getPassword());
                usr.setDni(user.getDni());
                usr.setNumberOfWorker(user.getNumberOfWorker());
                callback.onWorkerReceived(usr);
            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void checkWorker(final String email,final String password,final UserCallback callback){
        String URL="http://10.0.2.2/api/ucodgt/user/checkLoginWorker.php?email="+email;

        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String jsonEmpty= "{}";
                            JSONObject jsonEmptyObject = new JSONObject(jsonEmpty);

                            if("{}" != jsonEmptyObject.toString()){
                                String name=response.getString("name");
                                String surname=response.getString("surname");
                                String email=response.getString("email");
                                String passwordhashed=response.getString("password");
                                if(!BCrypt.checkpw(password,passwordhashed)){

                                    WorkerDTO user=new WorkerDTO(null,null,null,null,null,null,null);

                                    callback.onWorkerReceived(user);
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
                                    WorkerDTO user=new WorkerDTO(null,password,name,surname,dateBirth,email,Integer.parseInt(number));
                                    callback.onWorkerReceived(user);
                                }

                            }
                        } catch (JSONException e) {
                            WorkerDTO user=new WorkerDTO(null,null,null,null,null,null,null);

                            callback.onWorkerReceived(user);
                        }


                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("ADebugTag", "Value: " +error.toString());

                    }
                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void checkWorkerEmail(WorkerDTO userToFind, Context applicationContext, UserCallback callback){
        String email=userToFind.getEmail().toString();
        requestQueue= Volley.newRequestQueue(applicationContext);
        WorkerDTO usr = userToFind;
        checkEmailWorker(email, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {}

            @Override
            public void onError(VolleyError error) {}

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                usr.setEmail(user.getEmail());
                usr.setAge(user.getAge());
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setPassword(user.getPassword());
                usr.setDni(user.getDni());
                usr.setNumberOfWorker(user.getNumberOfWorker());
                callback.onWorkerReceived(usr);
            }
            @Override
            public void onAdminReceived(AdminDTO user) {}
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void checkEmailWorker(final String email,final UserCallback callback){
        String URL="http://10.0.2.2/api/ucodgt/user/checkLoginWorker.php?email="+email;

        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String jsonEmpty= "{}";
                            JSONObject jsonEmptyObject = new JSONObject(jsonEmpty);

                            if("{}" != jsonEmptyObject.toString()){
                                String name=response.getString("name");
                                String surname=response.getString("surname");
                                String email=response.getString("email");
                                String age=response.getString("age");
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateBirth;
                                try {
                                    dateBirth = format.parse(age);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                String number=response.getString("numberOfWorker");
                                WorkerDTO user=new WorkerDTO(null,null,name,surname,dateBirth,email,Integer.parseInt(number));
                                callback.onWorkerReceived(user);
                            }
                        } catch (JSONException e) {
                            WorkerDTO user=new WorkerDTO(null,null,null,null,null,null,null);

                            callback.onWorkerReceived(user);
                        }


                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("ADebugTag", "Value: " +error.toString());

                    }
                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void addUser(WorkerDTO userToFind, Context applicationContext, UserCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        WorkerDTO usr = userToFind;
        addToDb(userToFind,applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                usr.setEmail(user.getEmail());
                usr.setAge(user.getAge());
                usr.setName(user.getName());
                usr.setSurname(user.getSurname());
                usr.setPassword(user.getPassword());
                usr.setNumberOfWorker(user.getNumberOfWorker());
                usr.setDni(user.getDni());
                callback.onWorkerReceived(usr);

            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void addToDb(final WorkerDTO worker,final Context applicationContext, final UserCallback callback) {
        String URL = "http://192.168.1.19/api/ucodgt/user/addWorker.php";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(worker.getAge());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        Log.e("Error", "Error en la solicitud", error);
                        Toast.makeText(applicationContext,"The DNI exists", Toast.LENGTH_LONG).show();
                        callback.onWorkerReceived(new WorkerDTO(null, null, null, null, null, null, null));

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
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



}