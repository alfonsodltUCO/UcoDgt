package com.uco.ucodgt.mvc.model.data.penalty.listPenalty;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uco.ucodgt.mvc.model.business.penalty.list.severityType;
import com.uco.ucodgt.mvc.model.business.penalty.typeof;

import org.json.JSONException;

import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.business.penalty.list.ListPenaltyDTO;

import com.uco.ucodgt.mvc.model.data.ListPenaltyCallback;

/**
 * Data Access Object (DAO) class for handling operations related to penalty lists.
 * @author Alfonso de la torre
 */
public class ListPenaltyDAO {

    RequestQueue requestQueue;

    /**
     * Checks the list of penalties based on the provided PenaltyDTO object.
     *
     * @param penalty The PenaltyDTO object containing the reason for the penalty to check.
     * @param applicationContext The context of the application.
     * @param callback The callback to handle the result of the check operation.
     */
    public void checkList(PenaltyDTO penalty, Context applicationContext, ListPenaltyCallback callback){
        requestQueue= Volley.newRequestQueue(applicationContext);
        checkList(penalty, new ListPenaltyCallback() {

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onListReceived(ListPenaltyDTO listPenalty) {
                callback.onListReceived(listPenalty);
            }
        });
    }

    /**
     * Private method to perform the actual check of the penalty list.
     *
     * @param penalty The PenaltyDTO object containing the reason for the penalty to check.
     * @param callback The callback to handle the result of the check operation.
     */
    private void checkList(final PenaltyDTO penalty,final ListPenaltyCallback callback){
        String URL="http://10.0.2.2:81/api/ucodgt/penalty/listPenalty/getListPenalty.php?reason="+penalty.getReason();

        JsonObjectRequest JsonObjectRequest;
        JsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET,
                URL,
                null,
                response -> {

                    try {
                        String reason=response.getString("reason");
                        String maxQ=response.getString("maxQuantity");
                        String minQ =response.getString("minQuantity");
                        String lawArticle=response.getString("lawArticle");
                        String severity=response.getString("severity");
                        String maxPoints=response.getString("maxPoints");
                        String minPoints=response.getString("minPoints");
                        ListPenaltyDTO lpD=new ListPenaltyDTO(typeof.valueOf(reason),Float.parseFloat(maxQ),Float.parseFloat(minQ),Integer.parseInt(lawArticle),Integer.parseInt(maxPoints),Integer.parseInt(minPoints),severityType.valueOf(severity));
                        callback.onListReceived(lpD);
                    } catch (JSONException e) {

                        callback.onListReceived(new ListPenaltyDTO());
                    }
                },
                callback::onError
        );

        requestQueue.add(JsonObjectRequest);
    }

}
