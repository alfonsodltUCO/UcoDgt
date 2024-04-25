package mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.admin.penalty.CheckPenaltyToAdd;
import mvc.view.admin.AdminActivity;
/**
 * Activity to allow administrators to introduce a description for a new penalty.
 * @author Alfonso de la torre
 */
public class IntroduceDescriptionForPenalty extends AppCompatActivity implements View.OnClickListener{
    Button goCheckAdd,goMain;
    EditText etDescrp;
    String date, dniC,dniW,state, reason,informed,locality,place, quantity,points,licenceplate;

    /**
     * Initializes the activity with UI components and retrieves data from the intent.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
     */
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
    /**
     * Handles click events for UI components.
     *
     * @param v The View that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.goMainMenu){
            // Navigate back to the main menu

            Intent intentGoMain = new Intent(IntroduceDescriptionForPenalty.this, AdminActivity.class);
            startActivity(intentGoMain);
            finish();

        }else if(v.getId()==R.id.checkPenaltyToAdd){
            // Proceed to check and add the penalty with the provided description

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
