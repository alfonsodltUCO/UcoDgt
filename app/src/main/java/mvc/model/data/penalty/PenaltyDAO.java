package mvc.model.data.penalty;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.stateof;
import mvc.model.business.penalty.typeof;
import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.business.vehicle.typeofColor;
import mvc.model.data.PenaltyCallback;
import mvc.model.data.UserCallback;
import mvc.model.data.VehicleCallback;

public class PenaltyDAO {

    RequestQueue requestQueue;
    public void getPenalties(VehicleDTO vh,Context applicationContext, PenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getPenaltiesFromBd(vh,new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabl
    // a, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario

    private void getPenaltiesFromBd(final VehicleDTO vh, final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getAllPenaltiesFromCar.php";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    if (response != null && !response.isEmpty()) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray listOfPenalties = jsonResponse.getJSONArray("penalties");
                            List<PenaltyDTO> penaltiesToSend = new ArrayList<>();
                            for (int i = 0; i < listOfPenalties.length(); i++) {
                                JSONObject penaltyJson = listOfPenalties.getJSONObject(i);
                                PenaltyDTO penalty = new PenaltyDTO();
                                penalty.setId(Integer.valueOf(penaltyJson.getString("id")));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dt1;
                                try {
                                    dt1 = format.parse(penaltyJson.getString("date"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                penalty.setDate(dt1);
                                penalty.setDniClient(penaltyJson.getString("dni_client"));
                                penalty.setDniWorker(penaltyJson.getString("dni_worker"));
                                penalty.setState(stateof.valueOf(penaltyJson.getString("state")));
                                penalty.setReason(typeof.valueOf(penaltyJson.getString("reason")));
                                penalty.setDescription(penaltyJson.getString("description"));
                                penalty.setPlace(penaltyJson.getString("place"));
                                penalty.setInformedAtTheMoment(Boolean.parseBoolean(penaltyJson.getString("informedAtTheMoment")));
                                penalty.setLocality(penaltyJson.getString("locality"));
                                penalty.setLicenceplate(penaltyJson.getString("licenceplate"));
                                penalty.setQuantity(Float.valueOf(penaltyJson.getString("quantity")));
                                penalty.setPoints(Integer.valueOf(penaltyJson.getString("points")));
                                Log.d("a",penalty.toString());
                                penaltiesToSend.add(penalty);
                            }
                            callback.onPenaltiesReceived(penaltiesToSend);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("licenceplate", vh.getLicencePlate()); // Agregar la matrícula del vehículo como parámetro POST
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getPenalty(PenaltyDTO penaltyToFind, Context applicationContext, PenaltyCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        getPenaltyToFind(penaltyToFind, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                callback.onPenaltyReceived(penalty);
            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void getPenaltyToFind(final PenaltyDTO penaltyToFInd,final PenaltyCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/penalty/getPenalty.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        PenaltyDTO penalty = new PenaltyDTO();
                        penalty.setId(Integer.valueOf(jsonResponse.getString("id")));

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dt1;
                        try {
                            dt1 = format.parse(jsonResponse.getString("date"));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        penalty.setDate(dt1);

                        penalty.setDniClient(jsonResponse.getString("dni_client"));
                        penalty.setDniWorker(jsonResponse.getString("dni_worker"));
                        penalty.setState(stateof.valueOf(jsonResponse.getString("state")));
                        penalty.setReason(typeof.valueOf(jsonResponse.getString("reason")));
                        penalty.setDescription(jsonResponse.getString("description"));
                        penalty.setPlace(jsonResponse.getString("place"));
                        penalty.setInformedAtTheMoment(Boolean.parseBoolean(jsonResponse.getString("informedAtTheMoment")));
                        penalty.setLocality(jsonResponse.getString("locality"));
                        penalty.setLicenceplate(jsonResponse.getString("licenceplate"));
                        penalty.setQuantity(Float.valueOf(jsonResponse.getString("quantity")));
                        penalty.setPoints(Integer.valueOf(jsonResponse.getString("points")));
                        callback.onPenaltyReceived(penalty);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    callback.onError(error);

                }
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id", penaltyToFInd.getId().toString());
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void getPenalties(ClientDTO client,Context applicationContext, PenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getPenaltiesFromBd(client,new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabl
    // a, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario

    private void getPenaltiesFromBd(final ClientDTO cl,final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getAllPenaltiesFromUser.php";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    if (response != null && !response.isEmpty()) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray listOfPenalties = jsonResponse.getJSONArray("penalties");
                            List<PenaltyDTO> penaltiesToSend = new ArrayList<>();
                            for (int i = 0; i < listOfPenalties.length(); i++) {
                                JSONObject penaltyJson = listOfPenalties.getJSONObject(i);
                                PenaltyDTO penalty = new PenaltyDTO();
                                penalty.setId(Integer.valueOf(penaltyJson.getString("id")));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dt1;
                                try {
                                    dt1 = format.parse(penaltyJson.getString("date"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                penalty.setDate(dt1);
                                penalty.setDniClient(penaltyJson.getString("dni_client"));
                                penalty.setDniWorker(penaltyJson.getString("dni_worker"));
                                penalty.setState(stateof.valueOf(penaltyJson.getString("state")));
                                penalty.setReason(typeof.valueOf(penaltyJson.getString("reason")));
                                penalty.setDescription(penaltyJson.getString("description"));
                                penalty.setPlace(penaltyJson.getString("place"));
                                penalty.setInformedAtTheMoment(Boolean.parseBoolean(penaltyJson.getString("informedAtTheMoment")));
                                penalty.setLocality(penaltyJson.getString("locality"));
                                penalty.setLicenceplate(penaltyJson.getString("licenceplate"));
                                penalty.setQuantity(Float.valueOf(penaltyJson.getString("quantity")));
                                penalty.setPoints(Integer.valueOf(penaltyJson.getString("points")));
                                penaltiesToSend.add(penalty);
                            }
                            callback.onPenaltiesReceived(penaltiesToSend);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dni", cl.getDni().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void getPenalties(Context applicationContext, PenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getPenaltiesFromBd(new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabl
    // a, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario

    private void getPenaltiesFromBd(final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getAllPenalties.php";
        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    if (response.length()>0) {

                        try {
                            JSONArray listOfPenalties = response.getJSONArray("penalties");
                            List<PenaltyDTO> penaltiesToSend = new ArrayList<>();
                            for (int i = 0; i < listOfPenalties.length(); i++) {
                                JSONObject penaltyJson = listOfPenalties.getJSONObject(i);
                                PenaltyDTO penalty = new PenaltyDTO();
                                penalty.setId(Integer.valueOf(penaltyJson.getString("id")));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dt1;
                                try {
                                    dt1 = format.parse(penaltyJson.getString("date"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                penalty.setDate(dt1);
                                penalty.setDniClient(penaltyJson.getString("dni_client"));
                                penalty.setDniWorker(penaltyJson.getString("dni_worker"));
                                penalty.setState(stateof.valueOf(penaltyJson.getString("state")));
                                penalty.setReason(typeof.valueOf(penaltyJson.getString("reason")));
                                penalty.setDescription(penaltyJson.getString("description"));
                                penalty.setPlace(penaltyJson.getString("place"));
                                penalty.setInformedAtTheMoment(Boolean.parseBoolean(penaltyJson.getString("informedAtTheMoment")));
                                penalty.setLocality(penaltyJson.getString("locality"));
                                penalty.setLicenceplate(penaltyJson.getString("licenceplate"));
                                penalty.setQuantity(Float.valueOf(penaltyJson.getString("quantity")));
                                penalty.setPoints(Integer.valueOf(penaltyJson.getString("points")));
                                penaltiesToSend.add(penalty);
                            }
                            callback.onPenaltiesReceived(penaltiesToSend);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> {
                    callback.onError(error);
                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void deletePenalty(PenaltyDTO penaltyToFind, Context applicationContext, PenaltyCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        deletePenaltyFromBd(penaltyToFind, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                callback.onPenaltyReceived(penalty);
            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void deletePenaltyFromBd(final PenaltyDTO penaltyToDelete,final PenaltyCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/penalty/deletePenalty.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        PenaltyDTO penalty = new PenaltyDTO();
                        penalty.setId(Integer.valueOf(jsonResponse.getString("id")));

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dt1;
                        try {
                            dt1 = format.parse(jsonResponse.getString("date"));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        penalty.setDate(dt1);

                        penalty.setDniClient(jsonResponse.getString("dni_client"));
                        penalty.setDniWorker(jsonResponse.getString("dni_worker"));
                        penalty.setState(stateof.valueOf(jsonResponse.getString("state")));
                        penalty.setReason(typeof.valueOf(jsonResponse.getString("reason")));
                        penalty.setDescription(jsonResponse.getString("description"));
                        penalty.setPlace(jsonResponse.getString("place"));
                        penalty.setInformedAtTheMoment(Boolean.parseBoolean(jsonResponse.getString("informedAtTheMoment")));
                        penalty.setLocality(jsonResponse.getString("locality"));
                        penalty.setLicenceplate(jsonResponse.getString("licenceplate"));
                        penalty.setQuantity(Float.valueOf(jsonResponse.getString("quantity")));
                        penalty.setPoints(Integer.valueOf(jsonResponse.getString("points")));
                        callback.onPenaltyReceived(penalty);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("id", penaltyToDelete.getId().toString());
                return params;
            }
        };
        requestQueue.add(request);
    }
    public void getPenalties(String date1,String date2,Context applicationContext, PenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getPenaltiesFromBd(date1,date2,new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabl
    // a, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario

    private void getPenaltiesFromBd(final String date1, final String date2,final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getPenaltiesByDates.php?start="+date1+"&end="+date2;
        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    if (response.length()>0) {

                        try {
                            JSONArray listOfPenalties = response.getJSONArray("penalties");
                            List<PenaltyDTO> penaltiesToSend = new ArrayList<>();
                            for (int i = 0; i < listOfPenalties.length(); i++) {
                                JSONObject penaltyJson = listOfPenalties.getJSONObject(i);
                                PenaltyDTO penalty = new PenaltyDTO();
                                penalty.setId(Integer.valueOf(penaltyJson.getString("id")));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dt1;
                                try {
                                    dt1 = format.parse(penaltyJson.getString("date"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                penalty.setDate(dt1);
                                penalty.setDniClient(penaltyJson.getString("dni_client"));
                                penalty.setDniWorker(penaltyJson.getString("dni_worker"));
                                penalty.setState(stateof.valueOf(penaltyJson.getString("state")));
                                penalty.setReason(typeof.valueOf(penaltyJson.getString("reason")));
                                penalty.setDescription(penaltyJson.getString("description"));
                                penalty.setPlace(penaltyJson.getString("place"));
                                penalty.setInformedAtTheMoment(Boolean.parseBoolean(penaltyJson.getString("informedAtTheMoment")));
                                penalty.setLocality(penaltyJson.getString("locality"));
                                penalty.setLicenceplate(penaltyJson.getString("licenceplate"));
                                penalty.setQuantity(Float.valueOf(penaltyJson.getString("quantity")));
                                penalty.setPoints(Integer.valueOf(penaltyJson.getString("points")));
                                penaltiesToSend.add(penalty);
                            }
                            callback.onPenaltiesReceived(penaltiesToSend);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> {
                    callback.onError(error);
                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void getPenalties(String state,Context applicationContext, PenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getPenaltiesFromBd(state,new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabl
    // a, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario

    private void getPenaltiesFromBd(final String state,final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getPenaltiesByState.php?state="+state;
        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    if (response.length()>0) {

                        try {
                            JSONArray listOfPenalties = response.getJSONArray("penalties");
                            List<PenaltyDTO> penaltiesToSend = new ArrayList<>();
                            for (int i = 0; i < listOfPenalties.length(); i++) {
                                JSONObject penaltyJson = listOfPenalties.getJSONObject(i);
                                PenaltyDTO penalty = new PenaltyDTO();
                                penalty.setId(Integer.valueOf(penaltyJson.getString("id")));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dt1;
                                try {
                                    dt1 = format.parse(penaltyJson.getString("date"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                penalty.setDate(dt1);
                                penalty.setDniClient(penaltyJson.getString("dni_client"));
                                penalty.setDniWorker(penaltyJson.getString("dni_worker"));
                                penalty.setState(stateof.valueOf(penaltyJson.getString("state")));
                                penalty.setReason(typeof.valueOf(penaltyJson.getString("reason")));
                                penalty.setDescription(penaltyJson.getString("description"));
                                penalty.setPlace(penaltyJson.getString("place"));
                                penalty.setInformedAtTheMoment(Boolean.parseBoolean(penaltyJson.getString("informedAtTheMoment")));
                                penalty.setLocality(penaltyJson.getString("locality"));
                                penalty.setLicenceplate(penaltyJson.getString("licenceplate"));
                                penalty.setQuantity(Float.valueOf(penaltyJson.getString("quantity")));
                                penalty.setPoints(Integer.valueOf(penaltyJson.getString("points")));
                                penaltiesToSend.add(penalty);
                            }
                            callback.onPenaltiesReceived(penaltiesToSend);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                error -> {
                    callback.onError(error);
                }
        );

        requestQueue.add(JsonObjectRequest);
    }

    public void addPenalty(PenaltyDTO penaltyToFind, Context applicationContext, PenaltyCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        addToDb(penaltyToFind,applicationContext, new PenaltyCallback() {


            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                callback.onPenaltyReceived(penalty);
            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void addToDb(final PenaltyDTO penalty,final Context applicationContext, final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/addPenalty.php";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(penalty.getDate());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    if(!response.isEmpty()){
                        callback.onPenaltyReceived(penalty);
                    }else{
                        callback.onPenaltyReceived(new PenaltyDTO());
                    }
                },
                error -> {
                    callback.onError(error);

                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                String val;
                if(penalty.isInformedAtTheMoment()){
                    val="1";
                }else{
                    val="0";
                }
                Map<String, String> params = new HashMap<>();
                params.put("date", strDate);
                params.put("description", penalty.getDescription());
                params.put("reason", penalty.getReason().toString());
                params.put("state", penalty.getState().toString());
                params.put("dniC", penalty.getDniClient());
                params.put("dniW", penalty.getDniWorker());
                params.put("place", penalty.getPlace());
                params.put("informed", val);
                params.put("locality", penalty.getLocality());
                params.put("licenceplate", penalty.getLicenceplate());
                params.put("quantity", penalty.getQuantity().toString());
                params.put("points", penalty.getPoints().toString());
                return params;
            }
        };

        requestQueue.add(request);
    }


}
