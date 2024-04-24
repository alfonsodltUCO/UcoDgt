package mvc.controller.client.user;

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

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.client.ClientActivity;
import mvc.view.client.user.ShowUser;

public class CheckUserToFindForClient extends AppCompatActivity {
    private ProgressBar progressBar;
    String userToFind;
    String dni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        showLoading();
        Intent intent=getIntent();
        dni=intent.getStringExtra("dni");

        if(!TextUtils.isEmpty(dni)){

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                if(userToFind.equals("client")){
                    showLoading();
                    ManagerClient mngcl=new ManagerClient();
                    ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);
                    mngcl.getUser(clientToFind, CheckUserToFindForClient.this, new UserCallback() {
                        @Override
                        public void onUserReceived(ClientDTO user) {//recibo un usuario
                            Toast.makeText(CheckUserToFindForClient.this,"Client Found", Toast.LENGTH_LONG).show();
                            Intent intentSeeUser=new Intent(CheckUserToFindForClient.this, ShowUser.class);
                            intentSeeUser.putExtra("client", user);
                            intentSeeUser.putExtra("dni",dni);
                            startActivity(intentSeeUser);
                            hideLoading();
                            finish();

                        }

                        @Override
                        public void onError(VolleyError error) {//error no encotnrado
                            Toast.makeText(CheckUserToFindForClient.this,"An error has occurred try again please", Toast.LENGTH_LONG).show();
                            Intent intentGoBack=new Intent(CheckUserToFindForClient.this, ClientActivity.class);
                            intentGoBack.putExtra("dni",dni);
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
                }
            });

        }else{
            Intent intentAdmin=new Intent(CheckUserToFindForClient.this, ClientActivity.class);
            intent.putExtra("dni",dni);

            startActivity(intentAdmin);
            Toast.makeText(CheckUserToFindForClient.this,"An error has occurred try again please", Toast.LENGTH_LONG).show();
            finish();
        }


    }

    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }


}
