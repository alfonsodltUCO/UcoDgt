package mvc.model.data;

import com.android.volley.VolleyError;

import java.util.List;

import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.user.client.ClientDTO;

public interface PenaltyCallback {
    void onPenaltiesReceived(List<PenaltyDTO> penalties);
    void onError(VolleyError error);
}
