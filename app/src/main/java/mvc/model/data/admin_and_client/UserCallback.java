package mvc.model.data.admin_and_client;

import com.android.volley.VolleyError;

import mvc.model.business.user.admin_and_client.UserDTO;
import mvc.model.business.user.worker.WorkerDTO;

public interface UserCallback {
    void onUserReceived(UserDTO user);
    void onError(VolleyError error);
    void onWorkerReceived(WorkerDTO user);
}
