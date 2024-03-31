package mvc.model.business.user.worker;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.worker.WorkerDAO;

public class ManagerWorker {
    public ManagerWorker(){

    }
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
                Log.e("Error", "Error en el inicio de sesión: " + error.toString());
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }
        });
    }

    public void checkEmailNotExists(WorkerDTO worker, Context applicationContext, UserCallback callback){
        WorkerDAO workerD=new WorkerDAO();
        WorkerDTO userToCheck=new WorkerDTO(null,null,null,null,null, worker.getEmail(), null);
        workerD.checkWorkerEmail(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {}

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);

                Log.e("Error", "Error en el inicio de sesión: " + error.toString());
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {callback.onWorkerReceived(user);}

            @Override
            public void onAdminReceived(AdminDTO user) {}
        });
    }

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
        });
    }
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
        });
    }

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
        });
    }
}
