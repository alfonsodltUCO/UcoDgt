package mvc.view.worker.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import java.text.SimpleDateFormat;

import mvc.model.business.user.worker.WorkerDTO;
import mvc.view.worker.WorkerActivity;

/**
 * Activity for see the info of the worker.
 * @author Alfonso de la torre
 */
public class ShowWorker extends AppCompatActivity implements View.OnClickListener {
    TextView name,surname,numberofworker,birth,dni,email;
    String strDate;
    String dniNoText;
    String numberWorker;
    Button goMenu;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_worker_for_worker);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        name=findViewById(R.id.textViewFoundName);
        birth=findViewById(R.id.textViewFoundDateBirth);
        surname=findViewById(R.id.textViewFoundSurname);
        email=findViewById(R.id.textViewFoundEmail);
        dni=findViewById(R.id.textViewFoundDni);
        goMenu=findViewById(R.id.goMainMenu);
        numberofworker=findViewById(R.id.tvNumberWorker);

        goMenu.setOnClickListener(this);

        numberWorker=getIntent().getStringExtra("numberWorker");
        WorkerDTO worker = (WorkerDTO) getIntent().getSerializableExtra("worker");

        assert worker != null;
        name.setText("name= "+worker.getName());
        surname.setText("surname= "+worker.getSurname());
        email.setText("email= "+worker.getEmail());
        numberofworker.setText("licence points= "+worker.getNumberOfWorker().toString());

        dni.setText("dni= "+worker.getDni());
        dniNoText=worker.getDni();
        strDate= formatter.format(worker.getAge());
        birth.setText("birth= "+strDate);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v){

        if(v.getId()==R.id.goMainMenu){

            Intent showUser=new Intent(ShowWorker.this, WorkerActivity.class);
            showUser.putExtra("numberWorker",numberWorker);
            startActivity(showUser);
            finish();

        }
    }
}
