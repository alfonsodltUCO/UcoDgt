package mvc.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import java.text.SimpleDateFormat;

import mvc.controller.admin.CheckUserToDelete;
import mvc.controller.admin.CheckUserToFind;
import mvc.controller.penalty.CheckPenaltiesToList;
import mvc.controller.penalty.CheckPenaltyToFind;
import mvc.controller.vehicle.CheckVehiclesToList;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;


public class ShowUser extends AppCompatActivity implements View.OnClickListener {
    TextView name,surname,email,numberofworker_licencepoints,birth,dni;
    String strDate;
    String type;
    String dniNoText;
    Button goMenu,deleteUser,listPenalties,listVehicles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user);
        Intent intentFound=getIntent();
        type=intentFound.getStringExtra("type");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        name=findViewById(R.id.textViewFoundName);
        birth=findViewById(R.id.textViewFoundDateBirth);
        surname=findViewById(R.id.textViewFoundSurname);
        email=findViewById(R.id.textViewFoundEmail);
        dni=findViewById(R.id.textViewFoundDni);
        goMenu=findViewById(R.id.goMainMenu);
        deleteUser=findViewById(R.id.deleteUser);
        listPenalties=findViewById(R.id.listPenalties);
        listVehicles=findViewById(R.id.listVehicles);
        numberofworker_licencepoints=findViewById(R.id.textViewFoundLicencePoints_numberworker);
        goMenu.setOnClickListener(this);
        deleteUser.setOnClickListener(this);
        listVehicles.setOnClickListener(this);
        listPenalties.setOnClickListener(this);

        if(type.equals("worker")){

            WorkerDTO worker = (WorkerDTO) getIntent().getSerializableExtra("worker");
            name.setText("name= "+worker.getName());
            surname.setText("surname= "+worker.getSurname());
            email.setText("email= "+worker.getEmail());
            numberofworker_licencepoints.setText("worker number= "+worker.getNumberOfWorker().toString());
            dni.setText("dni= "+worker.getDni());
            dniNoText=worker.getDni();
            strDate= formatter.format(worker.getAge());
            birth.setText("birth= "+strDate);

        }else{

            ClientDTO client = (ClientDTO) getIntent().getSerializableExtra("client");
            name.setText("name= "+client.getName());
            surname.setText("surname= "+client.getSurname());
            email.setText("email= "+client.getEmail());
            numberofworker_licencepoints.setText("licence points= "+client.getLicencepoints().toString());
            dni.setText("dni= "+client.getDni());
            dniNoText=client.getDni();
            strDate= formatter.format(client.getAge());
            birth.setText("birth= "+strDate);
        }
    }
    public void onClick(View v){
        if(v.getId()==R.id.goMainMenu){
            Intent checkUserToAdd=new Intent(ShowUser.this, AdminActivity.class);
            startActivity(checkUserToAdd);
        }else if(v.getId()==R.id.deleteUser){
            Intent checkUserToDelete=new Intent(ShowUser.this, CheckUserToDelete.class);
            checkUserToDelete.putExtra("dni",dniNoText);
            checkUserToDelete.putExtra("type",type);
            startActivity(checkUserToDelete);
        } else if (v.getId()==R.id.listPenalties) {
            Intent goList=new Intent(ShowUser.this, CheckPenaltiesToList.class);
            goList.putExtra("dni",dniNoText);
            startActivity(goList);
        }else if(v.getId()==R.id.listVehicles){
            Intent goListVeh=new Intent(ShowUser.this, CheckVehiclesToList.class);
            goListVeh.putExtra("dni",dniNoText);
            startActivity(goListVeh);
        }
    }
}
