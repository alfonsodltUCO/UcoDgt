package mvc.view.client.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.client.penalty.CheckPenaltyToPay;
import mvc.view.client.ClientActivity;

public class IntroduceDataForPay extends AppCompatActivity implements View.OnClickListener {
    String dni,id,quantity;
    TextView quant;
    Button goMain,goFinish;
    EditText etNumber,etCvv,etCaducity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_penalty);
        dni=getIntent().getStringExtra("dni");
        id=getIntent().getStringExtra("id");
        quantity=getIntent().getStringExtra("quantity");
        quant=findViewById(R.id.tvQuantityToPut);
        quant.setText(quantity);
        goFinish=findViewById(R.id.goPay);
        goMain=findViewById(R.id.goMainMenu);
        etCaducity=findViewById(R.id.etCaducity);
        etCvv=findViewById(R.id.etCvv);
        etNumber=findViewById(R.id.etNumberCard);
        goMain.setOnClickListener(this);
        goFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goMainMenu){
            Intent goMain=new Intent(IntroduceDataForPay.this, ClientActivity.class);
            goMain.putExtra("dni",dni);
            startActivity(goMain);
            finish();
        }else if(v.getId()==R.id.goPay){
            Intent goPay=new Intent(IntroduceDataForPay.this, CheckPenaltyToPay.class);
            goPay.putExtra("id", id);
            goPay.putExtra("dni",dni);
            goPay.putExtra("number",etNumber.getText().toString());
            goPay.putExtra("cvv",etCvv.getText().toString());
            goPay.putExtra("caducity",etCaducity.getText().toString());
            startActivity(goPay);
            finish();
        }

    }
}
