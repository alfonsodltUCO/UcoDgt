package com.example.ucodgt.mvc.controller.admin.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.io.Serializable;
import java.util.List;

import com.example.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.example.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.example.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.example.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.example.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.example.ucodgt.mvc.model.data.UserCallback;
import com.example.ucodgt.mvc.view.admin.AdminActivity;
import com.example.ucodgt.mvc.view.admin.user.ShowUsers;
/**
 * An activity to check users and display them.
 * @author Alfonso de la torre
 */
public class CheckUsersToList extends AppCompatActivity {

    ProgressBar progressBar;
    List<WorkerDTO> listWorkers;
    List<ClientDTO> listClients;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        ManagerWorker mngwrk=new ManagerWorker();
        showLoading();

        mngwrk.getUsers(CheckUsersToList.this,new UserCallback() {
            @Override
            public void onWorkersReceived(List<WorkerDTO> workers){
                listWorkers=workers;

                runOnUiThread(() -> {

                    ManagerClient mngCl=new ManagerClient();
                    mngCl.getUsers(CheckUsersToList.this, new UserCallback() {

                        @Override
                        public void onUserReceived(ClientDTO user) {

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

                            listClients=clients;
                            runOnUiThread(()->{

                                if(listClients.isEmpty() && listWorkers.isEmpty()){

                                    Intent emptyLists = new Intent(CheckUsersToList.this, AdminActivity.class);
                                    startActivity(emptyLists);
                                    Toast.makeText(CheckUsersToList.this,"Not found any user",Toast.LENGTH_LONG).show();
                                    hideLoading();
                                    finish();

                                }else{

                                    Intent notEmptyLists = new Intent(CheckUsersToList.this, ShowUsers.class);
                                    notEmptyLists.putExtra("workers", (Serializable) listWorkers);
                                    notEmptyLists.putExtra("clients",(Serializable) listClients);
                                    Toast.makeText(CheckUsersToList.this,"found users",Toast.LENGTH_LONG).show();
                                    startActivity(notEmptyLists);
                                    hideLoading();
                                    finish();

                                }
                            });
                        }
                    });
                });
            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }

            @Override
            public void onUserReceived(ClientDTO user) {

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
