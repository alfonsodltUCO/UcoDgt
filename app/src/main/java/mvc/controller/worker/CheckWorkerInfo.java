package mvc.controller.worker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.worker.WorkerActivity;
import mvc.view.worker.user.ShowWorker;

public class CheckWorkerInfo extends AppCompatActivity {

    String numberWorker;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();

        numberWorker=getIntent().getStringExtra("numberWorker");

        ManagerWorker mngW=new ManagerWorker();
        WorkerDTO wrk=new WorkerDTO(null,null,null,null,null,null,Integer.parseInt(numberWorker));


        mngW.getUserByNumber(wrk, CheckWorkerInfo.this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {
                Log.e("e",error.getMessage().toString());
                Intent intent=new Intent(CheckWorkerInfo.this, WorkerActivity.class);
                intent.putExtra("numberWorker",numberWorker);
                Toast.makeText(CheckWorkerInfo.this,"An error has occured try again please", Toast.LENGTH_LONG).show();
                startActivity(intent);
                hideLoading();
                finish();
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

                Intent intent=new Intent(CheckWorkerInfo.this, ShowWorker.class);
                intent.putExtra("numberWorker",numberWorker);
                intent.putExtra("worker",user);
                startActivity(intent);
                hideLoading();
                finish();
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
