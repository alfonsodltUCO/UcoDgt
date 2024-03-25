package mvc.controller;

import static mvc.controller.commonFunctions.ForCheckUsertoAdd.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import mvc.view.AddUserActivity;

public class CheckUserToAdd extends AppCompatActivity {
    String name,surname,dni,email,password,typeofuserWhoDoTheAdd,age,licencepoints;
    String typeofuserAdded;
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
        typeofuserWhoDoTheAdd=intentReceived.getStringExtra("whodotheadd");
        typeofuserAdded=intentReceived.getStringExtra("tpeofusertoadd");
        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(surname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(licencepoints)){
            if(!checkDni(dni)){//no valid
                Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
                intentAdmin.putExtra("typeofuser",typeofuserWhoDoTheAdd);
                startActivity(intentAdmin);
                Toast.makeText(CheckUserToAdd.this,"No valid DNI", Toast.LENGTH_LONG).show();
            }else{//valid dni
               if(!checkNameAndSUrname(name,surname)){
                   Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
                   intentAdmin.putExtra("typeofuser",typeofuserWhoDoTheAdd);
                   startActivity(intentAdmin);
                   Toast.makeText(CheckUserToAdd.this,"No valid input for name/surname", Toast.LENGTH_LONG).show();
               }else{
                   if(!checkLicencePoints(licencepoints)){
                       Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
                       intentAdmin.putExtra("typeofuser",typeofuserWhoDoTheAdd);
                       startActivity(intentAdmin);//son 8 porque interpretamos que cuando te sacas el carnet te meten en el sistema
                       Toast.makeText(CheckUserToAdd.this,"Number have to be 8 exactly", Toast.LENGTH_LONG).show();
                   }else{

                   }
               }
            }
        }else{
            Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
            intentAdmin.putExtra("typeofuser",typeofuserWhoDoTheAdd);
            startActivity(intentAdmin);
            Toast.makeText(CheckUserToAdd.this,"Please fill all fields", Toast.LENGTH_LONG).show();

        }
    }
}
