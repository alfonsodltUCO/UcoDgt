package mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.admin.penalty.CheckPenaltyToDelete;
import mvc.view.admin.AdminActivity;

public class DeletePenaltyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etId;
    Button goDelete,goMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_penalty);
        goMain=findViewById(R.id.goMainMenu);
        goDelete=findViewById(R.id.deletePenalty);
        etId=findViewById(R.id.editTextId);
        goMain.setOnClickListener(this);
        goDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.deletePenalty){
            Intent goDelete = new Intent(DeletePenaltyActivity.this, CheckPenaltyToDelete.class);
            goDelete.putExtra("id",etId.getText().toString());
            startActivity(goDelete);
            finish();
        } else if (v.getId()==R.id.goMainMenu) {
            Intent intentGoMain = new Intent(DeletePenaltyActivity.this, AdminActivity.class);
            startActivity(intentGoMain);
            finish();
        }

    }
}
