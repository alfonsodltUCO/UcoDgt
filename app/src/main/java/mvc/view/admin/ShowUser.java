package mvc.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.model.business.user.worker.WorkerDTO;

public class ShowUser extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user);
        Intent intentFound=getIntent();
        WorkerDTO worker = (WorkerDTO) getIntent().getSerializableExtra("worker");
    }
}
