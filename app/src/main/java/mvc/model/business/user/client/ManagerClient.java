package mvc.model.business.user.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.client.ClientDAO;


/**
 * Manages operations related to client users in the system.
 * This class provides methods to handle client user logins, user addition, user deletion,
 * fetching user data, updating user data, and checking email existence.
 * It communicates with the data layer through an instance of ClientDAO.
 * @author Alfonso de la torre
 *
 */
public class ManagerClient {

    /**
     * Default constructor for ManagerClient.
     */
    public ManagerClient(){

    }


    /**
     * Checks the login credentials of a client user.
     *
     * @param clientDTO The ClientDTO object containing login credentials (email and password) to be checked.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the login check operation.
     */
    public void checkLogInClient(ClientDTO clientDTO, Context applicationContext,UserCallback callback){
        ClientDAO userD=new ClientDAO();
        ClientDTO userToCheck=new ClientDTO(null,clientDTO.getPassword(),null,null,null,clientDTO.getEmail(),null);

        userD.checkLogInClient(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

                callback.onUserReceived(user);
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
     * Checks if the given email address already exists in the system for a client user.
     *
     * @param client The ClientDTO object containing the email address to be checked.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the email existence check operation.
     */
    public void checkEmailNotExists(ClientDTO client, Context applicationContext, UserCallback callback){
        ClientDAO clientD=new ClientDAO();
        ClientDTO userToCheck=new ClientDTO(null,null,null,null,null, client.getEmail(), null);
        clientD.checkEmailClient(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {}

            @Override
            public void onAdminReceived(AdminDTO user) {}

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }

    /**
     * Adds a new client user to the system.
     *
     * @param client The ClientDTO object representing the new client user.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the user addition operation.
     */
    public void addUser(ClientDTO client, Context applicationContext, UserCallback callback) {
        ClientDAO clientD=new ClientDAO();
        clientD.addUser(client, applicationContext, new UserCallback(){

            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
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
     * Retrieves a client user's information from the system.
     *
     * @param client The ClientDTO object representing the client user whose information is to be fetched.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the user information retrieval operation.
     */
    public void getUser(ClientDTO client, Context applicationContext, UserCallback callback){
        ClientDAO clientD=new ClientDAO();
        clientD.getUser(client, applicationContext, new UserCallback(){

            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
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
     * Deletes a client user from the system.
     *
     * @param client The ClientDTO object representing the client user to be deleted.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the user deletion operation.
     */
    public void deleteUser(ClientDTO client, Context applicationContext, UserCallback callback){
        ClientDAO clientD=new ClientDAO();
        clientD.deleteUser(client, applicationContext, new UserCallback(){

            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
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
     * Retrieves a list of all client users from the system.
     *
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the user retrieval operation.
     */
    public void getUsers(Context applicationContext, UserCallback callback){
        ClientDAO clientD=new ClientDAO();
        clientD.getUsers(applicationContext, new UserCallback(){

            @Override
            public void onUserReceived(ClientDTO user) {
            }

            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {
                callback.onClientsReceived(clients);
            }
        });
    }


    /**
     * Updates the license points of a client user in the system.
     *
     * @param client The ClientDTO object representing the client user whose license points are to be updated.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the license points update operation.
     */
    public void updatePoints(ClientDTO client, Context applicationContext, UserCallback callback){
        ClientDAO clientD=new ClientDAO();
        clientD.updatePoints(client, applicationContext, new UserCallback(){

            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
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
