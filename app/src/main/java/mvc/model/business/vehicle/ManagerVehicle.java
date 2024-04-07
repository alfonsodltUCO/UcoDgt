package mvc.model.business.vehicle;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;

import mvc.model.data.VehicleCallback;
import mvc.model.data.vehicle.VehicleDAO;


public class ManagerVehicle {
    public ManagerVehicle(){

    }
    public void checkVehicle(Bitmap image, Context applicationContext, VehicleCallback callback){
       VehicleDAO vehicleD=new VehicleDAO();
       vehicleD.checkVehicle(image,applicationContext, new VehicleCallback() {
           @Override
           public void onVehicleReceived(VehicleDTO vehicle) {
               callback.onVehicleReceived(vehicle);
           }

           @Override
           public void onError(VolleyError error) {
                callback.onError(error);
           }
       });

    }


}
