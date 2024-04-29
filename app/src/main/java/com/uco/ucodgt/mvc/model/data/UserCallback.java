package com.uco.ucodgt.mvc.model.data;

import com.android.volley.VolleyError;

import java.util.List;

import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;

/**
 * Interface for handling callbacks related to user operations.
 * @author Alfonso de la torre
 */
public interface UserCallback {

    /**
     * Called when a client user is received.
     *
     * @param user The ClientDTO object representing the received client user.
     */
    void onUserReceived(ClientDTO user);

    /**
     * Called when an error occurs during a user operation.
     *
     * @param error The VolleyError object representing the error.
     */
    void onError(VolleyError error);

    /**
     * Called when a worker user is received.
     *
     * @param user The WorkerDTO object representing the received worker user.
     */
    void onWorkerReceived(WorkerDTO user);

    /**
     * Called when an administrator user is received.
     *
     * @param user The AdminDTO object representing the received administrator user.
     */
    void onAdminReceived(AdminDTO user);

    /**
     * Called when a list of worker users is received.
     *
     * @param workers The list of WorkerDTO objects representing the received worker users.
     */
    void onWorkersReceived(List<WorkerDTO> workers);

    /**
     * Called when a list of client users is received.
     *
     * @param clients The list of ClientDTO objects representing the received client users.
     */
    void onClientsReceived(List<ClientDTO> clients);
}