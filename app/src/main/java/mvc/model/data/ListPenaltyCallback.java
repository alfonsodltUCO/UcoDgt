package mvc.model.data;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.list.ListPenaltyDTO;

/**
 * Interface for handling callbacks related to penalty list operations.
 * @author Alfonso de la torre
 */
public interface ListPenaltyCallback {

    /**
     * Called when an error occurs during a penalty list operation.
     *
     * @param error The VolleyError object representing the error.
     */
    void onError(VolleyError error);

    /**
     * Called when a penalty list is received.
     *
     * @param listPenalty The ListPenaltyDTO object representing the received penalty list.
     */
    void onListReceived(ListPenaltyDTO listPenalty);
}