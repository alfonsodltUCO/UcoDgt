package mvc.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ucodgt.R;

import mvc.controller.CheckLogIn;
import mvc.controller.SignUp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    Button signupButton;

    EditText editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.checkLogIn);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        signupButton=findViewById(R.id.signupButton);
        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id == R.id.checkLogIn) {
            goCheckLogIn();
        } else if (id == R.id.signupButton) {
            goSignUp();
        }
    }

    private void goCheckLogIn(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Intent intent = new Intent(MainActivity.this, CheckLogIn.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    private void goSignUp(){
        Intent intentSignUp = new Intent(MainActivity.this, SignUp.class);
        startActivity(intentSignUp);
    }

}