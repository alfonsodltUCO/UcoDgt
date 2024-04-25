package mvc.controller.admin.users;

import static mvc.controller.commonFunctions.ForCheckPenalty.checkNumeric;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.admin.AdminActivity;


/**
 * Activity class to update the points of a client.
 * @author Alfonso de la torre
 */
public class CheckPointsToUpdate extends AppCompatActivity {
    private ProgressBar progressBar;
    String dni,points;


    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        dni=getIntent().getStringExtra("dni");
        points=getIntent().getStringExtra("points");

        if(!checkNumeric(points)){

            Intent goMain=new Intent(CheckPointsToUpdate.this, AdminActivity.class);
            Toast.makeText(CheckPointsToUpdate.this,"Points incorrect try again please", Toast.LENGTH_LONG).show();
            startActivity(goMain);
            finish();
            hideLoading();

        }else{
            if(Integer.parseInt(points)>15 || Integer.parseInt(points)<=0){

                Intent goMain=new Intent(CheckPointsToUpdate.this, AdminActivity.class);
                Toast.makeText(CheckPointsToUpdate.this,"Points must be between [1,15]", Toast.LENGTH_LONG).show();
                startActivity(goMain);
                finish();
                hideLoading();

            }else{

                ManagerClient mngC=new ManagerClient();
                ClientDTO cl=new ClientDTO(dni,null,null,null,null,null,Integer.parseInt(points));
                mngC.updatePoints(cl, CheckPointsToUpdate.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {

                        Intent intent=new Intent(CheckPointsToUpdate.this, AdminActivity.class);
                        Toast.makeText(CheckPointsToUpdate.this,"Update done", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onError(VolleyError error) {

                        Intent goMain=new Intent(CheckPointsToUpdate.this, AdminActivity.class);
                        Toast.makeText(CheckPointsToUpdate.this,"An error has occurred try again please", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        finish();
                        hideLoading();

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


        }
    }


    /**
     * Show loading progress bar.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    /**
     * Hide loading progress bar.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
