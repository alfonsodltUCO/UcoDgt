package mvc.model.data;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.list.ListPenaltyDTO;

public interface ListPenaltyCallback {
    void onError(VolleyError error);

    void onListReceived(ListPenaltyDTO listPenalty);
}
