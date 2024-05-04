package com.uco.ucodgt.mvc.view.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.uco.ucodgt.R;
import com.uco.ucodgt.mvc.controller.client.user.CheckUserToFindForClient;
import com.uco.ucodgt.mvc.controller.client.vehicle.CheckVehiclesToListForClient;
import com.uco.ucodgt.mvc.view.ImagePagerAdapter;
import com.uco.ucodgt.mvc.view.MainActivity;
import com.uco.ucodgt.mvc.view.client.penalty.IntroducePenaltyToFind;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class used to handle all the client options
 * @author Alfonso de la Torre
 * The dni will be passed due do be able to manage the client
 * Also to not ask for each operation against his options
 */

public class ClientActivity extends AppCompatActivity {

    int currentPage = 0;
    Timer timer;
    int TOTALPAGES=4;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;
    Button closeSession;
    String dni;

    /**
     * Method used to handle the view creation
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * Method used to create the menu of the client
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(com.uco.ucodgt.R.menu.clientmenu,menu);
        setContentView(com.uco.ucodgt.R.layout.clientmain);

        ViewPager viewPager = findViewById(com.uco.ucodgt.R.id.viewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, new int[]{R.drawable.carrusel_1, R.drawable.carrusel_2, R.drawable.carrusel3});
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
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Method used to handle the client menu creation
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dni=getIntent().getStringExtra("dni");
        if(item.getItemId()==com.uco.ucodgt.R.id.item1ClientListVehicles){
            Intent intent=new Intent(ClientActivity.this, CheckVehiclesToListForClient.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
            finish();

        }else if(item.getItemId()==com.uco.ucodgt.R.id.item2ClientListPenalties){
            Intent intent=new Intent(ClientActivity.this, IntroducePenaltyToFind.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
            finish();
        }else if(item.getItemId()==com.uco.ucodgt.R.id.item3ClientPayPenalty){
            Intent intent=new Intent(ClientActivity.this, IntroducePenaltyToFind.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
            finish();
        }else if(item.getItemId()==com.uco.ucodgt.R.id.item4ClientCheckMyPoints){
            Intent intent=new Intent(ClientActivity.this, CheckUserToFindForClient.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
            finish();
        } else if (item.getItemId()==com.uco.ucodgt.R.id.closeSession) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
