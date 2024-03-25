package mvc.model.business.user.worker;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.client.ClientDAO;
import mvc.model.data.worker.WorkerDAO;

public class ManagerWorker {
    public ManagerWorker(){

    }
    public void checkLogInWorker(String email, String password, Context applicationContext, UserCallback callback){
        WorkerDAO userD=new WorkerDAO();
        WorkerDTO userToCheck=new WorkerDTO(null,password,null,null,null,email,null);

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

            @Override
            public void onAdminReceived(AdminDTO user) {

            }
        });
    }
}
