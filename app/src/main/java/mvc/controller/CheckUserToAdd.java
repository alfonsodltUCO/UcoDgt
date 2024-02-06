package mvc.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.view.AddUserActivity;
import mvc.view.AdminActivity;

public class CheckUserToAdd extends AppCompatActivity {
    String name,surname,dni,email,password,typeofuserWhoDoTheAdd,age,licencepoints;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentReceived=getIntent();
        name=intentReceived.getStringExtra("name");
        surname=intentReceived.getStringExtra("surname");
        email=intentReceived.getStringExtra("email");
        password=intentReceived.getStringExtra("password");
        dni=intentReceived.getStringExtra("dni");
        age=intentReceived.getStringExtra("age");
        licencepoints=intentReceived.getStringExtra("licencepoints");
        typeofuserWhoDoTheAdd=intentReceived.getStringExtra("type");
        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(surname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(licencepoints)){
            if(checkDni(dni)==true){

            }
        }else{
            Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
            intentAdmin.putExtra("typeofuser",typeofuserWhoDoTheAdd);
            startActivity(intentAdmin);
            Toast.makeText(CheckUserToAdd.this,"Please fill all fields", Toast.LENGTH_SHORT).show();

        }
    }
    public boolean checkDni(String dni){
        return false;
    }
}
