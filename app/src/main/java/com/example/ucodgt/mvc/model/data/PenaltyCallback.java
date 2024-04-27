package com.example.ucodgt.mvc.model.data;

import com.android.volley.VolleyError;

import java.util.List;

import com.example.ucodgt.mvc.model.business.penalty.PenaltyDTO;

/**
 * Interface for handling callbacks related to penalty operations.
 * @author Alfonso de la torre
 */
public interface PenaltyCallback {

    /**
     * Called when a list of penalties is received.
     *
     * @param penalties The list of PenaltyDTO objects representing the received penalties.
     */
    void onPenaltiesReceived(List<PenaltyDTO> penalties);

    /**
     * Called when an error occurs during a penalty operation.
     *
     * @param error The VolleyError object representing the error.
     */
    void onError(VolleyError error);

    /**
     * Called when a single penalty is received.
     *
     * @param penalty The PenaltyDTO object representing the received penalty.
     */
    void onPenaltyReceived(PenaltyDTO penalty);
}

