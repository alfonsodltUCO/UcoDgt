package mvc.view.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import java.text.SimpleDateFormat;

import mvc.controller.admin.users.CheckUserToDelete;
import mvc.controller.admin.penalty.CheckPenaltiesToList;
import mvc.controller.admin.vehicle.CheckVehiclesToList;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.view.admin.AdminActivity;

/**
 * Activity for displaying user details and providing options to perform actions related to the user.
 * @author Alfonso d la torre
 */
public class ShowUser extends AppCompatActivity implements View.OnClickListener {
    TextView name,surname,email,numberofworker_licencepoints,birth,dni,obtaining;
    String strDate,strDate2;
    String type;
    String dniNoText;
    Button goMenu,deleteUser,listPenalties,listVehicles;
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
        setContentView(R.layout.show_user);

        // Retrieve the user type from the intent
        Intent intentFound=getIntent();
        type=intentFound.getStringExtra("type");
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
        numberofworker_licencepoints=findViewById(R.id.textViewFoundLicencePoints_numberworker);

        goMenu.setOnClickListener(this);
        deleteUser.setOnClickListener(this);
        listVehicles.setOnClickListener(this);
        listPenalties.setOnClickListener(this);

        // Check the type of user and set the TextViews accordingly
        if(type.equals("worker")){

            WorkerDTO worker = (WorkerDTO) getIntent().getSerializableExtra("worker");
            name.setText("name= "+worker.getName());
            surname.setText("surname= "+worker.getSurname());
            email.setText("email= "+worker.getEmail());
            obtaining.setText("");
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
            strDate2= formatter.format(client.getDateLicenceObtaining());
            birth.setText("birth= "+strDate);
            obtaining.setText("Date obtainig licence= "+strDate2);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v){

        if(v.getId()==R.id.goMainMenu){

            Intent showUser=new Intent(ShowUser.this, AdminActivity.class);
            startActivity(showUser);
            finish();

        }else if(v.getId()==R.id.deleteUser){

            Intent checkUserToDelete=new Intent(ShowUser.this, CheckUserToDelete.class);
            checkUserToDelete.putExtra("dni",dniNoText);
            checkUserToDelete.putExtra("type",type);
            startActivity(checkUserToDelete);
            finish();

        } else if (v.getId()==R.id.listPenalties) {

            Intent goList=new Intent(ShowUser.this, CheckPenaltiesToList.class);
            goList.putExtra("dni",dniNoText);
            startActivity(goList);
            finish();

        }else if(v.getId()==R.id.listVehicles){

            Intent goListVeh=new Intent(ShowUser.this, CheckVehiclesToList.class);
            goListVeh.putExtra("dni",dniNoText);
            startActivity(goListVeh);
            finish();

        }
    }
}
