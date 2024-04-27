package com.example.ucodgt.mvc.view.worker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.ucodgt.mvc.controller.worker.user.CheckWorkerInfo;
import com.example.ucodgt.mvc.view.worker.penalty.AddPenaltyActivity;
import com.example.ucodgt.mvc.view.MainActivity;
import com.example.ucodgt.mvc.view.worker.user.FindUserActivity;
import com.example.ucodgt.mvc.view.worker.vehicle.GetVehiclePlate;

/**
 * This class represents the activity for managing worker options.
 * @author Alfonso de la torre
 */
public class WorkerActivity extends AppCompatActivity implements View.OnClickListener{

    Button closeSession;
    String numberWorker;

    /**
     * Method used to handle the view creation
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * Method used to create the menu of the worker
     */
    @SuppressLint("MissingInflatedId")
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(com.example.ucodgt.R.menu.workermenu,menu);
        setContentView(com.example.ucodgt.R.layout.workermain);
        closeSession=findViewById(com.example.ucodgt.R.id.closeSession);
        closeSession.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Method used to handle the worker menu creation
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        numberWorker=getIntent().getStringExtra("numberWorker");
        if(item.getItemId()==com.example.ucodgt.R.id.item1WorkerAddPenalty){

            Intent intent=new Intent(WorkerActivity.this,AddPenaltyActivity.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            finish();

        }else if(item.getItemId()==com.example.ucodgt.R.id.item2WorkerCheckVehicle){

            Intent intent=new Intent(WorkerActivity.this, GetVehiclePlate.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            finish();


        }else if(item.getItemId()==com.example.ucodgt.R.id.item3WorkerCheckUser){

            Intent intent=new Intent(WorkerActivity.this, FindUserActivity.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            finish();

        }else if(item.getItemId()==com.example.ucodgt.R.id.item5WorkerGetMyInfo){

            Intent intent=new Intent(WorkerActivity.this, CheckWorkerInfo.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            finish();

        } else if (item.getItemId()==com.example.ucodgt.R.id.item4WorkerCancelPenalty) {

            Intent intent=new Intent(WorkerActivity.this, GetVehiclePlate.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            finish();
        }
        return false;
    }

    /**
     * Method used to handle the event of button clicked
     */
    public void onClick(View v) {

        if(v.getId()==com.example.ucodgt.R.id.closeSession){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
