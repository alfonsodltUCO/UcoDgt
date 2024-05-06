package com.uco.ucodgt.mvc.view.client.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;


import com.uco.ucodgt.R;
import com.uco.ucodgt.mvc.controller.client.penalty.CheckPenaltiesToListForClient;
import com.uco.ucodgt.mvc.controller.client.vehicle.CheckVehiclesToListForClient;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.view.client.ClientActivity;

/**
 * Activity class to display details of a user/client.
 * @author Alfonso de la torre
 */
public class ShowUser extends AppCompatActivity implements View.OnClickListener {
    TextView name,surname,email,licencepoints,birth,dni,obtaining;
    String strDate,strDate2;
    String dniRec;
    ImageView image;

    String dniNoText;
    Button goMenu,listPenalties,listVehicles,goChangeData;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     * @see AppCompatActivity
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.show_user_for_client);
        dniRec=getIntent().getStringExtra("dni");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        obtaining=findViewById(com.uco.ucodgt.R.id.textViewDateObtaining);
        name=findViewById(com.uco.ucodgt.R.id.textViewFoundName);
        birth=findViewById(com.uco.ucodgt.R.id.textViewFoundDateBirth);
        surname=findViewById(com.uco.ucodgt.R.id.textViewFoundSurname);

        email=findViewById(com.uco.ucodgt.R.id.textViewFoundEmail);
        dni=findViewById(com.uco.ucodgt.R.id.textViewFoundDni);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);

        listPenalties=findViewById(com.uco.ucodgt.R.id.listPenalties);
        listVehicles=findViewById(com.uco.ucodgt.R.id.listVehicles);
        licencepoints=findViewById(com.uco.ucodgt.R.id.textViewFoundLicencePoints_numberworker);
        goChangeData=findViewById(R.id.changeData);
        image=findViewById(com.uco.ucodgt.R.id.imageShow);

        goMenu.setOnClickListener(this);

        image.setImageResource(com.uco.ucodgt.R.drawable.cliente);
        listVehicles.setOnClickListener(this);
        listPenalties.setOnClickListener(this);
        ClientDTO client = (ClientDTO) getIntent().getSerializableExtra("client");

        assert client != null;
        name.setText("name= "+client.getName());
        surname.setText("surname= "+client.getSurname());
        email.setText("email= "+client.getEmail());
        licencepoints.setText("licence points= "+client.getLicencepoints().toString());
        dni.setText("dni= "+client.getDni());
        dniNoText=client.getDni();

        strDate= formatter.format(client.getAge());
        strDate2= formatter.format(client.getDateLicenceObtaining());
        birth.setText("birth= "+strDate);
        obtaining.setText("Date obtainig licence= "+strDate2);
        goChangeData.setOnClickListener(this);

    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v){

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){
            // Navigate to main menu

            Intent goMain=new Intent(ShowUser.this, ClientActivity.class);
            goMain.putExtra("dni",dniRec);
            startActivity(goMain);
            finish();

        }else if (v.getId()==com.uco.ucodgt.R.id.listPenalties) {
            // Navigate to list of penalties for the user/client

            Intent goList=new Intent(ShowUser.this, CheckPenaltiesToListForClient.class);
            goList.putExtra("dni",dniRec);
            startActivity(goList);
            finish();

        }else if(v.getId()==com.uco.ucodgt.R.id.listVehicles){
            // Navigate to list of vehicles for the user/client

            Intent goListVeh=new Intent(ShowUser.this, CheckVehiclesToListForClient.class);
            goListVeh.putExtra("dni",dniRec);
            startActivity(goListVeh);
            finish();

        }else if(v.getId()==com.uco.ucodgt.R.id.changeData){
            // Go change client data

            Intent goChange=new Intent(ShowUser.this, IntroduceActualPassword.class);
            goChange.putExtra("dni",dniRec);
            startActivity(goChange);
            finish();

        }
    }
}
