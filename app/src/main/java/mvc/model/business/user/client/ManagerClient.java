package mvc.model.business.user.client;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.client.ClientDAO;

public class ManagerClient {
    public ManagerClient(){

    }

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
                Log.e("Error", "Error en el inicio de sesión: " + error.toString());
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
                Log.e("Error", "Error en el inicio de sesión: " + error.toString());
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
