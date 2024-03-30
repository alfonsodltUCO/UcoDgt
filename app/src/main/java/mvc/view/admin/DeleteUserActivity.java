package mvc.view.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.admin.CheckUserToDelete;
import mvc.controller.admin.CheckUserToFind;

public class DeleteUserActivity extends AppCompatActivity implements View.OnClickListener{
    RadioGroup radioGroupTypeOfUser;
    Button deleteUser;
    String selectedOption;

    TextView dniToDelete;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_user);
        radioGroupTypeOfUser=findViewById(R.id.radioGroupTypeUserToDelete);
        dniToDelete=findViewById(R.id.editTextDniToDelete);
        deleteUser=findViewById(R.id.deleteUser);
        radioGroupTypeOfUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    selectedOption = radioButton.getText().toString().trim();
                }
            }
        });
        deleteUser.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.deleteUser){
            Intent checkUserToAdd=new Intent(DeleteUserActivity.this, CheckUserToDelete.class);
            checkUserToAdd.putExtra("userToDelete",selectedOption);
            checkUserToAdd.putExtra("dni",dniToDelete.getText().toString().trim());
            startActivity(checkUserToAdd);
        }
    }
}
