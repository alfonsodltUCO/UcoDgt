package mvc.model.business.user.admin;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.admin.AdminDAO;


public class ManagerAdmin {
    public ManagerAdmin(){

    }
    public void checkLogInAdmin(AdminDTO admin, Context applicationContext, UserCallback callback){
        AdminDAO userD=new AdminDAO();
        AdminDTO userToCheck=new AdminDTO(null,admin.getPassword(),null,null,null,admin.getPassword());
        userD.checkLogInAdmin(userToCheck, applicationContext, new UserCallback() {
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

            }

            @Override
            public void onAdminReceived(AdminDTO user) {
                callback.onAdminReceived(user);

            }
        });
    }

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
