package mvc.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.CheckUserToAdd;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    Button checkAddUser;
    String typeofuser;
    String typeofusertoadd;
    EditText editTextName,editTextDni,editTextSurname,editTextAge,editTextPassword,editTextLicencePoints,editTextEmail;

    RadioButton rbuttonworker, rbuttonclient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_user);
        editTextAge=findViewById(R.id.editTextDateBirth);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextDni=findViewById(R.id.editTextDNI);
        editTextName=findViewById(R.id.editTextName);
        editTextSurname=findViewById(R.id.editTextSurname);
        rbuttonclient=findViewById(R.id.radioButtonClient);
        rbuttonclient=findViewById(R.id.radioButtonWorker);
        editTextLicencePoints=findViewById(R.id.editTextLicencePoints);
        editTextPassword=findViewById(R.id.editTextPassword);
        checkAddUser=findViewById(R.id.checkAdd);
        checkAddUser.setOnClickListener(this);
        Intent intent=getIntent();
        typeofuser =intent.getStringExtra("typeofuser");

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
           checkUserToAdd.putExtra("age",editTextAge.toString().trim());
           checkUserToAdd.putExtra("licencepoints",editTextLicencePoints.getText().toString().trim());
           checkUserToAdd.putExtra("type",typeofuser);
           startActivity(checkUserToAdd);
       }
    }
}
