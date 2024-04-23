package mvc.controller.commonFunctions;

import android.content.Context;

import com.android.volley.VolleyError;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.list.ListPenaltyDTO;
import mvc.model.business.penalty.list.ManagerListPenalty;
import mvc.model.data.ListPenaltyCallback;

public class ForCheckListPenalty {

    public static boolean checkPenalty(PenaltyDTO penalty,Context context) {
        final boolean[] success = {false}; // Variable de estado

        ManagerListPenalty mngLP = new ManagerListPenalty();
        mngLP.checkList(penalty,context, new ListPenaltyCallback() {
            @Override
            public void onError(VolleyError error) {
                success[0] = false; // Error, establecer success como false
            }

            @Override
            public void onListReceived(ListPenaltyDTO listPenalty) {
                if(penalty.getQuantity()>listPenalty.getMaxQ() || penalty.getQuantity()<listPenalty.getMinQ()){
                    success[0] = false;
                } else if (penalty.getPoints()>listPenalty.getMaxP() || penalty.getPoints()<listPenalty.getMinP()) {
                    success[0] = false;
                }
                success[0] = true;
            }
        });

        return success[0];
    }

}
