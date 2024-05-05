package com.uco.ucodgt.mvc.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uco.ucodgt.mvc.controller.admin.users.CheckUsersToList;
import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltiesToList;
import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckVehiclesToList;
import com.uco.ucodgt.mvc.view.admin.penalty.AddPenaltyActivity;
import com.uco.ucodgt.mvc.view.admin.penalty.DeletePenaltyActivity;
import com.uco.ucodgt.mvc.view.admin.penalty.IntroducePenaltyForSearch;
import com.uco.ucodgt.mvc.view.admin.user.AddUserActivity;
import com.uco.ucodgt.mvc.view.admin.user.DeleteUserActivity;
import com.uco.ucodgt.mvc.view.admin.user.FindUserActivity;
import com.uco.ucodgt.mvc.view.admin.vehicle.AddVehicleActivity;
import com.uco.ucodgt.mvc.view.admin.vehicle.DeleteVehicleActivity;
import com.uco.ucodgt.mvc.view.admin.vehicle.GetVehiclePlate;
import com.uco.ucodgt.mvc.view.MainActivity;
/**
 * Activity class for the admin panel.
 * @author Alfonso de la torre
 */
public class AdminActivity extends AppCompatActivity implements View.OnClickListener{
    Button closeSession;
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     * @see AppCompatActivity
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     */
    public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(com.uco.ucodgt.R.menu.adminmenu,menu);
       setContentView(com.uco.ucodgt.R.layout.adminmain);
        closeSession=findViewById(com.uco.ucodgt.R.id.closeSession);
        closeSession.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);

    }
    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed,
     * true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(com.uco.ucodgt.R.id.item1AdminAddUser == item.getItemId()){

            Intent intentAddUser=new Intent(AdminActivity.this, AddUserActivity.class);
            startActivity(intentAddUser);
            finish();
        }else if(com.uco.ucodgt.R.id.item4AdminFindUser==item.getItemId()){

            Intent intentFindUser=new Intent(AdminActivity.this, FindUserActivity.class);
            startActivity(intentFindUser);
            finish();

        } else if (com.uco.ucodgt.R.id.item2AdminDeleteUser==item.getItemId()) {

            Intent intentDeleteUser=new Intent(AdminActivity.this, DeleteUserActivity.class);
            startActivity(intentDeleteUser);
            finish();

        } else if (com.uco.ucodgt.R.id.item3AdminListUsers==item.getItemId()) {

            Intent intentListUsersr=new Intent(AdminActivity.this, CheckUsersToList.class);
            startActivity(intentListUsersr);
            finish();

        } else if (com.uco.ucodgt.R.id.item8AdminFindVehicle==item.getItemId()) {

            Intent intentFindVehicle=new Intent(AdminActivity.this, GetVehiclePlate.class);
            startActivity(intentFindVehicle);
            finish();

        }else if(com.uco.ucodgt.R.id.item6AdminDeleteVehicle== item.getItemId()){

            Intent intentDeleteVehicle=new Intent(AdminActivity.this, DeleteVehicleActivity.class);
            startActivity(intentDeleteVehicle);
            finish();

        }else if(com.uco.ucodgt.R.id.item5AdminAddVehicle== item.getItemId()) {

            Intent intentAddVehicle = new Intent(AdminActivity.this, AddVehicleActivity.class);
            startActivity(intentAddVehicle);
            finish();

        }else if(com.uco.ucodgt.R.id.item7AdminListVehicles== item.getItemId()) {

            Intent intentFindVehicles = new Intent(AdminActivity.this, CheckVehiclesToList.class);
            startActivity(intentFindVehicles);
            finish();

        } else if (com.uco.ucodgt.R.id.item9AdminListPenalties==item.getItemId()) {

            Intent intentListPenalties = new Intent(AdminActivity.this, CheckPenaltiesToList.class);
            startActivity(intentListPenalties);
            finish();

        } else if (com.uco.ucodgt.R.id.item12AdminFindPenalty==item.getItemId()) {

            Intent intentFind = new Intent(AdminActivity.this, IntroducePenaltyForSearch.class);
            startActivity(intentFind);
            finish();

        } else if(com.uco.ucodgt.R.id.item11AdminDeletePenalty==item.getItemId()){

            Intent goDelete=new Intent(AdminActivity.this, DeletePenaltyActivity.class);
            startActivity(goDelete);
            finish();

        }else if(com.uco.ucodgt.R.id.item10AdminAddPenalty== item.getItemId()){

            Intent goAdd=new Intent(AdminActivity.this, AddPenaltyActivity.class);
            startActivity(goAdd);
            finish();
        } else if (com.uco.ucodgt.R.id.item13AdminUpdateUserPoints==item.getItemId()) {

            Intent goUpdate=new Intent(AdminActivity.this, FindUserActivity.class);
            startActivity(goUpdate);
            finish();
        } else if (com.uco.ucodgt.R.id.item14AdminExtendItv==item.getItemId()) {

            Intent goExtend=new Intent(AdminActivity.this, GetVehiclePlate.class);
            startActivity(goExtend);
            finish();
        }
        return false;
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.closeSession){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
