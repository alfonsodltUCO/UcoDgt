package mvc.model.business.vehicle;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.user.client.ClientDTO;
import mvc.model.data.VehicleCallback;
import mvc.model.data.vehicle.VehicleDAO;

/**
 * Manager class for handling vehicle-related operations.
 * @author Alfonso de la torre
 */
public class ManagerVehicle {
    /**
     * Constructor for ManagerVehicle.
     */
    public ManagerVehicle() {
    }

    /**
     * Adds a new vehicle for a specific client.
     *
     * @param vehicle            The VehicleDTO representing the vehicle to add.
     * @param cl             The ClientDTO representing the client.
     * @param applicationContext The context of the application.
     * @param callback           The callback for handling the result of the operation.
     */
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

    /**
     * Checks the details of a vehicle from an image.
     *
     * @param image               The Bitmap image of the vehicle.
     * @param applicationContext The context of the application.
     * @param callback            The callback for handling the result of the operation.
     */
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

    /**
     * Retrieves details of a specific vehicle.
     *
     * @param vehicle             The VehicleDTO representing the vehicle to retrieve.
     * @param applicationContext The context of the application.
     * @param callback            The callback for handling the result of the operation.
     */
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

    /**
     * Deletes a specific vehicle.
     *
     * @param vehicle             The VehicleDTO representing the vehicle to delete.
     * @param applicationContext The context of the application.
     * @param callback            The callback for handling the result of the operation.
     */
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

    /**
     * Retrieves all vehicles.
     *
     * @param applicationContext The context of the application.
     * @param callback            The callback for handling the result of the operation.
     */
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

    /**
     * Retrieves vehicles associated with a specific client.
     *
     * @param client              The ClientDTO representing the client.
     * @param applicationContext The context of the application.
     * @param callback            The callback for handling the result of the operation.
     */
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
