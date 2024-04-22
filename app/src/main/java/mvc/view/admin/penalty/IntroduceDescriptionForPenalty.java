package mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.penalty.CheckPenaltyToAdd;
import mvc.view.admin.AdminActivity;

public class IntroduceDescriptionForPenalty extends AppCompatActivity implements View.OnClickListener{
    Button goCheckAdd,goMain;
    EditText etDescrp;
    String date;
    String dniC;
    String dniW;
    String state;
    String reason;
    String informed;
    String locality;
    String place;
    String quantity;
    String points;
    String licenceplate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_penalty_description);
        etDescrp=findViewById(R.id.etDescription);
        date=getIntent().getStringExtra("date");
        dniC=getIntent().getStringExtra("dniC");
        dniW=getIntent().getStringExtra("dniW");
        state=getIntent().getStringExtra("state");
        reason=getIntent().getStringExtra("reason");
        informed=getIntent().getStringExtra("informed");
        locality=getIntent().getStringExtra("locality");
        place=getIntent().getStringExtra("place");
        quantity=getIntent().getStringExtra("quantity");
        points=getIntent().getStringExtra("points");
        licenceplate=getIntent().getStringExtra("licenceplate");

        goCheckAdd=findViewById(R.id.checkPenaltyToAdd);
        goMain=findViewById(R.id.goMainMenu);

        goCheckAdd.setOnClickListener(this);
        goMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goMainMenu){
            Intent intentGoMain = new Intent(IntroduceDescriptionForPenalty.this, AdminActivity.class);
            startActivity(intentGoMain);
            finish();
        }else if(v.getId()==R.id.checkPenaltyToAdd){
            Intent goNext = new Intent(IntroduceDescriptionForPenalty.this, CheckPenaltyToAdd.class);
            goNext.putExtra("date",date);
            goNext.putExtra("dniC",dniC);
            goNext.putExtra("dniW",dniW);
            goNext.putExtra("state",state);
            goNext.putExtra("reason",reason);
            goNext.putExtra("place",place);
            goNext.putExtra("informed",informed);
            goNext.putExtra("locality",locality);
            goNext.putExtra("licenceplate",licenceplate);
            goNext.putExtra("quantity",quantity);
            goNext.putExtra("points",points);
            goNext.putExtra("description",etDescrp.getText().toString());
            startActivity(goNext);
            finish();
        }
    }
}
