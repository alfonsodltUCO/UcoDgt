package mvc.view.client.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import java.text.SimpleDateFormat;


import mvc.controller.client.penalty.CheckPenaltiesToListForClient;
import mvc.controller.client.vehicle.CheckVehiclesToListForClient;
import mvc.model.business.user.client.ClientDTO;
import mvc.view.client.ClientActivity;


public class ShowUser extends AppCompatActivity implements View.OnClickListener {
    TextView name,surname,email,licencepoints,birth,dni,obtaining;
    String strDate,strDate2;
    String type;
    String dniRec;
    String dniNoText;
    Button goMenu,deleteUser,listPenalties,listVehicles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user_for_client);
        dniRec=getIntent().getStringExtra("dni");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        obtaining=findViewById(R.id.textViewDateObtaining);
        name=findViewById(R.id.textViewFoundName);
        birth=findViewById(R.id.textViewFoundDateBirth);
        surname=findViewById(R.id.textViewFoundSurname);
        email=findViewById(R.id.textViewFoundEmail);
        dni=findViewById(R.id.textViewFoundDni);
        goMenu=findViewById(R.id.goMainMenu);
        deleteUser=findViewById(R.id.deleteUser);
        listPenalties=findViewById(R.id.listPenalties);
        listVehicles=findViewById(R.id.listVehicles);
        licencepoints=findViewById(R.id.textViewFoundLicencePoints_numberworker);
        goMenu.setOnClickListener(this);
        deleteUser.setOnClickListener(this);
        listVehicles.setOnClickListener(this);
        listPenalties.setOnClickListener(this);
        ClientDTO client = (ClientDTO) getIntent().getSerializableExtra("client");

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

    }
    public void onClick(View v){
        if(v.getId()==R.id.goMainMenu){
            Intent goMain=new Intent(ShowUser.this, ClientActivity.class);
            startActivity(goMain);
            finish();
        }else if (v.getId()==R.id.listPenalties) {
            Intent goList=new Intent(ShowUser.this, CheckPenaltiesToListForClient.class);
            goList.putExtra("dni",dniRec);
            startActivity(goList);
            finish();

        }else if(v.getId()==R.id.listVehicles){
            Intent goListVeh=new Intent(ShowUser.this, CheckVehiclesToListForClient.class);
            goListVeh.putExtra("dni",dniRec);
            startActivity(goListVeh);
            finish();

        }
    }
}
