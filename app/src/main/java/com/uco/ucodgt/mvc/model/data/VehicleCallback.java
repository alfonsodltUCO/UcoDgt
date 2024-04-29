package com.uco.ucodgt.mvc.model.data;

import com.android.volley.VolleyError;

import java.util.List;

import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;

/**
 * Interface for handling callbacks related to vehicle operations.
 * @author Alfonso de la torre
 */
public interface VehicleCallback {

    /**
     * Called when a single vehicle is received.
     *
     * @param vehicle The VehicleDTO object representing the received vehicle.
     */
    void onVehicleReceived(VehicleDTO vehicle);

    /**
     * Called when an error occurs during a vehicle operation.
     *
     * @param error The VolleyError object representing the error.
     */
    void onError(VolleyError error);

    /**
     * Called when a list of vehicles is received.
     *
     * @param vehicles The list of VehicleDTO objects representing the received vehicles.
     */
    void onVehiclesReceived(List<VehicleDTO> vehicles);
}