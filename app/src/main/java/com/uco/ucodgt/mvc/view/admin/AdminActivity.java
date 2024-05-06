package com.uco.ucodgt.mvc.view.admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.viewpager.widget.ViewPager;
import android.app.AlertDialog;

import com.uco.ucodgt.R;
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
import com.uco.ucodgt.mvc.view.ImagePagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Activity class for the admin panel.
 * @author Alfonso de la torre
 */
public class AdminActivity extends AppCompatActivity{


    int currentPage = 0;
    Timer timer;
    int TOTALPAGES=4;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;
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
    @SuppressLint("ResourceType")
    public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(com.uco.ucodgt.R.menu.adminmenu,menu);
       setContentView(com.uco.ucodgt.R.layout.adminmain);

        ViewPager viewPager = findViewById(com.uco.ucodgt.R.id.viewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, new int[]{com.uco.ucodgt.R.drawable.cliente, com.uco.ucodgt.R.drawable.multa, R.drawable.deportivo});
        viewPager.setAdapter(adapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == TOTALPAGES-1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        adapter.setOnImageClickListener(position -> {

            if(position == 0) {

                final String[] options = {"Add User", "Delete User", "List Users", "Update Points Of User", "Find User"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Select an option for client")
                        .setItems(options, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0:
                                        Intent intentAddUser=new Intent(AdminActivity.this, AddUserActivity.class);
                                        startActivity(intentAddUser);
                                        finish();
                                        break;
                                    case 1:
                                        Intent intentDeleteUser=new Intent(AdminActivity.this, DeleteUserActivity.class);
                                        startActivity(intentDeleteUser);
                                        finish();
                                        break;
                                    case 2:
                                        Intent intentListUsers=new Intent(AdminActivity.this, CheckUsersToList.class);
                                        startActivity(intentListUsers);
                                        finish();
                                        break;
                                    case 3:
                                        Intent goUpdate=new Intent(AdminActivity.this, FindUserActivity.class);
                                        startActivity(goUpdate);
                                        finish();
                                        break;
                                    case 4:
                                        Intent intentFindUser=new Intent(AdminActivity.this, FindUserActivity.class);
                                        startActivity(intentFindUser);
                                        finish();
                                        break;
                                }
                            }
                        });
                builder.create().show();
            }else if (position==1){
                final String[] options = {"List Penalties", "Add Penalties", "Delete Penalties", "Find Penalty"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Select an option for penalties")
                        .setItems(options, (dialog, which) -> {

                            switch (which) {
                                case 0:
                                    Intent intentListPenalties = new Intent(AdminActivity.this, CheckPenaltiesToList.class);
                                    startActivity(intentListPenalties);
                                    finish();
                                    break;
                                case 1:

                                    Intent goAdd=new Intent(AdminActivity.this, AddPenaltyActivity.class);
                                    startActivity(goAdd);
                                    finish();
                                    break;
                                case 2:

                                    Intent goDelete = new Intent(AdminActivity.this, DeletePenaltyActivity.class);
                                    startActivity(goDelete);
                                    finish();

                                    break;
                                case 3:
                                    Intent intentFind = new Intent(AdminActivity.this, IntroducePenaltyForSearch.class);
                                    startActivity(intentFind);
                                    finish();
                                    break;

                            }
                        });

                builder.create().show();
            }else if(position==2){
                final String[] options = {"Find Vehicle", "Delete Vehicle", "Add vehicle", "List Vehicles", "Extend itv"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Select an option for vehicles")
                        .setItems(options, (dialog, which) -> {

                            switch (which) {
                                case 0:

                                    Intent intentFindVehicle=new Intent(AdminActivity.this, GetVehiclePlate.class);
                                    startActivity(intentFindVehicle);
                                    finish();
                                    break;
                                case 1:
                                    Intent intentDeleteVehicle=new Intent(AdminActivity.this, DeleteVehicleActivity.class);
                                    startActivity(intentDeleteVehicle);
                                    finish();
                                    break;
                                case 2:
                                    Intent intentAddVehicle = new Intent(AdminActivity.this, AddVehicleActivity.class);
                                    startActivity(intentAddVehicle);
                                    finish();
                                    break;
                                case 3:
                                    Intent intentFindVehicles = new Intent(AdminActivity.this, CheckVehiclesToList.class);
                                    startActivity(intentFindVehicles);
                                    finish();
                                    break;
                                case 4:
                                    Intent goExtend=new Intent(AdminActivity.this, GetVehiclePlate.class);
                                    startActivity(goExtend);
                                    finish();
                                    break;
                            }
                        });

                builder.create().show();
            }
        });
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

            Intent intentListUsers=new Intent(AdminActivity.this, CheckUsersToList.class);
            startActivity(intentListUsers);
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
        }else if(com.uco.ucodgt.R.id.closeSession==item.getItemId()){

            Intent goMain=new Intent(AdminActivity.this, MainActivity.class);
            startActivity(goMain);
            finish();
        }
        return false;
    }

}
