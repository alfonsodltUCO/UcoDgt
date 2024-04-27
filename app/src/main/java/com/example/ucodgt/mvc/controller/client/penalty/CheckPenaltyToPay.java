package com.example.ucodgt.mvc.controller.client.penalty;

import static com.example.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkCardData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.example.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.example.ucodgt.mvc.model.data.PenaltyCallback;
import com.example.ucodgt.mvc.view.client.ClientActivity;
/**
 * This class is responsible for handling the payment process for a penalty.
 * It checks the card data and performs the payment, updating the penalty status accordingly.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToPay extends AppCompatActivity {
    ProgressBar progressBar;

    String cvv,number,dni,id,caducity;
    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.example.ucodgt.R.layout.loading);
        progressBar=findViewById(com.example.ucodgt.R.id.progressbar);
        showLoading();
        cvv=getIntent().getStringExtra("cvv");
        number=getIntent().getStringExtra("number");
        dni=getIntent().getStringExtra("dni");
        id=getIntent().getStringExtra("id");
        caducity=getIntent().getStringExtra("caducity");

        if(!TextUtils.isEmpty(number) && !TextUtils.isEmpty(cvv) && !TextUtils.isEmpty(caducity)){// Check if card data is valid

            if(!checkCardData(cvv,number,caducity)){//Simulate a paymnet, check data is valid (format)

                Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                Toast.makeText(CheckPenaltyToPay.this, "Introduce correctly the data please", Toast.LENGTH_LONG).show();
                goMain.putExtra("dni",dni);
                startActivity(goMain);
                hideLoading();
                finish();
            }else{
                // Perform payment

                PenaltyDTO penalty=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);
                ManagerPenalty mngP=new ManagerPenalty();

                mngP.getPenalty(penalty, CheckPenaltyToPay.this, new PenaltyCallback() {
                    @Override
                    public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    }

                    @Override
                    public void onError(VolleyError error) {

                    }

                    @Override
                    public void onPenaltyReceived(PenaltyDTO penalty) {

                        if(penalty.getState().toString().equals("paid")){

                            Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                            goMain.putExtra("dni",dni);
                            startActivity(goMain);
                            Toast.makeText(CheckPenaltyToPay.this, "The penalty is already paid", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();

                        }else if(penalty.getState().toString().equals("cancelled")){

                            Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                            goMain.putExtra("dni",dni);
                            startActivity(goMain);
                            Toast.makeText(CheckPenaltyToPay.this, "The penalty is cancelled", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();


                        }else{
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(penalty.getDate());

                            Calendar cal2 = Calendar.getInstance();
                            cal2.setTime(new Date());

                            cal2.add(Calendar.MONTH, -1);

                            if(!cal.before(cal2)){
                                Toast.makeText(CheckPenaltyToPay.this, "The discount will be applied: "+(penalty.getQuantity()/2), Toast.LENGTH_LONG).show();
                                penalty.setQuantity(penalty.getQuantity()/2);
                            }

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
    /**
     * Shows the loading progress bar.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    /**
     * Hides the loading progress bar.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
