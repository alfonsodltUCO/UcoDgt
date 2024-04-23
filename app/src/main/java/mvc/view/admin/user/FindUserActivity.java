package mvc.view.admin.user;

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

import mvc.controller.admin.CheckUserToFind;
import mvc.view.admin.AdminActivity;

public class FindUserActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup radiogrouptypeuser;
    String selectedOption;
    EditText dniToSearch;
    Button search,goMenu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_user);
        search=findViewById(R.id.findUser);
        radiogrouptypeuser = findViewById(R.id.radioGroupTypeUserToFind);
        dniToSearch = findViewById(R.id.editTextDniToFind);
        goMenu=findViewById(R.id.goMainMenu);
        radiogrouptypeuser.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                selectedOption = radioButton.getText().toString().trim();
            }
        });
        search.setOnClickListener(this);
        goMenu.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.findUser){
            Intent checkUserToAdd=new Intent(FindUserActivity.this, CheckUserToFind.class);
            checkUserToAdd.putExtra("userToFind",selectedOption);
            checkUserToAdd.putExtra("dni",dniToSearch.getText().toString().trim());
            startActivity(checkUserToAdd);
        }else if(v.getId()==R.id.goMainMenu){
            Intent goMenu=new Intent(FindUserActivity.this, AdminActivity.class);
            startActivity(goMenu);
        }
    }
}
