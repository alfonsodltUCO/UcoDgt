package mvc.controller.penalty;

import static mvc.controller.commonFunctions.ForCheckListPenalty.checkPenalty;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkDate;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkPoints;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkQuantity;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkReasonOf;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkStateOf;
import static mvc.controller.commonFunctions.ForCheckUser.checkDni;
import static mvc.controller.commonFunctions.ForCheckVehicle.checkPlate;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mvc.controller.admin.CheckUserToAdd;
import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.list.ManagerListPenalty;
import mvc.model.business.penalty.stateof;
import mvc.model.business.penalty.typeof;
import mvc.view.admin.AddUserActivity;
import mvc.view.admin.penalty.AddPenaltyActivity;

public class CheckPenaltyToAdd extends AppCompatActivity {
    private ProgressBar progressBar;

    String dniW;
    String dniC;
    String date;
    String state;
    String reason;

    String description;
    boolean val;


    String place;

    String informed;

    String locality;

    String licenceplate;

    String quantity;

    String points;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        showLoading();
        date=getIntent().getStringExtra("date");
        dniC=getIntent().getStringExtra("dniC");
        dniW=getIntent().getStringExtra("dniW");
        state=getIntent().getStringExtra("state");
        reason=getIntent().getStringExtra("reason");
        place=getIntent().getStringExtra("place");
        informed=getIntent().getStringExtra("informed");
        locality=getIntent().getStringExtra("locality");
        licenceplate=getIntent().getStringExtra("licenceplate");
        quantity=getIntent().getStringExtra("quantity");
        points=getIntent().getStringExtra("points");
        description=getIntent().getStringExtra("description");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfPenalty;
        try {
            dateOfPenalty = format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(informed.equals("yes") || informed.equals("Yes") || informed.equals("YES")){
            val=true;
        }else if(informed.equals("no") || informed.equals("No") || informed.equals("NO")){
            val=false;
        }else{
            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckPenaltyToAdd.this,"Invalid value for informed at the moment\nMust be Yes/No", Toast.LENGTH_LONG).show();
            hideLoading();
            finish();
        }

        if(!TextUtils.isEmpty(points) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(licenceplate) && !TextUtils.isEmpty(quantity) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(dniC) && !TextUtils.isEmpty(dniW) && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(reason) && !TextUtils.isEmpty(place) && !TextUtils.isEmpty(informed) && !TextUtils.isEmpty(locality)){
            if(!checkDate(date)){
                Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckPenaltyToAdd.this,"Invalid date\nMust be at least today or yesterday", Toast.LENGTH_LONG).show();
                hideLoading();
                finish();
            }else{
                if(!checkDni(dniC)){
                    Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                    startActivity(intentAdmin);
                    Toast.makeText(CheckPenaltyToAdd.this,"Invalid format DNI of client", Toast.LENGTH_LONG).show();
                    hideLoading();
                    finish();
                }else{
                    if(!checkDni(dniW)){
                        Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                        startActivity(intentAdmin);
                        Toast.makeText(CheckPenaltyToAdd.this,"Invalid format DNI of worker", Toast.LENGTH_LONG).show();
                        hideLoading();
                        finish();
                    }else{
                        if(!checkStateOf(state)){
                            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                            startActivity(intentAdmin);
                            Toast.makeText(CheckPenaltyToAdd.this,"Invalid value for state", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();
                        }else{
                            if(!checkReasonOf(reason)){
                                Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                startActivity(intentAdmin);
                                Toast.makeText(CheckPenaltyToAdd.this,"Invalid format for reason", Toast.LENGTH_LONG).show();
                                hideLoading();
                                finish();
                            }else{
                                if(!checkPlate(licenceplate)){
                                    Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                    startActivity(intentAdmin);
                                    Toast.makeText(CheckPenaltyToAdd.this,"Invalid format for licence plate", Toast.LENGTH_LONG).show();
                                    hideLoading();
                                    finish();
                                }else{
                                    if(!checkPoints(points)){
                                        Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                        startActivity(intentAdmin);
                                        Toast.makeText(CheckPenaltyToAdd.this,"Points must be a number\nAnd a number", Toast.LENGTH_LONG).show();
                                        hideLoading();
                                        finish();
                                    }else {
                                        if(!checkQuantity(quantity)){
                                            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                            startActivity(intentAdmin);
                                            Toast.makeText(CheckPenaltyToAdd.this,"Quantity must be a number\nAnd a number", Toast.LENGTH_LONG).show();
                                            hideLoading();
                                            finish();
                                        }else{
                                            PenaltyDTO penalty=new PenaltyDTO(null,Integer.parseInt(points),dateOfPenalty,Float.parseFloat(quantity), typeof.valueOf(reason), stateof.valueOf(state),dniC,dniW,description,place,val,locality,licenceplate);
                                            if(!checkPenalty(penalty,CheckPenaltyToAdd.this,penalty)){
                                                Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                                startActivity(intentAdmin);
                                                Toast.makeText(CheckPenaltyToAdd.this,"Points and quantity must be between the range of penalty reason", Toast.LENGTH_LONG).show();
                                                hideLoading();
                                                finish();
                                            }else{

                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckPenaltyToAdd.this,"Please fill all fields", Toast.LENGTH_LONG).show();
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