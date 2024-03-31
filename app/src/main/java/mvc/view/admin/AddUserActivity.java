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

import mvc.controller.admin.CheckUserToAdd;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    Button checkAddUser;
    String typeofuser;
    String selectedOption;
    EditText editTextName,editTextDni,editTextSurname,editTextAge,editTextPassword,editTextLicencePoints,editTextEmail;

    RadioGroup radiogrouptypeuser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_user);
        editTextAge=findViewById(R.id.editTextDateBirth);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextDni=findViewById(R.id.editTextDNI);
        editTextName=findViewById(R.id.editTextName);
        editTextSurname=findViewById(R.id.editTextSurname);
        radiogrouptypeuser=findViewById(R.id.radioGroupTypeUserToAdd);
        editTextLicencePoints=findViewById(R.id.editTextLicencePoints);
        editTextPassword=findViewById(R.id.editTextPassword);
        radiogrouptypeuser.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                selectedOption = radioButton.getText().toString().trim();
            }
        });
        checkAddUser=findViewById(R.id.checkAdd);

        checkAddUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.checkAdd){
           Intent checkUserToAdd=new Intent(AddUserActivity.this, CheckUserToAdd.class);
           checkUserToAdd.putExtra("name",editTextName.getText().toString().trim());
           checkUserToAdd.putExtra("surname",editTextSurname.getText().toString().trim());
           checkUserToAdd.putExtra("dni",editTextDni.getText().toString().trim());
           checkUserToAdd.putExtra("email",editTextEmail.getText().toString().trim());
           checkUserToAdd.putExtra("password",editTextPassword.getText().toString().trim());
           checkUserToAdd.putExtra("age",editTextAge.getText().toString().trim());
           checkUserToAdd.putExtra("licencepoints",editTextLicencePoints.getText().toString().trim());
           checkUserToAdd.putExtra("type",typeofuser);
           checkUserToAdd.putExtra("typeofusertoadd",selectedOption);
           startActivity(checkUserToAdd);
       }
    }
}
