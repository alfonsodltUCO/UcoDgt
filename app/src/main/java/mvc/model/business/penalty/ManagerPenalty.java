package mvc.model.business.penalty;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.PenaltyCallback;
 import mvc.model.data.penalty.PenaltyDAO;

/**
 * This class is responsible for managing penalty-related operations.
 * It interacts with the data access object (DAO) to perform various operations such as retrieving penalties,
 * adding penalties, deleting penalties, performing payments, getting the last penalty for a client or vehicle...
 * @author Alfonso de la torre
 */

public class ManagerPenalty {

    /**
     * Constructs a new ManagerPenalty object.
     */

    public ManagerPenalty(){

    }

    /**
     * Retrieves a specific penalty.
     *
     * @param penalty           The penalty DTO to retrieve.
     * @param applicationContext The context of the application.
     * @param callback          The callback to handle the result of the operation.
     */

    public  void getPenalty(PenaltyDTO penalty,Context applicationContext,PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getPenalty(penalty,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                callback.onPenaltyReceived(penalty);
            }
        });
    }

    /**
     * Retrieves penalties associated with a specific vehicle.
     *
     * @param vh                 The vehicle DTO to retrieve penalties for.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */

    public void getPenalties(VehicleDTO vh,Context applicationContext, PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getPenalties(vh,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }

    /**
     * Retrieves penalties associated with a specific client.
     *
     * @param cl                 The client DTO to retrieve penalties for.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */
    public void getPenalties(ClientDTO cl, Context applicationContext, PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getPenalties(cl,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }

    /**
     * Retrieves all penalties.
     *
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */

    public void getPenalties(Context applicationContext, PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getPenalties(applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }

    /**
     * Retrieves penalties within a specific date range.
     *
     * @param date1              The start date of the range.
     * @param date2              The end date of the range.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */


    public void getPenalties(String date1, String date2,Context applicationContext, PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getPenalties(date1,date2,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }

    /**
     * Retrieves penalties within a specific date range and matching a given penalty DTO.
     *
     * @param date1              The start date of the range.
     * @param date2              The end date of the range.
     * @param penalty            The penalty DTO to match.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */

    public void getPenalties(String date1, String date2,PenaltyDTO penalty,Context applicationContext, PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getPenalties(date1,date2,penalty,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }

    /**
     * Retrieves penalties with a specific state.
     *
     * @param state              The state of the penalties to retrieve.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */


    public void getPenalties(String state,Context applicationContext, PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getPenalties(state,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }

    /**
     * Deletes a specific penalty.
     *
     * @param penaltyToSend      The penalty DTO to delete.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */

    public void deletePenalty(PenaltyDTO penaltyToSend,Context applicationContext,PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.deletePenalty(penaltyToSend,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                callback.onPenaltyReceived(penalty);
            }
        });
    }

    /**
     * Adds a new penalty.
     *
     * @param penaltyToSend      The penalty DTO to add.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */

    public void addPenalty(PenaltyDTO penaltyToSend,Context applicationContext,PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.addPenalty(penaltyToSend,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                callback.onPenaltyReceived(penalty);
            }
        });
    }

    /**
     * Performs a payment for a penalty.
     *
     * @param penaltyToSend      The penalty DTO for which payment is to be done.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */


    public void doPayment(PenaltyDTO penaltyToSend,Context applicationContext,PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.doPayment(penaltyToSend,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                callback.onPenaltyReceived(penalty);
            }
        });
    }

    /**
     * Retrieves penalties matching a given penalty DTO.
     *
     * @param penalty            The penalty DTO to match.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */


    public void getPenalties(PenaltyDTO penalty,Context applicationContext, PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getPenalties(penalty,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
                callback.onPenaltiesReceived(penalties);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

            }
        });
    }

    /**
     * Retrieves the last penalty of a client.
     *
     * @param penalty            The penalty DTO to retrieve the last penalty for.
     * @param applicationContext The context of the application.
     * @param callback           The callback to handle the result of the operation.
     */

    public void getLastPenalty(PenaltyDTO penalty,Context applicationContext, PenaltyCallback callback){
        PenaltyDAO penaltyD=new PenaltyDAO();
        penaltyD.getLastPenalty(penalty,applicationContext, new PenaltyCallback() {

            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                callback.onPenaltyReceived(penalty);
            }
        });
    }
}
