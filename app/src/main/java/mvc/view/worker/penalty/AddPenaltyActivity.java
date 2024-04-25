package mvc.view.worker.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.view.worker.WorkerActivity;


/**
 * Activity for adding a new penalty.
 * @author Alfonso de la torre
 */
public class AddPenaltyActivity extends AppCompatActivity implements View.OnClickListener {
    Button goAddDescrp,goMain;
    EditText etDniClient,etReason,etPlace,etInformedAtTheMoment,etLocality,etLPlate,etQuantity,etPoints;

    String numberWorker;
    /**
     * Initializes the activity with UI components and sets up click listeners.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_penalty_for_worker);
        goAddDescrp=findViewById(R.id.goNext);
        goMain=findViewById(R.id.goMainMenu);
        numberWorker=getIntent().getStringExtra("numberWorker");
        String plate=getIntent().getStringExtra("licencePlate");

        etDniClient=findViewById(R.id.etCDni);
        etReason=findViewById(R.id.etReason);
        etPlace=findViewById(R.id.etPlace);
        etInformedAtTheMoment=findViewById(R.id.etInformedAtTheMoment);
        etLocality=findViewById(R.id.etLocality);
        etLPlate=findViewById(R.id.etLicencePlate);
        etQuantity=findViewById(R.id.etQuantity);
        etPoints=findViewById(R.id.etPoints);

        if(!TextUtils.isEmpty(plate)){
            etLPlate.setText(plate);
        }
        goAddDescrp.setOnClickListener(this);
        goMain.setOnClickListener(this);
    }

    /**
     * Handles click events for the buttons in the activity.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {

        if(v.getId()==R.id.goNext){

            Intent goNext = new Intent(AddPenaltyActivity.this, IntroduceDescriptionForPenalty.class);
            goNext.putExtra("dniC",etDniClient.getText().toString());
            goNext.putExtra("state","processed");
            goNext.putExtra("reason",etReason.getText().toString());
            goNext.putExtra("place",etPlace.getText().toString());
            goNext.putExtra("informed",etInformedAtTheMoment.getText().toString());
            goNext.putExtra("locality",etLocality.getText().toString());
            goNext.putExtra("licenceplate",etLPlate.getText().toString());
            goNext.putExtra("quantity",etQuantity.getText().toString());
            goNext.putExtra("points",etPoints.getText().toString());
            goNext.putExtra("numberWorker",numberWorker);
            startActivity(goNext);
            finish();

        } else if (v.getId()==R.id.goMainMenu) {

            Intent intentGoMain = new Intent(AddPenaltyActivity.this, WorkerActivity.class);
            intentGoMain.putExtra("numberWorker",numberWorker);
            startActivity(intentGoMain);
            finish();

        }
    }
}
