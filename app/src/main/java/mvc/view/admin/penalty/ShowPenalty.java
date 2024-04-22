package mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.penalty.CheckPenaltyToDelete;
import mvc.controller.vehicle.CheckVehicleToDelete;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.vehicle.ShowVehicle;

public class ShowPenalty extends AppCompatActivity implements View.OnClickListener{
    String idtodelete;
    TextView id,description,dniw,dnic,quant,points,date,state,reason,licenceP;
    Button goMain,deletePenalty;
    PenaltyDTO penalty;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        penalty=(PenaltyDTO)getIntent().getSerializableExtra("penalty");
        setContentView(R.layout.show_penalty);
        goMain=findViewById(R.id.goMainMenu);
        deletePenalty=findViewById(R.id.deletePenalty);
        id=findViewById(R.id.tvId);
        date=findViewById(R.id.tvDate);
        dnic=findViewById(R.id.tvDniC);
        dniw=findViewById(R.id.tvDniW);
        description=findViewById(R.id.tvDescription);
        state=findViewById(R.id.tvState);
        reason=findViewById(R.id.tvReason);
        licenceP=findViewById(R.id.tvLicenceP);
        idtodelete=id.getText().toString();
        quant=findViewById(R.id.tvQuantity);
        points=findViewById(R.id.tvPoints);
        id.setText("id= "+penalty.getId().toString());
        date.setText("date= "+penalty.getDate().toString());
        dniw.setText("dni Worker= "+penalty.getDniWorker());
        dnic.setText("dni Client= "+penalty.getDniClient());
        description.setText(penalty.getDescription());
        state.setText("state= "+penalty.getState().toString());
        reason.setText("reason= "+penalty.getReason().toString());
        points.setText("points= "+penalty.getPoints().toString());
        quant.setText("quantity= "+ penalty.getQuantity().toString());
        licenceP.setText("plate= "+penalty.getLicenceplate().toString());
        goMain.setOnClickListener(this);
        deletePenalty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goMainMenu){
            Intent goMain=new Intent(ShowPenalty.this, AdminActivity.class);
            startActivity(goMain);
            finish();
        }else if(v.getId()==R.id.deletePenalty){
            Intent goDelete=new Intent(ShowPenalty.this, CheckPenaltyToDelete.class);
            goDelete.putExtra("id", penalty.getId().toString());
            startActivity(goDelete);
            finish();
        }
    }
}
