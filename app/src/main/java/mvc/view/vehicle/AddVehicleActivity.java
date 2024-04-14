package mvc.view.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.admin.CheckUserToAdd;
import mvc.controller.vehicle.CheckVehicleToAdd;
import mvc.view.admin.AddUserActivity;
import mvc.view.admin.AdminActivity;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{
    Button check,goMenu;
    EditText etLicencePlate,etCarType,etColor,etItvFrom,etItvTo,etDni,etInsurance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehicle);
        etLicencePlate=findViewById(R.id.editTextLicencePlate);
        etCarType=findViewById(R.id.editTextCarType);
        etColor=findViewById(R.id.editTextColor);
        etItvFrom=findViewById(R.id.editTextValidItvFrom);
        etItvTo=findViewById(R.id.editTextValidItvTo);
        check=findViewById(R.id.checkAddVehicle);
        goMenu=findViewById(R.id.goMainMenu);
        etDni=findViewById(R.id.editTextDniVehicleToAdd);
        etInsurance=findViewById(R.id.editTextIdInsurance);
        goMenu.setOnClickListener(this);
        check.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.checkAddVehicle){
            Intent checkVehicleToAdd=new Intent(AddVehicleActivity.this, CheckVehicleToAdd.class);
            checkVehicleToAdd.putExtra("licenceplate",etLicencePlate.getText().toString().trim());
            checkVehicleToAdd.putExtra("cartype",etCarType.getText().toString().trim());
            checkVehicleToAdd.putExtra("color",etColor.getText().toString().trim());
            checkVehicleToAdd.putExtra("itvfrom",etItvFrom.getText().toString().trim());
            checkVehicleToAdd.putExtra("itvto",etItvTo.getText().toString().trim());
            checkVehicleToAdd.putExtra("dni",etDni.getText().toString().trim());
            checkVehicleToAdd.putExtra("insurance",etInsurance.getText().toString().trim());
            startActivity(checkVehicleToAdd);
        }else if(v.getId()==R.id.goMainMenu){
            Intent goMenu=new Intent(AddVehicleActivity.this, AdminActivity.class);
            startActivity(goMenu);
        }
    }
}
