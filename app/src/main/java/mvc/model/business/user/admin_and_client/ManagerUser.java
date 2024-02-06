package mvc.model.business.user.admin_and_client;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.admin_and_client.UserCallback;
import mvc.model.data.admin_and_client.UserDAO;

public class ManagerUser {
    public ManagerUser(){

    }

    public void checkLogInClient(String email, String password, Context applicationContext,UserCallback callback){
        UserDAO userD=new UserDAO();
        UserDTO userToCheck=new UserDTO(null,password,null,null,null,email,null);
        userD.checkLogInClient(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(UserDTO user) {
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
        UserDAO userD=new UserDAO();
        UserDTO userToCheck=new UserDTO(null,password,null,null,null,email,null);
        userD.checkLogInAdmin(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(UserDTO user) {
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
        UserDAO userD=new UserDAO();
        WorkerDTO userToCheck=new WorkerDTO(null,password,null,null,null,email,null,null,null);
        userD.checkLogInWorker(userToCheck, applicationContext, new UserCallback() {

            @Override
            public void onUserReceived(UserDTO user) {

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
