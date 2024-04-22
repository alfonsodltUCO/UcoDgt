package mvc.model.data.penalty.listPenalty;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import mvc.model.business.penalty.list.severityType;
import mvc.model.business.penalty.typeof;

import org.json.JSONException;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.list.ListPenaltyDTO;

import mvc.model.data.ListPenaltyCallback;

public class ListPenaltyDAO {

    RequestQueue requestQueue;


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
    private void checkList(final PenaltyDTO penalty,final ListPenaltyCallback callback){
        String URL="http://192.168.1.19:81/api/ucodgt/listPenalty/getListPenalty.php?type="+penalty.getReason();

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
                error -> {
                    callback.onError(error);

                }
        );

        requestQueue.add(JsonObjectRequest);
    }

}
