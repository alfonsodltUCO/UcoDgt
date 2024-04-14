package mvc.view.admin;

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
import mvc.view.vehicle.AddVehicleActivity;
import mvc.view.vehicle.DeleteVehicleActivity;
import mvc.view.vehicle.GetVehiclePlate;
import mvc.view.MainActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{
    Button closeSession;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.adminmenu,menu);
       setContentView(R.layout.adminmain);
        closeSession=findViewById(R.id.closeSession);
        closeSession.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.item1AdminAddUser == item.getItemId()){
            Intent intentAddUser=new Intent(AdminActivity.this, AddUserActivity.class);
            startActivity(intentAddUser);
        }else if(R.id.item4AdminFindUser==item.getItemId()){
            Intent intentFindUser=new Intent(AdminActivity.this, FindUserActivity.class);
            startActivity(intentFindUser);
        } else if (R.id.item2AdminDeleteUser==item.getItemId()) {
            Intent intentDeleteUser=new Intent(AdminActivity.this, DeleteUserActivity.class);
            startActivity(intentDeleteUser);
        } else if (R.id.item3AdminListUsers==item.getItemId()) {
            Intent intentListUsersr=new Intent(AdminActivity.this, CheckUsersToList.class);
            startActivity(intentListUsersr);
        } else if (R.id.item8AdminFindVehicle==item.getItemId()) {
            Intent intentFindVehicle=new Intent(AdminActivity.this, GetVehiclePlate.class);
            startActivity(intentFindVehicle);
        }else if(R.id.item6AdminDeleteVehicle== item.getItemId()){
            Intent intentDeleteVehicle=new Intent(AdminActivity.this, DeleteVehicleActivity.class);
            startActivity(intentDeleteVehicle);
        }else if(R.id.item5AdminAddVehicle== item.getItemId()) {
            Intent intentAddVehicle = new Intent(AdminActivity.this, AddVehicleActivity.class);
            startActivity(intentAddVehicle);
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
