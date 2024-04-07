package mvc.model.data.vehicle;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
import mvc.model.data.UserCallback;
import mvc.model.data.VehicleCallback;

public class VehicleDAO {
    RequestQueue requestQueue;


    public void checkVehicle(Bitmap image, Context applicationContext, VehicleCallback callback){

        requestQueue= Volley.newRequestQueue(applicationContext);
        checkVehicleFromBd(image, new VehicleCallback() {

            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });
    }
    private void checkVehicleFromBd(final Bitmap image, final VehicleCallback callback) {
        String URL = "http://192.168.1.19:8080/checkImage";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                response -> {
                    // Maneja la respuesta aquí
                    Log.d("Response", response.toString());
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


}
