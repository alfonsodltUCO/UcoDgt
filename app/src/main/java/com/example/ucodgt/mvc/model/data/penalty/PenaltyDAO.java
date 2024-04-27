package com.example.ucodgt.mvc.model.data.penalty;

import android.annotation.SuppressLint;
import android.content.Context;


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

import com.example.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.example.ucodgt.mvc.model.business.penalty.stateof;
import com.example.ucodgt.mvc.model.business.penalty.typeof;
import com.example.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.example.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.example.ucodgt.mvc.model.data.PenaltyCallback;

/**
 * Provides methods to interact with penalty data.
 * @author Alfonso de la torre
 */
public class PenaltyDAO {

    RequestQueue requestQueue;

    /**
     * Retrieves penalties associated with a vehicle.
     *
     * @param vh                 The VehicleDTO object representing the vehicle.
     * @param applicationContext The application context.
     * @param callback           The callback to handle the response.
     */
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

    /**
     * Retrieves penalties associated with a specific vehicle from the database.
     *
     * @param vh The VehicleDTO object representing the vehicle.
     * @param callback The callback to handle the penalties received or errors encountered.
     */
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
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("licenceplate", vh.getLicencePlate());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * Retrieves a specific penalty from the database.
     *
     * @param penaltyToFind The PenaltyDTO object representing the penalty to find.
     * @param applicationContext The application context.
     * @param callback The callback to handle the penalty received or errors encountered.
     */

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

    /**
     * Retrieves penalty information from the database.
     *
     * @param penaltyToFInd The PenaltyDTO object representing the penalty to find.
     * @param callback The callback to handle the received penalty or errors encountered.
     */
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

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id", penaltyToFInd.getId().toString());
                if(penaltyToFInd.getDniClient()==null){
                    params.put("dni","");
                }else{
                    params.put("dni",penaltyToFInd.getDniClient());

                }
                return params;
            }
        };

        requestQueue.add(request);
    }
    /**
     * Retrieves penalties associated with a specific client from the database.
     *
     * @param client The ClientDTO object representing the client whose penalties are to be retrieved.
     * @param applicationContext The application context.
     * @param callback The callback to handle the received penalties or errors encountered.
     */
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

    /**
     * Retrieves penalties associated with a specific client from the database.
     *
     * @param cl The ClientDTO object representing the client whose penalties are to be retrieved.
     * @param callback The callback to handle the received penalties or errors encountered.
     */
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
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dni", cl.getDni());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * Retrieves all penalties from the database.
     *
     * @param applicationContext The application context.
     * @param callback The callback to handle the received penalties or errors encountered.
     */
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

    /**
     * Retrieves all penalties from the database.
     *
     * @param callback The callback to handle the received penalties or errors encountered.
     */
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
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        );

        requestQueue.add(JsonObjectRequest);
    }

    /**
     * Deletes a penalty from the database.
     *
     * @param penaltyToFind The PenaltyDTO object representing the penalty to delete.
     * @param applicationContext The application context.
     * @param callback The callback to handle the result of deleting the penalty or errors encountered.
     */

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

    /**
     * Deletes a penalty from the database.
     *
     * @param penaltyToDelete The PenaltyDTO object representing the penalty to delete.
     * @param callback The callback to handle the result of deleting the penalty or errors encountered.
     */
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

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
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

    /**
     * Retrieves penalties within a specific date range from the database.
     *
     * @param date1 The start date of the date range.
     * @param date2 The end date of the date range.
     * @param applicationContext The application context.
     * @param callback The callback to handle the penalties received or errors encountered.
     */
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

    /**
     * Retrieves penalties within a specific date range from the database.
     *
     * @param date1 The start date of the date range.
     * @param date2 The end date of the date range.
     * @param callback The callback to handle the penalties received or errors encountered.
     */
    private void getPenaltiesFromBd(final String date1, final String date2,final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getPenaltiesByDates.php?start="+date1+"&end="+date2;
        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    if (response.length() > 0) {

                        try {
                            JSONArray listOfPenalties = response.getJSONArray("penalties");
                            List<PenaltyDTO> penaltiesToSend = new ArrayList<>();
                            for (int i = 0; i < listOfPenalties.length(); i++) {
                                JSONObject penaltyJson = listOfPenalties.getJSONObject(i);
                                PenaltyDTO penalty = new PenaltyDTO();
                                penalty.setId(Integer.valueOf(penaltyJson.getString("id")));
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        );

        requestQueue.add(JsonObjectRequest);
    }

    /**
     * Retrieves penalties associated with a specific state from the database.
     *
     * @param state The state of the penalties to retrieve.
     * @param applicationContext The application context.
     * @param callback The callback to handle the penalties received or errors encountered.
     */
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

    /**
     * Retrieves penalties associated with a specific state from the database.
     *
     * @param state The state of the penalties to retrieve.
     * @param callback The callback to handle the penalties received or errors encountered.
     */
    private void getPenaltiesFromBd(final String state,final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getPenaltiesByState.php?state="+state;
        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    if (response.length() > 0) {

                        try {
                            JSONArray listOfPenalties = response.getJSONArray("penalties");
                            List<PenaltyDTO> penaltiesToSend = new ArrayList<>();
                            for (int i = 0; i < listOfPenalties.length(); i++) {
                                JSONObject penaltyJson = listOfPenalties.getJSONObject(i);
                                PenaltyDTO penalty = new PenaltyDTO();
                                penalty.setId(Integer.valueOf(penaltyJson.getString("id")));
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        );

        requestQueue.add(JsonObjectRequest);
    }

    /**
     * Adds a new penalty to the database.
     *
     * @param penaltyToFind The PenaltyDTO object representing the penalty to be added.
     * @param applicationContext The application context.
     * @param callback The callback to handle the result of adding the penalty or errors encountered.
     */
    public void addPenalty(PenaltyDTO penaltyToFind, Context applicationContext, PenaltyCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        addToDb(penaltyToFind, new PenaltyCallback() {


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

    /**
     * Adds a new penalty to the database.
     *
     * @param penalty The PenaltyDTO object representing the penalty to be added.
     * @param callback The callback to handle the result of adding the penalty or errors encountered.
     */
    private void addToDb(final PenaltyDTO penalty, final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/addPenalty.php";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
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

    /**
     * Initiates a payment for a specific penalty.
     *
     * @param penaltyToFind The PenaltyDTO object representing the penalty for which payment is to be initiated.
     * @param applicationContext The application context.
     * @param callback The callback to handle the payment initiation result or errors encountered.
     */
    public void doPayment(PenaltyDTO penaltyToFind, Context applicationContext, PenaltyCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        doPaymentToBd(penaltyToFind, new PenaltyCallback() {

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

    /**
     * Initiates a payment for a specific penalty in the database.
     *
     * @param penaltyToFInd The PenaltyDTO object representing the penalty for payment initiation.
     * @param callback The callback to handle the payment initiation result or errors encountered.
     */
    private void doPaymentToBd(final PenaltyDTO penaltyToFInd,final PenaltyCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/penalty/doPayment.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        PenaltyDTO penalty = new PenaltyDTO();
                        penalty.setId(Integer.valueOf(jsonResponse.getString("id")));

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id", penaltyToFInd.getId().toString());
                params.put("quantity",penaltyToFInd.getQuantity().toString());
                return params;
            }
        };

        requestQueue.add(request);
    }

    /**
     * Retrieves penalties associated with a specific user from the database.
     *
     * @param penalty The PenaltyDTO object representing the user's penalty.
     * @param applicationContext The application context.
     * @param callback The callback to handle the penalties received or errors encountered.
     */
    public void getPenalties(PenaltyDTO penalty,Context applicationContext, PenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getPenaltiesFromBd(penalty,new PenaltyCallback() {

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

    /**
     * Retrieves penalties associated with a specific user and state from the database.
     *
     * @param penaltyToSend The PenaltyDTO object representing the user's penalty.
     * @param callback The callback to handle the penalties received or errors encountered.
     */
    private void getPenaltiesFromBd(final PenaltyDTO penaltyToSend,final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getPenaltiesByStateOfUser.php";

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
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dni", penaltyToSend.getDniClient());
                params.put("state",penaltyToSend.getState().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * Retrieves penalties associated with a specific date range and user from the database.
     *
     * @param date1 The start date of the date range.
     * @param date2 The end date of the date range.
     * @param penalty The PenaltyDTO object representing the user's penalty.
     * @param applicationContext The application context.
     * @param callback The callback to handle the penalties received or errors encountered.
     */

    public void getPenalties(String date1,String date2,PenaltyDTO penalty,Context applicationContext, PenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getPenaltiesFromBd(date1,date2,penalty,new PenaltyCallback() {

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

    /**
     * Retrieves penalties associated with a specific date range and user from the database.
     *
     * @param date1 The start date of the date range.
     * @param date2 The end date of the date range.
     * @param penaltyToSend The PenaltyDTO object representing the penalty to send.
     * @param callback The callback to handle the penalties received or errors encountered.
     */
    private void getPenaltiesFromBd(final String date1, final String date2,final PenaltyDTO penaltyToSend,final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getPenaltiesByDatesByUser.php";

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
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dni", penaltyToSend.getDniClient());
                params.put("date1",date1);
                params.put("date2",date2);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * Retrieves the last penalty associated with a specific user from the database.
     *
     * @param penalty The PenaltyDTO object representing the penalty.
     * @param applicationContext The application context.
     * @param callback The callback to handle the last penalty received or errors encountered.
     */

    public void getLastPenalty(PenaltyDTO penalty,Context applicationContext, PenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getLastPenalty(penalty,new PenaltyCallback() {

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

    /**
     * Retrieves the last penalty associated with a specific user from the database.
     *
     * @param penaltyToSend The PenaltyDTO object representing the penalty to send.
     * @param callback The callback to handle the last penalty received or errors encountered.
     */
    private void getLastPenalty(final PenaltyDTO penaltyToSend,final PenaltyCallback callback) {
        String URL = "http://192.168.1.19:81/api/ucodgt/penalty/getLastPenalty.php";

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    if (response != null && !response.isEmpty()) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            PenaltyDTO penalty = new PenaltyDTO();
                            penalty.setId(Integer.valueOf(jsonResponse.getString("id")));

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                    }
                },
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dni", penaltyToSend.getDniClient());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    /**
     * Retrieves the penalty for cancel.
     *
     * @param penaltyToFind The PenaltyDTO object representing the penalty to cancel.
     * @param applicationContext The application context.
     * @param callback The callback to handle the penalty received or errors encountered.
     */

    public void cancelPenalty(PenaltyDTO penaltyToFind, Context applicationContext, PenaltyCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        cancelPenalty(penaltyToFind, new PenaltyCallback() {

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

    /**
     * Set a penalty cancelled in the DB.
     *
     * @param penaltyToFInd The PenaltyDTO object representing the penalty to cancel.
     * @param callback The callback to handle the received penalty or errors encountered.
     */
    private void cancelPenalty(final PenaltyDTO penaltyToFInd,final PenaltyCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/penalty/cancelPenalty.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    callback.onPenaltyReceived(new PenaltyDTO());
                },
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id", penaltyToFInd.getId().toString());
                params.put("quantity",penaltyToFInd.getQuantity().toString());
                params.put("points",penaltyToFInd.getPoints().toString());
                params.put("dni",penaltyToFInd.getDniClient());
                return params;
            }
        };

        requestQueue.add(request);
    }


}
