package mvc.view.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.vehicle.CheckVehicleToFind;
import mvc.view.admin.AdminActivity;

public class IntroduceManual extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    Button buttonMain,buttonGoFind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_vehicle);
        et=findViewById(R.id.editTextPlateToSearch);
        buttonMain=findViewById(R.id.goMainMenu);
        buttonGoFind=findViewById(R.id.goFind);
        buttonGoFind.setOnClickListener(this);
        buttonMain.setOnClickListener(this);

    }
    public void onClick(View v) {

        if(v.getId()==R.id.goFind){
            Intent goIntent=new Intent(IntroduceManual.this, CheckVehicleToFind.class);
            goIntent.putExtra("licenceplate",et.getText().toString().trim());
            startActivity(goIntent);
            finish();
        }else if(v.getId()==R.id.goMainMenu){
            Intent goMenu=new Intent(IntroduceManual.this, AdminActivity.class);
            startActivity(goMenu);
            finish();
        }
    }
}
