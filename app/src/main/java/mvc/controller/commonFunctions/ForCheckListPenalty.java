package mvc.controller.commonFunctions;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.Objects;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.list.ListPenaltyDTO;
import mvc.model.business.penalty.list.ManagerListPenalty;
import mvc.model.data.ListPenaltyCallback;

public class ForCheckListPenalty {

    public static void checkPenalty(PenaltyDTO penalty,Context context,ListPenaltyCallback callback) {
        ManagerListPenalty mngLP = new ManagerListPenalty();
        mngLP.checkList(penalty,context, new ListPenaltyCallback() {
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
