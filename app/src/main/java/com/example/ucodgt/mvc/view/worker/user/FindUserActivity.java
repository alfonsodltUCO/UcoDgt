package com.example.ucodgt.mvc.view.worker.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


 import com.example.ucodgt.mvc.controller.worker.user.CheckUserToFindForWorker;
 import com.example.ucodgt.mvc.view.worker.WorkerActivity;

/**
 * Activity for finding a user based on criteria.
 * @author Alfonso de la torre
 */
public class FindUserActivity extends AppCompatActivity implements View.OnClickListener {
    EditText dniToSearch;

    String numberWorker;
    Button search,goMenu;
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
        setContentView(com.example.ucodgt.R.layout.find_user_for_worker);
        search=findViewById(com.example.ucodgt.R.id.findUser);
        numberWorker=getIntent().getStringExtra("numberWorker");
        dniToSearch = findViewById(com.example.ucodgt.R.id.editTextDniToFind);
        goMenu=findViewById(com.example.ucodgt.R.id.goMainMenu);

        search.setOnClickListener(this);
        goMenu.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.example.ucodgt.R.id.findUser){
            // Start the CheckUserToFind activity to find the user

            Intent goFind=new Intent(FindUserActivity.this, CheckUserToFindForWorker.class);
            goFind.putExtra("dni",dniToSearch.getText().toString().trim());
            goFind.putExtra("numberWorker",numberWorker);
            startActivity(goFind);
            finish();

        }else if(v.getId()==com.example.ucodgt.R.id.goMainMenu){
            // Navigate back to the AdminActivity

            Intent goMenu=new Intent(FindUserActivity.this, WorkerActivity.class);
            goMenu.putExtra("numberWorker",numberWorker);
            startActivity(goMenu);
            finish();
        }
    }
}
