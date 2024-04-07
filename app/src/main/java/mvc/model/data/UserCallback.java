package mvc.model.data;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;

public interface UserCallback {
    void onUserReceived(ClientDTO user);
    void onError(VolleyError error);
    void onWorkerReceived(WorkerDTO user);

    void onAdminReceived(AdminDTO user);

    void onWorkersReceived(List<WorkerDTO> workers);
    void onClientsReceived(List<ClientDTO> clients);

}
