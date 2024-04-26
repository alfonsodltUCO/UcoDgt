package mvc.view.admin.vehicle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import java.text.SimpleDateFormat;

import mvc.controller.admin.penalty.CheckPenaltiesToList;
import mvc.controller.admin.vehicle.CheckVehicleToDelete;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.view.admin.AdminActivity;
/**
 * Activity class to display details of a vehicle for administrators.
 * @author Alfonso de la torre
 */
public class ShowVehicle extends AppCompatActivity implements View.OnClickListener {
    String licplate;
    TextView lplate,itv1,itv2,idIns,color,type;
    Button goMain,deleteVehicle,listPenalties,extendItv,extendInsurance;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        VehicleDTO vehicle= (VehicleDTO) getIntent().getSerializableExtra("vehicle");
        setContentView(R.layout.show_vehicle);
        lplate=findViewById(R.id.textViewFoundLicencePlate);
        color=findViewById(R.id.textViewFoundColor);
        extendInsurance=findViewById(R.id.extendInsurance);
        extendItv=findViewById(R.id.extendItv);

        type=findViewById(R.id.textViewCarType);
        idIns=findViewById(R.id.textViewFoundIdInsurance);
        itv1=findViewById(R.id.textViewFoundValidItvFrom);
        itv2=findViewById(R.id.textViewFoundValidItvTo);

        lplate.setText("Licence plate= "+vehicle.getLicencePlate());
        color.setText("Color= "+vehicle.getColor().toString());
        type.setText("Type= "+vehicle.getCarType().toString());
        idIns.setText("Id insurance= "+vehicle.getIdInsurance());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(vehicle.getValidItvFrom());
        itv1.setText("Itv valid from= "+strDate);
        strDate= formatter.format(vehicle.getValidItvTo());

        itv2.setText("Itv valid to= "+strDate);
        goMain=findViewById(R.id.goMainMenu);
        deleteVehicle=findViewById(R.id.deleteVehicle);
        goMain.setOnClickListener(this);
        deleteVehicle.setOnClickListener(this);

        licplate=vehicle.getLicencePlate();
        listPenalties=findViewById(R.id.listPenalties);
        listPenalties.setOnClickListener(this);
        extendItv.setOnClickListener(this);
        extendInsurance.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.goMainMenu){

            Intent goMain=new Intent(ShowVehicle.this, AdminActivity.class);
            startActivity(goMain);
            finish();

        }else if(v.getId()==R.id.deleteVehicle){

            Intent goDelete=new Intent(ShowVehicle.this, CheckVehicleToDelete.class);
            goDelete.putExtra("licencePlate",licplate);
            startActivity(goDelete);
            finish();

        } else if (v.getId()==R.id.listPenalties) {

            Intent goList=new Intent(ShowVehicle.this, CheckPenaltiesToList.class);
            goList.putExtra("licencePlate",licplate);
            startActivity(goList);
            finish();

        } else if (v.getId()==R.id.extendInsurance) {

            Intent goExtend=new Intent(ShowVehicle.this, .class);
            goExtend.putExtra("licencePlate",licplate);
            startActivity(goExtend);
            finish();

        } else if (v.getId()==R.id.extendItv) {

            Intent goExtend=new Intent(ShowVehicle.this, .class);
            goExtend.putExtra("licencePlate",licplate);
            startActivity(goExtend);
            finish();

        }
    }

}
