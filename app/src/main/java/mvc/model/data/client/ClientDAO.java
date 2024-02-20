package mvc.model.data.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

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
        String URL="http://10.0.2.2/api/ucodgt/user/checkLoginClient.php?email="+email+"&password="+password;

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

                            if(!"{}".equals(jsonEmptyObject.toString())){
                                String name=response.getString("name");
                                String surname=response.getString("surname");
                                String email=response.getString("email");
                                String password=response.getString("password");
                                String age=response.getString("age");
                                String licencepoints=response.getString("licencepoints");
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateBirth;
                                try {
                                    dateBirth = format.parse(age);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                Integer licencep=Integer.parseInt(licencepoints);
                                ClientDTO user=new ClientDTO(null,password,name,surname,dateBirth,email,licencep);
                                callback.onUserReceived(user);

                            }
                        } catch (JSONException e) {
                            ClientDTO user=new ClientDTO(null,null,null,null,null,null,null);

                            callback.onUserReceived(user);
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
}
