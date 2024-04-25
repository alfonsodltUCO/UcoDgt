package mvc.view.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.admin.vehicle.CheckVehicleToAdd;
import mvc.view.admin.AdminActivity;
/**
 * Activity for adding a new vehicle.
 * @author Alfonso de la torre
 */
public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{
    Button check,goMenu;
    EditText etLicencePlate,etCarType,etColor,etItvFrom,etItvTo,etDni,etInsurance;
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
            // Prepare data to be sent to the controller

            Intent checkVehicleToAdd=new Intent(AddVehicleActivity.this, CheckVehicleToAdd.class);
            checkVehicleToAdd.putExtra("licenceplate",etLicencePlate.getText().toString().trim());
            checkVehicleToAdd.putExtra("cartype",etCarType.getText().toString().trim());
            checkVehicleToAdd.putExtra("color",etColor.getText().toString().trim());
            checkVehicleToAdd.putExtra("itvfrom",etItvFrom.getText().toString().trim());
            checkVehicleToAdd.putExtra("itvto",etItvTo.getText().toString().trim());
            checkVehicleToAdd.putExtra("dni",etDni.getText().toString().trim());
            checkVehicleToAdd.putExtra("insurance",etInsurance.getText().toString().trim());
            // Start the CheckVehicleToAdd activity

            startActivity(checkVehicleToAdd);

        }else if(v.getId()==R.id.goMainMenu){
            // Navigate back to the AdminActivity

            Intent goMenu=new Intent(AddVehicleActivity.this, AdminActivity.class);
            startActivity(goMenu);
        }
    }
}
