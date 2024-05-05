package com.uco.ucodgt.mvc.model.business.penalty.list;

import android.content.Context;

import com.android.volley.VolleyError;

import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.data.ListPenaltyCallback;
import com.uco.ucodgt.mvc.model.data.penalty.listPenalty.ListPenaltyDAO;

/**
 * This class is responsible for managing the list of possible penalties.
 * It interacts with the data access object (DAO) to check the list of penalties.
 * @author Alfonso de la Torre
 */

public class ManagerListPenalty {

    /**
     * Checks the list of penalties.
     *
     * @param penalty  The penalty DTO to check against the list.
     * @param context  The context of the application.
     * @param callback The callback to handle the result of the operation.
     */
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
