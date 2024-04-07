package mvc.view.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.vehicle.CheckVehicleToDelete;
import mvc.view.admin.AdminActivity;

public class DeleteVehicleActivity extends AppCompatActivity implements View.OnClickListener{

    Button deleteVehicle,goMain;
    EditText licencePlate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_vehicle);
        licencePlate=findViewById(R.id.editTextPlateToDelete);
        deleteVehicle=findViewById(R.id.deleteVehicle);
        goMain=findViewById(R.id.goMainMenu);

        deleteVehicle.setOnClickListener(this);
        goMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goMainMenu){
            Intent goMain=new Intent(DeleteVehicleActivity.this, AdminActivity.class);
            startActivity(goMain);
            finish();
        }else if(v.getId()==R.id.deleteVehicle){
            Intent goDelete=new Intent(DeleteVehicleActivity.this, CheckVehicleToDelete.class);
            goDelete.putExtra("licencePlate",licencePlate.getText().toString().trim());
            startActivity(goDelete);
            finish();
        }
    }
}
