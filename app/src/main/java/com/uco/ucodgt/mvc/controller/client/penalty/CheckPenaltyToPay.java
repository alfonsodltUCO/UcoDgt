package com.uco.ucodgt.mvc.controller.client.penalty;


  import android.content.Intent;
 import android.os.Bundle;
 import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

 import com.android.volley.VolleyError;

 import java.util.ArrayList;
import java.util.Calendar;
 import java.util.Date;
import java.util.List;


import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
 import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.config.UIConfig;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
 import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButton;
 import com.uco.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.view.client.ClientActivity;


/**
 * This class allow client after check data is responsible for handling the payment process for a penalty.
 * It checks the card data and performs the payment, updating the penalty status accordingly.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToPay extends AppCompatActivity {
    ProgressBar progressBar;

    int PAYPAL_REQUEST_CODE=123;

    PaymentButton paypalButton;

    Button goMain;


    String cliendid="AexbuQVcW3oE-e1SudUupCh0SogisvlGA1db4y61niKYgQ564U5n2vXvN5Gbj3IFCW6hFLbumoYQrcPv";

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



        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        dni=getIntent().getStringExtra("dni");
        id=getIntent().getStringExtra("id");

        CheckoutConfig config= new CheckoutConfig(
                getApplication(),
                cliendid,
                Environment.SANDBOX,
                CurrencyCode.EUR,
                UserAction.PAY_NOW,
                null,
                new SettingsConfig(
                        true,
                        true
                ),
                new UIConfig(),
                "com.uco.ucodgt://paypalpay"
        );
        PayPalCheckout.setConfig(config);


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
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    goMain.putExtra("dni",dni);
                    startActivity(goMain);
                    Toast.makeText(CheckPenaltyToPay.this, "The penalty is already paid", Toast.LENGTH_LONG).show();
                    hideLoading();


                }else if(penalty.getState().toString().equals("cancelled")){

                    Intent goMain=new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    goMain.putExtra("dni",dni);
                    startActivity(goMain);
                    Toast.makeText(CheckPenaltyToPay.this, "The penalty is cancelled", Toast.LENGTH_LONG).show();
                    hideLoading();



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

                    setContentView(com.uco.ucodgt.R.layout.paypal_view);

                    goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);

                    goMain.setOnClickListener((v)->{
                        Intent goMain = new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                        goMain.putExtra("dni", dni);
                        startActivity(goMain);
                        hideLoading();

                    });
                    paypalButton=findViewById(com.uco.ucodgt.R.id.paypalButton);

                    paypalButton.setup(
                            new CreateOrder() {
                                @Override
                                public void create(@NonNull CreateOrderActions createOrderActions) {
                                    ArrayList purchaseUnits = new ArrayList();
                                    purchaseUnits.add(
                                            new PurchaseUnit.Builder()
                                                    .amount(
                                                            new Amount.Builder()
                                                                    .currencyCode(CurrencyCode.EUR)
                                                                    .value(penalty.getQuantity().toString())
                                                                    .build()
                                                    )
                                                    .build()
                                    );
                                    OrderRequest request = new OrderRequest(
                                            OrderIntent.CAPTURE,
                                            new AppContext.Builder()
                                                    .userAction(UserAction.PAY_NOW)
                                                    .build(),
                                            purchaseUnits
                                    );
                                    createOrderActions.create(request, (CreateOrderActions.OnOrderCreated) null);
                                }
                            }, new OnApprove() {
                                @Override
                                public void onApprove(@NonNull Approval approval) {
                                    approval.getOrderActions().capture(new OnCaptureComplete() {
                                        @Override
                                        public void onCaptureComplete(@NonNull CaptureOrderResult captureOrderResult) {
                                            mngP.doPayment(penalty, CheckPenaltyToPay.this, new PenaltyCallback() {
                                                @Override
                                                public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                                                }

                                                @Override
                                                public void onError(VolleyError error) {

                                                    if (error.networkResponse.statusCode == 404) {

                                                        Intent goMain = new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                                                        try {
                                                            Thread.sleep(2*1000);
                                                        }
                                                        catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        goMain.putExtra("dni", dni);
                                                        startActivity(goMain);
                                                        Toast.makeText(CheckPenaltyToPay.this, "The penalty probably doesnt exists", Toast.LENGTH_LONG).show();
                                                        hideLoading();


                                                    } else {

                                                        Intent goMain = new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                                                        try {
                                                            Thread.sleep(2*1000);
                                                        }
                                                        catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        goMain.putExtra("dni", dni);
                                                        startActivity(goMain);
                                                        Toast.makeText(CheckPenaltyToPay.this, "An error has ocurred during payment process, try again please", Toast.LENGTH_LONG).show();
                                                        hideLoading();

                                                    }

                                                }

                                                @Override
                                                public void onPenaltyReceived(PenaltyDTO penalty) {

                                                    Intent goMain = new Intent(CheckPenaltyToPay.this, ClientActivity.class);
                                                    try {
                                                        Thread.sleep(2*1000);
                                                    }
                                                    catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                    goMain.putExtra("dni", dni);
                                                    Toast.makeText(CheckPenaltyToPay.this, "Payment realized", Toast.LENGTH_LONG).show();
                                                    startActivity(goMain);
                                                    hideLoading();

                                                }
                                            });
                                        }
                                    });
                                }
                            }
                    );
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


}
