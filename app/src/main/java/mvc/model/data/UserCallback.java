package mvc.model.data;

import com.android.volley.VolleyError;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;

public interface UserCallback {
    void onUserReceived(ClientDTO user);
    void onError(VolleyError error);
    void onWorkerReceived(WorkerDTO user);

    void onAdminReceived(AdminDTO user);

}
