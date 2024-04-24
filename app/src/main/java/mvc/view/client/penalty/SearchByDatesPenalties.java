package mvc.view.client.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.client.CheckPenaltiesToListForClient;
import mvc.view.client.ClientActivity;


public class SearchByDatesPenalties extends AppCompatActivity implements View.OnClickListener {
    EditText etDate1,etDate2;
    Button goSearch,goMain;
    String dni;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_penalty_dates);
        dni=getIntent().getStringExtra("dni");
        etDate1=findViewById(R.id.etDateStart);
        etDate2=findViewById(R.id.etDateEnd);
        goSearch=findViewById(R.id.findPenalty);
        goMain=findViewById(R.id.goMainMenu);
        goMain.setOnClickListener(this);
        goSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.findPenalty){
            Intent intentFind = new Intent(SearchByDatesPenalties.this, CheckPenaltiesToListForClient.class);
            intentFind.putExtra("date1",etDate1.getText().toString());
            intentFind.putExtra("date2",etDate2.getText().toString());
            intentFind.putExtra("dni",dni);
            startActivity(intentFind);
            finish();
        } else if (v.getId()==R.id.goMainMenu) {
            Intent intentGoMain = new Intent(SearchByDatesPenalties.this, ClientActivity.class);
            intentGoMain.putExtra("dni",dni);
            startActivity(intentGoMain);
            finish();
        }
    }
}
