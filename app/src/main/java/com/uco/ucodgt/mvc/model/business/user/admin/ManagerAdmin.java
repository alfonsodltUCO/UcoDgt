package com.uco.ucodgt.mvc.model.business.user.admin;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.List;

import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.model.data.admin.AdminDAO;


/**
 * Manages operations related to administrator users in the system.
 * This class provides methods to handle administrator user logins and email existence checks.
 * It communicates with the data layer through an instance of AdminDAO.
 * @author Alfonso de la torre
 *
 */
public class ManagerAdmin {

    /**
     * Default constructor for ManagerAdmin.
     */
    public ManagerAdmin(){

    }

    /**
     * Checks the login credentials of an administrator user.
     *
     * @param admin The AdminDTO object containing login credentials (email and password) to be checked.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the login check operation.
     */
    public void checkLogInAdmin(AdminDTO admin, Context applicationContext, UserCallback callback){
        AdminDAO userD=new AdminDAO();
        AdminDTO userToCheck=new AdminDTO(null,admin.getPassword(),null,null,null,admin.getEmail());
        userD.checkLogInAdmin(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {
                callback.onAdminReceived(user);

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }

    /**
     * Checks if the given email address already exists in the system for an administrator user.
     *
     * @param admin The AdminDTO object containing the email address to be checked.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the email existence check operation.
     */
    public void checkEmailNotExists(AdminDTO admin, Context applicationContext, UserCallback callback){
        AdminDAO userD=new AdminDAO();
        AdminDTO userToCheck=new AdminDTO(null,null,null,null,null, admin.getEmail());
        userD.checkEmailAdmin(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {
                callback.onAdminReceived(user);

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }


}
