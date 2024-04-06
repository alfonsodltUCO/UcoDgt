package mvc.model.data.vehicle;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

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
    private void checkVehicleFromBd(final Bitmap image,final VehicleCallback callback) {
        String URL = "http://192.168.1.19:8080/checkImage";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,
                response -> {
                    Log.d("a",response);
                },
                error -> {
                    callback.onError(error);
                    Log.d("ADebugTag", "Value: " + error.toString());

                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String toSend = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Map<String, String> params = new HashMap<>();
                params.put("image", toSend);
                return params;
            }
        };
        requestQueue.add(request);
    }

}
