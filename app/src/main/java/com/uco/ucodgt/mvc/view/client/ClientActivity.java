package com.uco.ucodgt.mvc.view.client;

import android.app.AlertDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


 import com.uco.ucodgt.mvc.controller.client.user.CheckUserToFindForClient;
import com.uco.ucodgt.mvc.controller.client.vehicle.CheckVehiclesToListForClient;
import com.uco.ucodgt.mvc.view.ImagePagerAdapter;
import com.uco.ucodgt.mvc.view.MainActivity;

import com.uco.ucodgt.mvc.view.client.penalty.IntroducePenaltyToFind;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

/**
 * Class used to handle all the client options
 * @author Alfonso de la Torre
 * The dni will be passed due do be able to manage the client
 * Also to not ask for each operation against his options
 */

public class ClientActivity extends AppCompatActivity implements View.OnClickListener{

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
        GifImageView gif=findViewById(com.uco.ucodgt.R.id.news);
        gif.setOnClickListener(this);
        ViewPager viewPager = findViewById(com.uco.ucodgt.R.id.viewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, new int[]{com.uco.ucodgt.R.drawable.carrusel_1, com.uco.ucodgt.R.drawable.carrusel_2, com.uco.ucodgt.R.drawable.verano});
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
                final String[] options = {"Yes", "No"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);
                builder.setTitle("You are going to be redirected to Internet, are you sure you want to keep going?")
                        .setItems(options, (dialog, which) -> {

                            switch (which) {
                                case 0:
                                    String url = "https://www.dgt.es/muevete-con-seguridad/evita-conductas-de-riesgo/Conducir-con-sueno-o-cansancio";
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);

                                    break;
                                case 1:
                                    dialog.dismiss();
                                    break;

                            }
                        });

                builder.create().show();

            }else if (position==1){
                final String[] options = {"Yes", "No"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);
                builder.setTitle("You are going to be redirected to Internet, are you sure you want to keep going?")
                        .setItems(options, (dialog, which) -> {

                            switch (which) {
                                case 0:
                                    String url = "https://www.dgt.es/muevete-con-seguridad/evita-conductas-de-riesgo/consumo-de-alcohol/";
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);

                                    break;
                                case 1:
                                    dialog.dismiss();
                                    break;

                            }
                        });

                builder.create().show();

            }else if(position==2){

                final String[] options = {"Yes", "No"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);
                builder.setTitle("You are going to be redirected to Internet, are you sure you want to keep going?")
                        .setItems(options, (dialog, which) -> {

                            switch (which) {
                                case 0:
                                    String url = "https://revista-org2.dgt.es/es/multimedia/video/2021/06JUNIO/0628-Campana-Verano-2021.shtml";
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);

                                    break;
                                case 1:
                                    dialog.dismiss();
                                    break;

                            }
                        });

                builder.create().show();

            }
        });
        onMenuOpened(com.uco.ucodgt.R.id.optionUsers,menu);
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


        }else if(item.getItemId()==com.uco.ucodgt.R.id.item2ClientListPenalties){
            Intent intent=new Intent(ClientActivity.this, IntroducePenaltyToFind.class);
            intent.putExtra("dni",dni);
            startActivity(intent);

        }else if(item.getItemId()==com.uco.ucodgt.R.id.item3ClientPayPenalty){
            Intent intent=new Intent(ClientActivity.this, IntroducePenaltyToFind.class);
            intent.putExtra("dni",dni);
            startActivity(intent);

        }else if(com.uco.ucodgt.R.id.closeSession==item.getItemId()){

            Intent goMain=new Intent(ClientActivity.this, MainActivity.class);
            startActivity(goMain);

        }else if(com.uco.ucodgt.R.id.item4ClientGetMyInfo==item.getItemId()){
            Intent intent=new Intent(ClientActivity.this, CheckUserToFindForClient.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==com.uco.ucodgt.R.id.news){
            final String[] options = {"Yes", "No"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);
            builder.setTitle("You are going to be redirected to Internet, are you sure you want to keep going?")
                    .setItems(options, (dialog, which) -> {

                        switch (which) {
                            case 0:
                                String url = "https://www.dgt.es/comunicacion/noticias/";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);

                                break;
                            case 1:
                                dialog.dismiss();
                                break;

                        }
                    });

            builder.create().show();
        }
    }
}
