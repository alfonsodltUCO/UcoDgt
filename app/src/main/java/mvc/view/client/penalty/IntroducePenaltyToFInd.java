package mvc.view.client.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.client.penalty.CheckPenaltiesToListForClient;
import mvc.controller.client.penalty.CheckPenaltyToFindForClient;
import mvc.view.client.ClientActivity;

public class IntroducePenaltyToFInd extends AppCompatActivity implements View.OnClickListener {

    EditText etId;
    Button goSearch,goMain,searchByDates;
    RadioGroup rgroup;
    String selectedOption;
    String dni;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_penalty_id);
        dni=getIntent().getStringExtra("dni");
        etId=findViewById(R.id.editTextId);
        goSearch=findViewById(R.id.findPenalty);
        goMain=findViewById(R.id.goMainMenu);
        searchByDates=findViewById(R.id.searchForDates);
        searchByDates.setOnClickListener(this);
        goMain.setOnClickListener(this);
        rgroup=findViewById(R.id.rgState);
        goSearch.setOnClickListener(this);
        rgroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                selectedOption = radioButton.getText().toString().trim();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.findPenalty){
            if(selectedOption!=null){
                Intent intentFind = new Intent(IntroducePenaltyToFInd.this, CheckPenaltiesToListForClient.class);
                intentFind.putExtra("state",selectedOption);
                intentFind.putExtra("dni",dni);
                startActivity(intentFind);
                finish();

            }else{
                Intent intentFind = new Intent(IntroducePenaltyToFInd.this, CheckPenaltyToFindForClient.class);
                intentFind.putExtra("id",etId.getText().toString());
                intentFind.putExtra("dni",dni);
                startActivity(intentFind);
                finish();
            }
        } else if (v.getId()==R.id.goMainMenu) {
            Intent intentGoMain = new Intent(IntroducePenaltyToFInd.this, ClientActivity.class);
            intentGoMain.putExtra("dni",dni);
            startActivity(intentGoMain);
            finish();
        }else if(v.getId()==R.id.searchForDates){
            Intent searchForDates=new Intent(IntroducePenaltyToFInd.this, SearchByDatesPenalties.class);
            searchForDates.putExtra("dni",dni);
            startActivity(searchForDates);
            finish();
        }
    }
}
