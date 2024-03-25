package mvc.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ucodgt.R;

import org.mindrot.jbcrypt.BCrypt;

import mvc.controller.CheckLogIn;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    EditText editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setTheme(R.style.Theme_UcoDgt);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.checkLogIn);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id == R.id.checkLogIn) {
            goCheckLogIn();
        }
    }

    private void goCheckLogIn(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Intent intent = new Intent(MainActivity.this, CheckLogIn.class);
        intent.putExtra("email", email);
        Log.d("ADebeb",BCrypt.hashpw(password,BCrypt.gensalt()));
        intent.putExtra("password", password);
        startActivity(intent);
    }
}