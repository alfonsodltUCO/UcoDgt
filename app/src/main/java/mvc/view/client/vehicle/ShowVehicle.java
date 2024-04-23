package mvc.view.client.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import java.text.SimpleDateFormat;

import mvc.controller.client.CheckPenaltiesToListForClient;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.view.client.ClientActivity;

public class ShowVehicle extends AppCompatActivity implements View.OnClickListener {
    String licplate;
    TextView lplate,itv1,itv2,idIns,color,type;
    String dni;
    Button goMain,deleteVehicle,listPenalties;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VehicleDTO vehicle= (VehicleDTO) getIntent().getSerializableExtra("vehicle");
        dni=getIntent().getStringExtra("dni");
        setContentView(R.layout.show_vehicle_for_client);
        deleteVehicle.setVisibility(View.GONE);
        lplate=findViewById(R.id.textViewFoundLicencePlate);
        color=findViewById(R.id.textViewFoundColor);
        type=findViewById(R.id.textViewCarType);
        idIns=findViewById(R.id.textViewFoundIdInsurance);
        itv1=findViewById(R.id.textViewFoundValidItvFrom);
        itv2=findViewById(R.id.textViewFoundValidItvTo);
        lplate.setText("Licence plate= "+vehicle.getLicencePlate());
        color.setText("Colo= "+vehicle.getColor().toString());
        type.setText("Type= "+vehicle.getCarType().toString());
        idIns.setText("Id insurance= "+vehicle.getIdInsurance());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(vehicle.getValidItvFrom());
        itv1.setText("Itv valid from= "+strDate);
        strDate= formatter.format(vehicle.getValidItvTo());
        itv2.setText("Itv valid to= "+strDate);
        goMain=findViewById(R.id.goMainMenu);
        goMain.setOnClickListener(this);
        deleteVehicle.setOnClickListener(this);
        licplate=vehicle.getLicencePlate();
        listPenalties=findViewById(R.id.listPenalties);
        listPenalties.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goMainMenu){
            Intent goMain=new Intent(ShowVehicle.this, ClientActivity.class);
            goMain.putExtra("dni",dni);
            startActivity(goMain);
            finish();
        } else if (v.getId()==R.id.listPenalties) {
            Intent goList=new Intent(ShowVehicle.this, CheckPenaltiesToListForClient.class);
            goList.putExtra("licencePlate",licplate);
            goList.putExtra("dni",dni);
            startActivity(goList);
            finish();
        }
    }

}
