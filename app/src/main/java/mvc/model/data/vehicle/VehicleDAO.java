package mvc.model.data.vehicle;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.business.vehicle.typeof;
import mvc.model.business.vehicle.typeofColor;
import mvc.model.data.UserCallback;
import mvc.model.data.VehicleCallback;

public class VehicleDAO {
    RequestQueue requestQueue;


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
        });
    }
    private void checkVehicleFromPy(final Bitmap image, final VehicleCallback callback) {
        String URL = "http://192.168.1.19:8080/checkImage";

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
                            });
                        }else{
                            getVehicle(plate, new VehicleCallback() {
                                @Override
                                public void onVehicleReceived(VehicleDTO vehicle) {
                                    callback.onVehicleReceived(vehicle);
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    callback.onError(error);//404
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    // Maneja los errores aquí
                    callback.onError(error);
                    Log.e("Error", error.toString());
                }
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
        };

        // Agrega la solicitud a la cola de solicitudes de Volley
        requestQueue.add(request);
    }

    private void checkVehicleFromPy2(final Bitmap image, final VehicleCallback callback) {
        String URL = "http://192.168.1.19:8080/checkImage2";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                response -> {
                    Log.d("Response", response.toString());
                    try {
                        String plate= (String) response.get("plate_text");
                        if(plate.isEmpty()){
                            callback.onError(new VolleyError(new NetworkResponse(401,null,null,true)));//no reconoce matricula
                        }else{
                            getVehicle(plate, new VehicleCallback() {
                                @Override
                                public void onVehicleReceived(VehicleDTO vehicle) {
                                    callback.onVehicleReceived(vehicle);
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    callback.onError(error);//404
                                }
                            });
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    // Maneja los errores aquí
                    callback.onError(error);
                    Log.e("Error", error.toString());
                }
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
        };

        // Agrega la solicitud a la cola de solicitudes de Volley
        requestQueue.add(request);
    }

    private void getVehicle(final String licencePlate,final VehicleCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/vehicle/getVehicle.php";
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
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date itv1;
                        Date itv2;
                        try {
                            itv1 = format.parse(validItvFrom);
                            itv2 = format.parse(validItvTo);

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        int id=Integer.parseInt(idInsurance);
                        VehicleDTO vehicle=new VehicleDTO(licencePlate,carType,color,itv1,itv2,id);
                        callback.onVehicleReceived(vehicle);

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
                params.put("licencePlate", licencePlate);
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void deleteVehicle(VehicleDTO vehicle, Context applicationContext, VehicleCallback callback){

        String licencePlate=vehicle.getLicencePlate();
        requestQueue= Volley.newRequestQueue(applicationContext);
        deleteVehicleFromBd(licencePlate, new VehicleCallback() {

            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicle);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });
    }
    // tienes que hacer 2 mas, uno por cada tabla, si no devuelve vacío entocnes en typeof pones el tipo que es de usuario
    private void deleteVehicleFromBd(final String licencePlate,final VehicleCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/vehicle/deleteVehicle.php";
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
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date itv1;
                        Date itv2;
                        try {
                            itv1 = format.parse(validItvFrom);
                            itv2 = format.parse(validItvTo);

                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        int id=Integer.parseInt(idInsurance);
                        VehicleDTO vehicle=new VehicleDTO(licencePlate,carType,color,itv1,itv2,id);
                        callback.onVehicleReceived(vehicle);

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
                params.put("licencePlate", licencePlate);
                return params;
            }
        };
        requestQueue.add(request);
    }


}
