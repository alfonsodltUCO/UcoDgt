package mvc.controller.worker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.UserCallback;
import mvc.view.worker.user.ShowUser;

public class CheckUserToSee extends AppCompatActivity {


    ProgressBar progressBar;

    String numberWorker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();

        numberWorker=getIntent().getStringExtra("numberWorker");

        String lplate=getIntent().getStringExtra("licencePlate");

        ManagerClient mngC=new ManagerClient();
        VehicleDTO vh=new VehicleDTO();
        vh.setLicencePlate(lplate);

        mngC.getOwner(vh,CheckUserToSee.this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                Intent goShow=new Intent(CheckUserToSee.this, ShowUser.class);
                goShow.putExtra("numberWorker",numberWorker);
                goShow.putExtra("client",user);
                startActivity(goShow);
                finish();
                hideLoading();
            }

            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
    }

    /**
     * Shows the loading progress bar.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    /**
     * Hides the loading progress bar.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

}
