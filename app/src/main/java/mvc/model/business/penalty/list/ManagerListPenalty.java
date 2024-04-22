package mvc.model.business.penalty.list;

import android.content.Context;

import com.android.volley.VolleyError;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.data.ListPenaltyCallback;
import mvc.model.data.penalty.listPenalty.ListPenaltyDAO;

public class ManagerListPenalty {

    public void checkList(PenaltyDTO penalty,Context context, ListPenaltyCallback callback){
        ListPenaltyDAO lpD=new ListPenaltyDAO();
        lpD.checkList(penalty,context, new ListPenaltyCallback() {
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
}
