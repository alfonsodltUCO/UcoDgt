package mvc.model.business.user.worker;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.worker.WorkerDAO;


/**
 * Manages operations related to worker users in the system.
 * This class provides methods to perform operations such as checking worker login credentials,
 * adding, deleting, and retrieving worker users, and checking for the existence of worker emails.
 *
 * @author Alfonso de la Torre
 */
public class ManagerWorker {

    /**
     * Default constructor for ManagerWorker.
     */
    public ManagerWorker(){

    }

    /**
     * Checks the login credentials of a worker user.
     *
     * @param worker The WorkerDTO object representing the worker user.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the login check operation.
     */
    public void checkLogInWorker(WorkerDTO worker, Context applicationContext, UserCallback callback){
        WorkerDAO userD=new WorkerDAO();
        WorkerDTO userToCheck=new WorkerDTO(null,worker.getPassword(),null,null,null, worker.getEmail(), null);

        userD.checkLogInWorker(userToCheck, applicationContext, new UserCallback() {

            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
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
     * Checks if a worker email already exists in the system.
     *
     * @param worker The WorkerDTO object representing the worker user.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the email check operation.
     */
    public void checkEmailNotExists(WorkerDTO worker, Context applicationContext, UserCallback callback){
        WorkerDAO workerD=new WorkerDAO();
        WorkerDTO userToCheck=new WorkerDTO(null,null,null,null,null, worker.getEmail(), null);
        workerD.checkWorkerEmail(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {}

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {callback.onWorkerReceived(user);}

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
     * Adds a new worker user to the system.
     *
     * @param worker The WorkerDTO object representing the new worker user.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the add operation.
     */

    public void addUser(WorkerDTO worker, Context applicationContext, UserCallback callback) {
        WorkerDAO workerD=new WorkerDAO();
        workerD.addUser(worker, applicationContext, new UserCallback(){

            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
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
     * Retrieves a worker user from the system.
     *
     * @param worker The WorkerDTO object representing the worker user to retrieve.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the retrieval operation.
     */
    public void getUser(WorkerDTO worker, Context applicationContext, UserCallback callback){
        WorkerDAO workerD=new WorkerDAO();
        workerD.getUser(worker, applicationContext, new UserCallback(){

            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
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
     * Deletes a worker user from the system.
     *
     * @param worker The WorkerDTO object representing the worker user to delete.
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the deletion operation.
     */
    public void deleteUser(WorkerDTO worker, Context applicationContext, UserCallback callback){
        WorkerDAO workerD=new WorkerDAO();
        workerD.deleteUser(worker, applicationContext, new UserCallback(){

            @Override
            public void onUserReceived(ClientDTO user) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
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
     * Retrieves all worker users from the system.
     *
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the retrieval operation.
     */
    public void getUsers(Context  applicationContext,UserCallback callback){
        WorkerDAO workD=new WorkerDAO();
        workD.getUsers(applicationContext,new UserCallback() {
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
                callback.onWorkersReceived(workers);
            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }

    /**
     * Retrieves a worker by number of worker.
     *
     * @param applicationContext The context of the application.
     * @param callback The callback to be invoked upon completion of the retrieval operation.
     */
    public void getUserByNumber(WorkerDTO worker,Context applicationContext,UserCallback callback){
        WorkerDAO workD=new WorkerDAO();
        workD.getUserByNumber(worker,applicationContext,new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
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
