package mvc.model.business.user.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.client.ClientDAO;

public class ManagerUserLogIn {
    public ManagerUserLogIn(){

    }

    public void checkLogInClient(String email, String password, Context applicationContext,UserCallback callback){
        ClientDAO userD=new ClientDAO();
        ClientDTO userToCheck=new ClientDTO(null,password,null,null,null,email,null);
        userD.checkLogInClient(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Error", "Error en el inicio de sesión: " + error.toString());
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }
        });
    }
    public void checkLogInAdmin(String email, String password, Context applicationContext,UserCallback callback){
        ClientDAO userD=new ClientDAO();
        ClientDTO userToCheck=new ClientDTO(null,password,null,null,null,email,null);
        userD.checkLogInAdmin(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                callback.onUserReceived(user);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Error", "Error en el inicio de sesión: " + error.toString());
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }
        });
    }
    public void checkLogInWorker(String email, String password, Context applicationContext,UserCallback callback){
        ClientDAO userD=new ClientDAO();
        WorkerDTO userToCheck=new WorkerDTO(null,password,null,null,null,email,null,null);
        userD.checkLogInWorker(userToCheck, applicationContext, new UserCallback() {

            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Error", "Error en el inicio de sesión: " + error.toString());
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                callback.onWorkerReceived(user);
            }
        });
    }
}
