package mvc.model.data.penalty;

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
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.business.vehicle.typeofColor;
import mvc.model.data.PenaltyCallback;
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

}
