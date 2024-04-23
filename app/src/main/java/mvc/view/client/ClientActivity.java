package mvc.view.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import mvc.controller.admin.CheckUsersToList;
import mvc.controller.penalty.CheckPenaltiesToList;
import mvc.controller.vehicle.CheckVehiclesToList;
import mvc.view.MainActivity;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.penalty.AddPenaltyActivity;
import mvc.view.admin.penalty.IntroducePenaltyForSearch;
import mvc.view.admin.user.AddUserActivity;
import mvc.view.admin.user.DeleteUserActivity;
import mvc.view.admin.user.FindUserActivity;
import mvc.view.admin.vehicle.AddVehicleActivity;
import mvc.view.admin.vehicle.DeleteVehicleActivity;
import mvc.view.admin.vehicle.GetVehiclePlate;

/**
 * This is used to handle all the client options
 */
public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

    Button closeSession;
    String dni;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        dni=getIntent().getStringExtra("dni");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.clientmenu,menu);
        setContentView(R.layout.clientmain);
        closeSession=findViewById(R.id.closeSession);
        closeSession.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item1ClientListVehicles){

        }else if(item.getItemId()==R.id.item2ClientListPenalties){

        }else if(item.getItemId()==R.id.item3ClientPayPenalty){

        }else if(item.getItemId()==R.id.item4ClientCheckMyPoints){//user info

        }else if(item.getItemId()==R.id.item5ClientAskForPoints){

        }
        return false;
    }

    public void onClick(View v) {

        if(v.getId()==R.id.closeSession){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
