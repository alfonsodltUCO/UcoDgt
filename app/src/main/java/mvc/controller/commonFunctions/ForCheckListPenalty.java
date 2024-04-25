package mvc.controller.commonFunctions;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.Objects;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.list.ListPenaltyDTO;
import mvc.model.business.penalty.list.ManagerListPenalty;
import mvc.model.data.ListPenaltyCallback;

/**
 * This class provides a method to check penalty information in a list context.
 * @author Alfonso de la torre
 */
public class ForCheckListPenalty {

    /**
     * Checks penalty information within a list context.
     *
     * @param penalty  The PenaltyDTO object containing penalty information.
     * @param context  The context of the application.
     * @param callback The callback to handle the result of the penalty check.
     */
    public static void checkPenalty(PenaltyDTO penalty, Context context, ListPenaltyCallback callback) {
        ManagerListPenalty mngLP = new ManagerListPenalty();
        mngLP.checkList(penalty, context, new ListPenaltyCallback() {
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