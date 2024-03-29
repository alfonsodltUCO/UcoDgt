package mvc.view.admin;

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

import mvc.controller.CheckUserToFind;

public class FindUserActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup radiogrouptypeuser = findViewById(R.id.radioGroupTypeUserToFind);
    String selectedOption;
    EditText dniToSearch = findViewById(R.id.editTextDniToFind);
    Button search=findViewById(R.id.findUser);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_user);

        radiogrouptypeuser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    selectedOption = radioButton.getText().toString().trim();
                }
            }
        });
        search.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.checkAdd){
            Intent checkUserToAdd=new Intent(FindUserActivity.this, CheckUserToFind.class);
            checkUserToAdd.putExtra("userToFind",selectedOption);
            checkUserToAdd.putExtra("dni",dniToSearch.toString().trim());
            startActivity(checkUserToAdd);
        }
    }
}
