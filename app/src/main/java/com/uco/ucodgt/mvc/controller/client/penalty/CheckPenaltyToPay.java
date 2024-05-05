package com.uco.ucodgt.mvc.controller.client.penalty;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkCardData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.uco.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.view.client.ClientActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is responsible for handling the payment process for a penalty.
 * It checks the card data and performs the payment, updating the penalty status accordingly.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToPay extends AppCompatActivity {
    ProgressBar progressBar;

    int PAYPAL_REQUEST_CODE=123;

    public static PayPalConfiguration configuration;

    String cliendid="AexbuQVcW3oE-e1SudUupCh0SogisvlGA1db4y61niKYgQ564U5n2vXvN5Gbj3IFCW6hFLbumoYQrcPv";
    String secretid="EAUc289DrSyxj1X-Uzc2Wox_bd9jM7QLxi6CfwCxncQYD_6XPmj-jQBNr5Ek_4GfNzrMWmnvLpqbBuHc";
    String dni,id;
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

        configuration=new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK).clientId(cliendid);

        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        dni=getIntent().getStringExtra("dni");
        id=getIntent().getStringExtra("id");





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
                    //dopayment

                    getPayment(penalty);



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

    private void getPayment(PenaltyDTO penalty) {

        PayPalPayment payment=new PayPalPayment(BigDecimal.valueOf(penalty.getQuantity()),"EUR","Quantity to pay",PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this,"AJAJJAJA", Toast.LENGTH_LONG).show();

        if(requestCode==PAYPAL_REQUEST_CODE){
            PaymentConfirmation paymentConfirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if(paymentConfirmation!=null){
                try {
                    String paymentdetails=paymentConfirmation.toJSONObject().toString();
                    JSONObject object=new JSONObject(paymentdetails);
                } catch (JSONException e) {
                    Toast.makeText(this,e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }else if(requestCode== Activity.RESULT_CANCELED){
            Toast.makeText(this,"error", Toast.LENGTH_LONG).show();
        } else if(requestCode==PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this,"invalid payment", Toast.LENGTH_LONG).show();
        } else if (requestCode==PaymentActivity.RESULT_OK) {
            Toast.makeText(this,"OK", Toast.LENGTH_LONG).show();

        }
        if (requestCode==PaymentActivity.RESULT_OK) {
            Toast.makeText(this,"OK", Toast.LENGTH_LONG).show();

        }
    }
}
