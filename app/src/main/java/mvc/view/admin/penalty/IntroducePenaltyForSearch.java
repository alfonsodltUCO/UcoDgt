package mvc.view.admin.penalty;

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

import mvc.controller.penalty.CheckPenaltiesToList;
import mvc.controller.penalty.CheckPenaltyToFind;
import mvc.view.admin.AdminActivity;

public class IntroducePenaltyForSearch extends AppCompatActivity implements View.OnClickListener {
    EditText etId;
    Button goSearch,goMain,searchByDates;
    RadioGroup rgroup;
    String selectedOption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_penalty_id);
        etId=findViewById(R.id.editTextId);
        goSearch=findViewById(R.id.findPenalty);
        goMain=findViewById(R.id.goMainMenu);
        searchByDates=findViewById(R.id.searchForDates);
        searchByDates.setOnClickListener(this);
        goMain.setOnClickListener(this);
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
                Intent intentFind = new Intent(IntroducePenaltyForSearch.this, CheckPenaltiesToList.class);
                intentFind.putExtra("state",selectedOption);
                startActivity(intentFind);
                finish();

            }else{
                Intent intentFind = new Intent(IntroducePenaltyForSearch.this, CheckPenaltyToFind.class);

                intentFind.putExtra("id",etId.getText().toString());
                startActivity(intentFind);
                finish();
            }
        } else if (v.getId()==R.id.goMainMenu) {
            Intent intentGoMain = new Intent(IntroducePenaltyForSearch.this, AdminActivity.class);
            startActivity(intentGoMain);
            finish();
        }else if(v.getId()==R.id.searchForDates){
            Intent searchForDates=new Intent(IntroducePenaltyForSearch.this,SearchByDatesPenalties.class);
            startActivity(searchForDates);
            finish();
        }
    }
}
