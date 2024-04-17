package mvc.model.business.penalty;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.PenaltyCallback;
import mvc.model.data.VehicleCallback;
import mvc.model.data.penalty.PenaltyDAO;
import mvc.model.data.vehicle.VehicleDAO;

public class ManagerPenalty {

    public ManagerPenalty(){

    }

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
        });
    }}
