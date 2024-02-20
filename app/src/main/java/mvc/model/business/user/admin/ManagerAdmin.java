package mvc.model.business.user.admin;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.admin.AdminDAO;
import mvc.model.data.client.ClientDAO;

public class ManagerAdmin {
    public ManagerAdmin(){

    }
    public void checkLogInAdmin(String email, String password, Context applicationContext, UserCallback callback){
        AdminDAO userD=new AdminDAO();
        AdminDTO userToCheck=new AdminDTO(null,password,null,null,null,email);
        userD.checkLogInAdmin(userToCheck, applicationContext, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("Error", "Error en el inicio de sesión: " + error.toString());
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {
                callback.onAdminReceived(user);

            }
        });
    }

}
