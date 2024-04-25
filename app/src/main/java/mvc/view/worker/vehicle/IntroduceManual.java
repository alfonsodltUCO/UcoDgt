package mvc.view.worker.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.worker.CheckVehicleToFindForWorker;
import mvc.view.worker.WorkerActivity;


/**
 * Activity class to introduce a manual search for a vehicle by licence plate.
 * @author Alfonso de la torre
 */
public class IntroduceManual extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    Button buttonMain,buttonGoFind;

    String numberWorker;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_vehicle);
        et=findViewById(R.id.editTextPlateToSearch);
        buttonMain=findViewById(R.id.goMainMenu);
        buttonGoFind=findViewById(R.id.goFind);

        numberWorker=getIntent().getStringExtra("numberWorker");

        buttonGoFind.setOnClickListener(this);
        buttonMain.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {

        if(v.getId()==R.id.goFind){

            Intent goIntent=new Intent(IntroduceManual.this, CheckVehicleToFindForWorker.class);
            goIntent.putExtra("licenceplate",et.getText().toString().trim());
            goIntent.putExtra("numberWorker",numberWorker);
            startActivity(goIntent);
            finish();

        }else if(v.getId()==R.id.goMainMenu){

            Intent goMenu=new Intent(IntroduceManual.this, WorkerActivity.class);
            goMenu.putExtra("numberWorker",numberWorker);
            startActivity(goMenu);
            finish();

        }
    }
}
