package mvc.model.data;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.business.vehicle.VehicleDTO;

public interface VehicleCallback {

    void onVehicleReceived(VehicleDTO vehicle);

    void onError(VolleyError error);

    void onVehiclesReceived(List<VehicleDTO> vehicles);

}
