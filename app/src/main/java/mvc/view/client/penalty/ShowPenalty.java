package mvc.view.client.penalty;

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

import mvc.view.client.penalty.IntroduceDataForPay;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.view.client.ClientActivity;
/**
 * Activity class to display details of a penalty for a client.
 * @author Alfonso de la torre
 */
public class ShowPenalty extends AppCompatActivity implements View.OnClickListener{
    String idtodelete;
    TextView id,description,dniw,dnic,quant,points,date,state,reason,licenceP;
    Button goMain,payPenalty;
    PenaltyDTO penalty;
    String dni,workerNum;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     * @see AppCompatActivity
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        penalty=(PenaltyDTO)getIntent().getSerializableExtra("penalty");
        dni=getIntent().getStringExtra("dni");
        workerNum=getIntent().getStringExtra("worker");
        setContentView(R.layout.show_penalty_for_client);

        goMain=findViewById(R.id.goMainMenu);
        payPenalty=findViewById(R.id.payPenalty);
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

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(penalty.getDate());
        date.setText("date= "+strDate);

        dniw.setText("number of Worker= "+workerNum);
        dnic.setText("dni Client= "+penalty.getDniClient());

        description.setText(penalty.getDescription());
        state.setText("state= "+penalty.getState().toString());
        reason.setText("reason= "+penalty.getReason().toString());

        points.setText("points= "+penalty.getPoints().toString());
        quant.setText("quantity= "+ penalty.getQuantity().toString());
        licenceP.setText("plate= "+ penalty.getLicenceplate());

        goMain.setOnClickListener(this);
        payPenalty.setOnClickListener(this);
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.goMainMenu){
            // Navigate to main menu

            Intent goMain=new Intent(ShowPenalty.this, ClientActivity.class);
            goMain.putExtra("dni",dni);
            startActivity(goMain);
            finish();

        }else if(v.getId()==R.id.payPenalty){
            // Navigate to payment screen for the penalty

            Intent goPay=new Intent(ShowPenalty.this, IntroduceDataForPay.class);
            goPay.putExtra("id", penalty.getId().toString());
            goPay.putExtra("dni",dni);
            goPay.putExtra("quantity",penalty.getQuantity().toString());
            startActivity(goPay);
            finish();

        }
    }
}
