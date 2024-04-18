package mvc.model.business.vehicle;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.VehicleCallback;
import mvc.model.data.client.ClientDAO;
import mvc.model.data.vehicle.VehicleDAO;


public class ManagerVehicle {
    public ManagerVehicle(){

    }

    public void addVehicle(VehicleDTO vehicle,ClientDTO cl,Context applicationContext,VehicleCallback callback){
        VehicleDAO vehicleD=new VehicleDAO();
        vehicleD.addVehicle(vehicle,cl,applicationContext, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicle);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });
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

           @Override
           public void onVehiclesReceived(List<VehicleDTO> vehicles) {

           }
       });

    }

    public void getVehicle(VehicleDTO vehicle, Context applicationContext, VehicleCallback callback){
        VehicleDAO vehicleD=new VehicleDAO();
        vehicleD.getVehicle(vehicle,applicationContext, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicle);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });

    }

    public void deleteVehicle(VehicleDTO vehicle, Context applicationContext, VehicleCallback callback){
        VehicleDAO vehicleD=new VehicleDAO();
        vehicleD.deleteVehicle(vehicle, applicationContext, new VehicleCallback(){

            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                callback.onVehicleReceived(vehicle);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });
    }

    public void getVehicles(Context applicationContext,VehicleCallback callback){
        VehicleDAO vehicleD=new VehicleDAO();
        vehicleD.getVehicles(applicationContext, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehiclesReceived) {
                callback.onVehiclesReceived(vehiclesReceived);
            }
        });
    }

    public void getVehicles(ClientDTO client,Context applicationContext,VehicleCallback callback){
        VehicleDAO vehicleD=new VehicleDAO();
        vehicleD.getVehicles(client,applicationContext, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehiclesReceived) {
                callback.onVehiclesReceived(vehiclesReceived);
            }
        });
    }



}
