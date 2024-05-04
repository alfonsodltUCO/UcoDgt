package com.uco.ucodgt.mvc.model.data.vehicle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.business.vehicle.typeof;
import com.uco.ucodgt.mvc.model.business.vehicle.typeofColor;
import com.uco.ucodgt.mvc.model.data.VehicleCallback;

/**
 * This class provides methods to interact with a database or external services
 * for managing vehicles.
 * @author Alfonso de la torre
 */
public class VehicleDAO {
    RequestQueue requestQueue;
    int TIMEOUT_MS = 20000; // 20 segundos

    /**
     * Get a vehicle using an image of licence plate.
     *
     * @param image              The image of the vehicle to be checked.
     * @param applicationContext The application context.
     * @param callback           The callback to handle the result of the vehicle check.
     */
    public void checkVehicle(Bitmap image, Context applicationContext, VehicleCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        checkVehicleFromPy(image, new VehicleCallback() {

            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicle);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });
    }

    /**
     * Sends an image for vehicle recognition and handles the response accordingly.
     *
     * @param image    The image of the vehicle to be recognized.
     * @param callback The callback to handle the result of the vehicle recognition.
     */
    private void checkVehicleFromPy(final Bitmap image, final VehicleCallback callback) {
        String URL = "http://34.118.84.252:8080/checkImage";

        JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.POST,
                URL,
                null,
                response -> {
                    Log.d("Response", response.toString());
                    try {
                        String plate= (String) response.get("plate_text");
                        if(plate.isEmpty()){
                            checkVehicleFromPy2(image, new VehicleCallback() {
                                @Override
                                public void onVehicleReceived(VehicleDTO vehicle) {

                                    callback.onVehicleReceived(vehicle);
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    callback.onError(error);//no recognized
                                }

                                @Override
                                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                                }
                            });
                        }else{
                            getVehicleFromBd(plate, new VehicleCallback() {
                                @Override
                                public void onVehicleReceived(VehicleDTO vehicle) {
                                    callback.onVehicleReceived(vehicle);
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    callback.onError(error);//404
                                }

                                @Override
                                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                callback::onError
        ) {
            @Override
            public byte[] getBody() {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("image", encodedImage);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            public RetryPolicy getRetryPolicy() {
                return new DefaultRetryPolicy(
                        TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                );
            }
        };

        requestQueue.add(request);
    }

    /**
     * Another private method similar to checkVehicleFromPy, it sends an image for
     * vehicle recognition and handles the response accordingly.
     *
     * @param image    The image of the vehicle to be recognized.
     * @param callback The callback to handle the result of the vehicle recognition.
     */
    private void checkVehicleFromPy2(final Bitmap image, final VehicleCallback callback) {
        String URL = "http://34.118.84.252:8080/checkImage2";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                response -> {
                    try {
                        String plate= (String) response.get("plate_text");
                        if(plate.isEmpty()){

                        }else{
                            getVehicleFromBd(plate, new VehicleCallback() {
                                @Override
                                public void onVehicleReceived(VehicleDTO vehicle) {
                                    callback.onVehicleReceived(vehicle);
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    callback.onError(error);//404
                                }

                                @Override
                                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                callback::onError
        ) {
            @Override
            public byte[] getBody() {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("image", encodedImage);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            public RetryPolicy getRetryPolicy() {
                return new DefaultRetryPolicy(
                        TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                );
            }
        };
        requestQueue.add(request);
    }

    /**
     * Retrieves information about a vehicle from the database using its license
     * plate.
     *
     * @param licencePlate The license plate of the vehicle to retrieve information
     *                     for.
     * @param callback     The callback to handle the result of the database
     *                     retrieval.
     */
    private void getVehicleFromBd(final String licencePlate,final VehicleCallback callback){
        String URL="http://34.118.84.252/api/ucodgt/vehicle/getVehicle.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String licenceplate = jsonResponse.getString("licenceplate");
                        typeof carType = typeof.valueOf(jsonResponse.getString("carType"));
                        typeofColor color = typeofColor.valueOf(jsonResponse.getString("color"));
                        String validItvFrom = jsonResponse.getString("validItvFrom");
                        String idInsurance = jsonResponse.getString("idInsurance");
                        String validItvTo = jsonResponse.getString("validItvTo");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date itv1;
                        Date itv2;
                        try {
                            itv1 = format.parse(validItvFrom);
                            itv2 = format.parse(validItvTo);

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        int id=Integer.parseInt(idInsurance);

                        VehicleDTO vehicle=new VehicleDTO(licenceplate,carType,color,itv1,itv2,id);
                        callback.onVehicleReceived(vehicle);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("licencePlate", licencePlate);
                return params;
            }
        };

        requestQueue.add(request);
    }

    /**
     * Deletes a vehicle from the database.
     *
     * @param vehicle            The vehicle to be deleted.
     * @param applicationContext The application context.
     * @param callback           The callback to handle the result of the deletion.
     */
    public void deleteVehicle(VehicleDTO vehicle, Context applicationContext, VehicleCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        deleteVehicleFromBd(vehicle, new VehicleCallback() {

            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicle);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });
    }

    /**
     * Deletes a vehicle from the database.
     *
     * @param vehicleSend The VehicleDTO object representing the vehicle to delete.
     * @param callback The callback to handle the result of deleting the vehicle or errors encountered.
     */
    private void deleteVehicleFromBd(final VehicleDTO vehicleSend,final VehicleCallback callback){
        String URL="http://34.118.84.252/api/ucodgt/vehicle/deleteVehicle.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {

                        JSONObject jsonResponse = new JSONObject(response);
                        typeof carType = typeof.valueOf(jsonResponse.getString("carType"));
                        typeofColor color = typeofColor.valueOf(jsonResponse.getString("color"));
                        String validItvFrom = jsonResponse.getString("validItvFrom");
                        String idInsurance = jsonResponse.getString("idInsurance");
                        String validItvTo = jsonResponse.getString("validItvTo");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date itv1;
                        Date itv2;
                        try {
                            itv1 = format.parse(validItvFrom);
                            itv2 = format.parse(validItvTo);

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        int id=Integer.parseInt(idInsurance);
                        VehicleDTO vehicle=new VehicleDTO(vehicleSend.getLicencePlate(),carType,color,itv1,itv2,id);
                        callback.onVehicleReceived(vehicle);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                params.put("licencePlate", vehicleSend.getLicencePlate());
                return params;
            }
        };
        requestQueue.add(request);
    }

    /**
     * Adds a new vehicle to the database.
     *
     * @param vehicleToAdd The VehicleDTO object representing the vehicle to add.
     * @param client The ClientDTO object representing the client associated with the vehicle.
     * @param applicationContext The application context.
     * @param callback The callback to handle the result of adding the vehicle or errors encountered.
     */
    public void addVehicle(VehicleDTO vehicleToAdd,ClientDTO client, Context applicationContext, VehicleCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        addToDb(vehicleToAdd,client,new VehicleCallback() {

            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicleToAdd);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });
    }

    /**
     * Adds a new vehicle to the database.
     *
     * @param vehicle The VehicleDTO object representing the vehicle to add.
     * @param client The ClientDTO object representing the client associated with the vehicle.
     * @param callback The callback to handle the result of adding the vehicle or errors encountered.
     */
    private void addToDb(final VehicleDTO vehicle,final ClientDTO client, final VehicleCallback callback) {
        String URL = "http://34.118.84.252/api/ucodgt/vehicle/addVehicle.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    if(!response.isEmpty()){
                        callback.onVehicleReceived(vehicle);
                    }
                },
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String strDate1= formatter.format(vehicle.getValidItvFrom());
                String strDate2= formatter.format(vehicle.getValidItvTo());
                Map<String, String> params = new HashMap<>();
                params.put("licencePlate", vehicle.getLicencePlate());
                params.put("carType", vehicle.getCarType().toString().trim());
                params.put("color", vehicle.getColor().toString().trim());
                params.put("validItvFrom", strDate1);
                params.put("validItvTo", strDate2);
                params.put("dni_client", client.getDni());
                params.put("idInsurance", String.valueOf(vehicle.getIdInsurance()));
                return params;
            }
        };

        requestQueue.add(request);
    }

    /**
     * Retrieves all vehicles from the database.
     *
     * @param applicationContext The application context.
     * @param callback The callback to handle the received vehicles or errors encountered.
     */
    public void getVehicles(Context applicationContext, VehicleCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getVehiclesFromBd(new VehicleCallback() {


            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {
                callback.onVehiclesReceived(vehicles);
            }
        });
    }
    /**
     * Retrieves all vehicles from the database.
     *
     * @param callback The callback to handle the received vehicles or errors encountered.
     */
    private void getVehiclesFromBd(final VehicleCallback callback){
        String URL="http://34.118.84.252/api/ucodgt/vehicle/getAllVehicles.php";

        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {
                    if(response.length()>0){
                        try {
                            JSONArray listofvehicles=response.getJSONArray("vehicles");
                            List<VehicleDTO> vehiclestosend=new ArrayList<VehicleDTO>();
                            for(int i=0;i<listofvehicles.length();i++){
                                VehicleDTO vehicle=new VehicleDTO();
                                JSONObject vehicleJson=listofvehicles.getJSONObject(i);

                                vehicle.setLicencePlate(vehicleJson.getString("licenceplate"));
                                vehicle.setCarType(typeof.valueOf(vehicleJson.getString("carType")));
                                vehicle.setColor(typeofColor.valueOf(vehicleJson.getString("color")));
                                vehicle.setIdInsurance(Integer.parseInt(vehicleJson.getString("idInsurance")));
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateItvF,dateItvT;
                                try {
                                    dateItvF = format.parse(vehicleJson.getString("validItvFrom"));
                                    dateItvT = format.parse(vehicleJson.getString("validItvTo"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                vehicle.setValidItvFrom(dateItvF);
                                vehicle.setValidItvTo(dateItvT);

                                vehiclestosend.add(vehicle);
                            }
                            callback.onVehiclesReceived(vehiclestosend);
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
     * Retrieves information about a specific vehicle from the database.
     *
     * @param vehicle The VehicleDTO object representing the vehicle to retrieve.
     * @param applicationContext The application context.
     * @param callback The callback to handle the received vehicle or errors encountered.
     */
    public void getVehicle(VehicleDTO vehicle, Context applicationContext, VehicleCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        getVehicleToFind(vehicle, new VehicleCallback() {

            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicle);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });
    }
    /**
     * Retrieves information about a specific vehicle from the database.
     *
     * @param vehicle The VehicleDTO object representing the vehicle to retrieve.
     * @param callback The callback to handle the received vehicle or errors encountered.
     */
    private void getVehicleToFind(final VehicleDTO vehicle,final VehicleCallback callback){
        String URL="http://34.118.84.252/api/ucodgt/vehicle/getVehicle.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String licenceplate = jsonResponse.getString("licenceplate");
                        String color = jsonResponse.getString("color");
                        String carType = jsonResponse.getString("carType");
                        String validItvFrom = jsonResponse.getString("validItvFrom");
                        String validItvTo = jsonResponse.getString("validItvTo");
                        String idIns=jsonResponse.getString("idInsurance");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dt1,dt2;
                        try {
                            dt1 = format.parse(validItvFrom);
                            dt2 = format.parse(validItvTo);

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        VehicleDTO vehicleToSend = new VehicleDTO(licenceplate,typeof.valueOf(carType),typeofColor.valueOf(color),dt1,dt2,Integer.parseInt(idIns));
                        callback.onVehicleReceived(vehicleToSend);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("licencePlate", vehicle.getLicencePlate());
                return params;
            }
        };

        requestQueue.add(request);
    }

    /**
     * Retrieves information about vehicles associated with a specific client from the database.
     *
     * @param client The ClientDTO object representing the client whose vehicles are to be retrieved.
     * @param applicationContext The application context.
     * @param callback The callback to handle the received vehicles or errors encountered.
     */
    public void getVehicles(ClientDTO client,Context applicationContext, VehicleCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        getVehiclesFromBd(client,new VehicleCallback() {


            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {
                callback.onVehiclesReceived(vehicles);
            }
        });
    }

    /**
     * Retrieves information about vehicles associated with a specific client from the database.
     *
     * @param client The ClientDTO object representing the client whose vehicles are to be retrieved.
     * @param callback The callback to handle the received vehicles or errors encountered.
     */
    private void getVehiclesFromBd(final ClientDTO client,final VehicleCallback callback){
        String URL="http://34.118.84.252/api/ucodgt/vehicle/getAllVehiclesByDni.php";

        StringRequest stringRequest = new StringRequest(

                Request.Method.POST,
                URL,
                response -> {
                    if(response != null && !response.isEmpty()){
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray listofvehicles=jsonResponse.getJSONArray("vehicles");
                            List<VehicleDTO> vehiclestosend=new ArrayList<VehicleDTO>();
                            for(int i=0;i<listofvehicles.length();i++){
                                VehicleDTO vehicle=new VehicleDTO();
                                JSONObject vehicleJson=listofvehicles.getJSONObject(i);

                                vehicle.setLicencePlate(vehicleJson.getString("licenceplate"));
                                vehicle.setCarType(typeof.valueOf(vehicleJson.getString("carType")));
                                vehicle.setColor(typeofColor.valueOf(vehicleJson.getString("color")));
                                vehicle.setIdInsurance(Integer.parseInt(vehicleJson.getString("idInsurance")));
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date dateItvF,dateItvT;
                                try {
                                    dateItvF = format.parse(vehicleJson.getString("validItvFrom"));
                                    dateItvT = format.parse(vehicleJson.getString("validItvTo"));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                vehicle.setValidItvFrom(dateItvF);
                                vehicle.setValidItvTo(dateItvT);

                                vehiclestosend.add(vehicle);
                            }
                             callback.onVehiclesReceived(vehiclestosend);
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
                params.put("dni", client.getDni());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * Update the vehicle with given dates for new itv dates.
     *
     * @param vehicleToAdd The VehicleDTO object representing the vehicle to update.
     * @param applicationContext The application context.
     * @param callback The callback to handle the result of adding the vehicle or errors encountered.
     */
    public void updateItv(VehicleDTO vehicleToAdd, Context applicationContext, VehicleCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        updateItv(vehicleToAdd,new VehicleCallback() {

            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicleToAdd);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });
    }

    /**
     * Update the dates of itv by a given vehicle.
     *
     * @param vehicle The VehicleDTO object representing the vehicle to update.
     * @param callback The callback to handle the result of adding the vehicle or errors encountered.
     */
    private void updateItv(final VehicleDTO vehicle, final VehicleCallback callback) {
        String URL = "http://34.118.84.252/api/ucodgt/vehicle/updateItv.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    if(!response.isEmpty()){
                        callback.onVehicleReceived(vehicle);
                    }
                },
                callback::onError
        ) {
            @Override
            protected Map<String, String> getParams() {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String strDate1= formatter.format(vehicle.getValidItvFrom());
                String strDate2= formatter.format(vehicle.getValidItvTo());
                Map<String, String> params = new HashMap<>();
                params.put("licencePlate", vehicle.getLicencePlate());
                params.put("validItvFrom", strDate1);
                params.put("validItvTo", strDate2);
                return params;
            }
        };

        requestQueue.add(request);
    }

}
