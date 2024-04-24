package mvc.controller.client;

import static mvc.controller.commonFunctions.ForCheckPenalty.checkCardData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.data.PenaltyCallback;
import mvc.view.client.ClientActivity;

public class CheckPenaltyToPay extends AppCompatActivity {
    ProgressBar progressBar;

    String cvv,number,dni,id,caducity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        cvv=getIntent().getStringExtra("cvv");
        number=getIntent().getStringExtra("number");
        dni=getIntent().getStringExtra("dni");
        id=getIntent().getStringExtra("id");
        caducity=getIntent().getStringExtra("caducity");

        if(!TextUtils.isEmpty(number) && !TextUtils.isEmpty(cvv) && !TextUtils.isEmpty(caducity)){
            if(!checkCardData(cvv,number,caducity)){
                Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                Toast.makeText(CheckPenaltyToPay.this, "Introduce correctly the data please", Toast.LENGTH_LONG).show();
                goMain.putExtra("dni",dni);
                startActivity(goMain);
                hideLoading();
                finish();
            }else{
                PenaltyDTO penalty=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);
                ManagerPenalty mngP=new ManagerPenalty();

                mngP.doPayment(penalty,CheckPenaltyToPay.this, new PenaltyCallback() {
                    @Override
                    public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    }

                    @Override
                    public void onError(VolleyError error) {
                        if(error.networkResponse.statusCode==404){
                            Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                            goMain.putExtra("dni",dni);
                            startActivity(goMain);
                            Toast.makeText(CheckPenaltyToPay.this, "The penalty probably doesnt exists", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();
                        }else{
                            Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                            goMain.putExtra("dni",dni);
                            startActivity(goMain);
                            Toast.makeText(CheckPenaltyToPay.this, "An error has ocurred during payment process, try again please", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();
                        }

                    }

                    @Override
                    public void onPenaltyReceived(PenaltyDTO penalty) {
                        Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                        goMain.putExtra("dni",dni);
                        Toast.makeText(CheckPenaltyToPay.this, "Payment realized", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        hideLoading();
                        finish();
                    }
                });
            }

        }else{
            Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
            goMain.putExtra("dni",dni);
            Toast.makeText(CheckPenaltyToPay.this, "Please fill the card information", Toast.LENGTH_LONG).show();
            startActivity(goMain);
            hideLoading();
            finish();
        }

    }
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
