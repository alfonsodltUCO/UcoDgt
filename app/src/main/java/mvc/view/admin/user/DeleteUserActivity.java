package mvc.view.admin.user;

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
import mvc.view.admin.AdminActivity;

public class DeleteUserActivity extends AppCompatActivity implements View.OnClickListener{
    RadioGroup radioGroupTypeOfUser;
    Button deleteUser,goMenu;
    String selectedOption;

    TextView dniToDelete;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_user);
        radioGroupTypeOfUser=findViewById(R.id.radioGroupTypeUserToDelete);
        dniToDelete=findViewById(R.id.editTextDniToDelete);
        deleteUser=findViewById(R.id.deleteUser);
        goMenu=findViewById(R.id.goMainMenu);

        radioGroupTypeOfUser.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                selectedOption = radioButton.getText().toString().trim();
            }
        });
        goMenu.setOnClickListener(this);
        deleteUser.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.deleteUser){
            Intent checkUserToAdd=new Intent(DeleteUserActivity.this, CheckUserToDelete.class);
            checkUserToAdd.putExtra("userToDelete",selectedOption);
            checkUserToAdd.putExtra("dni",dniToDelete.getText().toString().trim());
            startActivity(checkUserToAdd);
        }else if(v.getId()==R.id.goMainMenu){
            Intent goMenu=new Intent(DeleteUserActivity.this, AdminActivity.class);
            startActivity(goMenu);
        }
    }
}
