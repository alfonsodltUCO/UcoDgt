package mvc.controller.worker;

import static mvc.controller.commonFunctions.ForCheckUser.checkDni;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import mvc.view.worker.user.ShowUser;
import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.worker.WorkerActivity;

/**
 * This class is responsible for checking and finding a user for the client.
 * It retrieves the user information and displays it in the ShowUser activity.
 * @author Alfonso de la torre
 */
public class CheckUserToFindForWorker extends AppCompatActivity {
    private ProgressBar progressBar;
     String dni;

     String numberWorker;

    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        showLoading();
        Intent intent=getIntent();
        dni=intent.getStringExtra("dni");
        numberWorker=getIntent().getStringExtra("numberWorker");

        if(!TextUtils.isEmpty(dni)){// Check if dni is not empty

            if(!checkDni(dni)){

                Intent intentWorker=new Intent(CheckUserToFindForWorker.this, WorkerActivity.class);
                intentWorker.putExtra("numberWorker",numberWorker);
                startActivity(intentWorker);
                Toast.makeText(CheckUserToFindForWorker.this,"The dni is invalid", Toast.LENGTH_LONG).show();
                finish();

            }else{

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {

                    showLoading();
                    ManagerClient mngcl=new ManagerClient();
                    ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);

                    mngcl.getUser(clientToFind, CheckUserToFindForWorker.this, new UserCallback() {
                        @Override
                        public void onUserReceived(ClientDTO user) {// User found, show details in ShowUser activity
                            Toast.makeText(CheckUserToFindForWorker.this,"Client Found", Toast.LENGTH_LONG).show();
                            Intent intentSeeUser=new Intent(CheckUserToFindForWorker.this, ShowUser.class);
                            intentSeeUser.putExtra("client", user);
                            intentSeeUser.putExtra("numberWorker",numberWorker);
                            startActivity(intentSeeUser);
                            hideLoading();
                            finish();

                        }

                        @Override
                        public void onError(VolleyError error) {

                            Toast.makeText(CheckUserToFindForWorker.this,"The dni doesnt exist", Toast.LENGTH_LONG).show();
                            Intent intentGoBack=new Intent(CheckUserToFindForWorker.this, WorkerActivity.class);
                            intentGoBack.putExtra("numberWorker",numberWorker);
                            startActivity(intentGoBack);
                            hideLoading();
                            finish();
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

                });
            }


        }else{// If dni is empty, return to ClientActivity

            Intent intentWorker=new Intent(CheckUserToFindForWorker.this, WorkerActivity.class);
            intentWorker.putExtra("numberWorker",numberWorker);
            startActivity(intentWorker);
            Toast.makeText(CheckUserToFindForWorker.this,"An error has occurred try again please", Toast.LENGTH_LONG).show();
            finish();
        }


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
