package com.uco.ucodgt.mvc.view.admin.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltyToDelete;
import com.uco.ucodgt.mvc.controller.admin.users.CheckUserToDelete;
import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltiesToList;
import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckVehiclesToList;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.admin.penalty.IntroducePoints;
import com.uco.ucodgt.mvc.view.admin.penalty.ShowPenalty;

/**
 * Activity for displaying user details and providing options to perform actions related to the user.
 * @author Alfonso d la torre
 */
public class ShowUser extends AppCompatActivity implements View.OnClickListener {
    TextView name,surname,email,numberofworker_licencepoints,birth,dni,obtaining;
    String strDate,strDate2;
    String type;
    ImageView image;
    String dniNoText;
    Button goMenu,deleteUser,listPenalties,listVehicles,updatePoints;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.show_user);

        // Retrieve the user type from the intent
        Intent intentFound=getIntent();
        type=intentFound.getStringExtra("type");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        obtaining=findViewById(com.uco.ucodgt.R.id.textViewDateObtaining);
        name=findViewById(com.uco.ucodgt.R.id.textViewFoundName);
        birth=findViewById(com.uco.ucodgt.R.id.textViewFoundDateBirth);
        surname=findViewById(com.uco.ucodgt.R.id.textViewFoundSurname);
        email=findViewById(com.uco.ucodgt.R.id.textViewFoundEmail);
        dni=findViewById(com.uco.ucodgt.R.id.textViewFoundDni);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        deleteUser=findViewById(com.uco.ucodgt.R.id.deleteUser);
        listPenalties=findViewById(com.uco.ucodgt.R.id.listPenalties);
        listVehicles=findViewById(com.uco.ucodgt.R.id.listVehicles);
        numberofworker_licencepoints=findViewById(com.uco.ucodgt.R.id.textViewFoundLicencePoints_numberworker);
        updatePoints=findViewById(com.uco.ucodgt.R.id.updatePoints);
        image=findViewById(com.uco.ucodgt.R.id.imageShow);

        updatePoints.setOnClickListener(this);
        goMenu.setOnClickListener(this);
        deleteUser.setOnClickListener(this);
        listVehicles.setOnClickListener(this);
        listPenalties.setOnClickListener(this);

        // Check the type of user and set the TextViews accordingly
        if(type.equals("worker")){

            WorkerDTO worker = (WorkerDTO) getIntent().getSerializableExtra("worker");
            assert worker != null;
            name.setText("name= "+worker.getName());
            surname.setText("surname= "+worker.getSurname());
            email.setText("email= "+worker.getEmail());
            obtaining.setText("");
            numberofworker_licencepoints.setText("worker number= "+worker.getNumberOfWorker().toString());
            dni.setText("dni= "+worker.getDni());
            dniNoText=worker.getDni();
            image.setImageResource(com.uco.ucodgt.R.drawable.trabajador);
            strDate= formatter.format(worker.getAge());
            birth.setText("birth= "+strDate);

        }else{

            ClientDTO client = (ClientDTO) getIntent().getSerializableExtra("client");

            assert client != null;
            name.setText("name= "+client.getName());
            surname.setText("surname= "+client.getSurname());
            email.setText("email= "+client.getEmail());
            numberofworker_licencepoints.setText("licence points= "+client.getLicencepoints().toString());
            image.setImageResource(com.uco.ucodgt.R.drawable.userformenu);

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

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){

            Intent showUser=new Intent(ShowUser.this, AdminActivity.class);
            startActivity(showUser);
            finish();

        }else if(v.getId()==com.uco.ucodgt.R.id.deleteUser){
            showConfirmationDialog();


        } else if (v.getId()==com.uco.ucodgt.R.id.listPenalties) {

            if(type.equals("worker")){

                Intent goBack=new Intent(ShowUser.this, AdminActivity.class);
                startActivity(goBack);
                Toast.makeText(ShowUser.this,"Cannot list penalties of a worker",Toast.LENGTH_LONG).show();
                finish();
            }else{

                Intent goList=new Intent(ShowUser.this, CheckPenaltiesToList.class);
                goList.putExtra("dni",dniNoText);
                startActivity(goList);
                finish();
            }



        }else if(v.getId()==com.uco.ucodgt.R.id.listVehicles){

            if(type.equals("worker")){

                Intent goBack=new Intent(ShowUser.this, AdminActivity.class);
                startActivity(goBack);
                Toast.makeText(ShowUser.this,"Cannot list vehicles of a worker",Toast.LENGTH_LONG).show();
                finish();
            }else{

                Intent goListVeh=new Intent(ShowUser.this, CheckVehiclesToList.class);
                goListVeh.putExtra("dni",dniNoText);
                startActivity(goListVeh);
                finish();
            }


        }else if(v.getId()==com.uco.ucodgt.R.id.updatePoints){

            if(type.equals("worker")){

                Intent goBack=new Intent(ShowUser.this, AdminActivity.class);
                startActivity(goBack);
                Toast.makeText(ShowUser.this,"Cannot update points from a worker",Toast.LENGTH_LONG).show();
                finish();
            }else{

                Intent goUpdate=new Intent(ShowUser.this, IntroducePoints.class);
                goUpdate.putExtra("dni",dniNoText);
                startActivity(goUpdate);
                finish();
            }
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(com.uco.ucodgt.R.layout.confirm_activity, null);
        builder.setView(dialogView);

        TextView textConfirmation = dialogView.findViewById(com.uco.ucodgt.R.id.text_confirmation);
        Button btnConfirm = dialogView.findViewById(com.uco.ucodgt.R.id.btn_confirm);
        Button btnCancel = dialogView.findViewById(com.uco.ucodgt.R.id.btn_cancel);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnConfirm.setOnClickListener(v -> {

            Intent checkUserToDelete=new Intent(ShowUser.this, CheckUserToDelete.class);
            checkUserToDelete.putExtra("dni",dniNoText);
            checkUserToDelete.putExtra("type",type);
            startActivity(checkUserToDelete);
            finish();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {


            dialog.dismiss();

        });
    }
}
