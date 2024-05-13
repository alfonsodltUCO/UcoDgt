package com.uco.ucodgt.mvc.view.worker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
 import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
 import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


 import com.uco.ucodgt.mvc.controller.worker.user.CheckWorkerInfo;
import com.uco.ucodgt.mvc.view.ImagePagerAdapter;
import com.uco.ucodgt.mvc.view.MainActivity;
import com.uco.ucodgt.mvc.view.worker.penalty.AddPenaltyActivity;
 import com.uco.ucodgt.mvc.view.worker.user.FindUserActivity;
import com.uco.ucodgt.mvc.view.worker.vehicle.GetVehiclePlate;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the activity for managing worker options.
 * Will allow worker to choose any action to do.
 * @author Alfonso de la torre
 */
public class WorkerActivity extends AppCompatActivity{

    String numberWorker;
    int currentPage = 0;
    Timer timer;
    int TOTALPAGES=4;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;

    /**
     * Method used to handle the view creation
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * Method used to create the menu of the worker
     */
    @SuppressLint("MissingInflatedId")
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(com.uco.ucodgt.R.menu.workermenu,menu);
        setContentView(com.uco.ucodgt.R.layout.workermain);

        ViewPager viewPager = findViewById(com.uco.ucodgt.R.id.viewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, new int[]{com.uco.ucodgt.R.drawable.carruselworker_1, com.uco.ucodgt.R.drawable.carruselworker_2});
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
            if(position==0){

                AlertDialog.Builder builder = new AlertDialog.Builder(WorkerActivity.this);
                TextView message = new TextView(WorkerActivity.this);
                message.setText("Querido policía, tu dedicación y valentía son un faro de esperanza en tiempos oscuros. Cada día, te enfrentas a desafíos difíciles con coraje y determinación, protegiendo a nuestra comunidad y defendiendo la justicia. Recuerda que tu trabajo es fundamental y que cada acción que realizas marca la diferencia. Tu compromiso y sacrificio son ejemplos de nobleza y servicio. Sigue adelante con orgullo, sabiendo que tu labor es invaluable y que tu comunidad te apoya y valora. ¡Gracias por tu servicio incansable y por ser un verdadero héroe en uniforme!");
                message.setTextSize(30);
                message.setPadding(20, 20, 20, 20);
                message.setTextColor(Color.BLACK);

                builder.setView(message);
                builder.create().show();
            }else if(position==1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WorkerActivity.this);
                TextView message = new TextView(WorkerActivity.this);
                message.setText("Recuerda que cada desafío que enfrentas es una oportunidad para crecer y aprender. Mantén tu mente abierta y tu corazón firme en tus valores. La resiliencia es tu mejor aliada; ante la adversidad, mantente fuerte y persevera. Confía en tu entrenamiento, en tu intuición y en el apoyo de tus compañeros. Cada día, tu dedicación y profesionalismo hacen del mundo un lugar más seguro. ¡Sigue adelante con determinación y orgullo en tu noble labor de proteger y servir!");
                message.setTextSize(30);
                message.setPadding(20, 20, 20, 20);
                message.setTextColor(Color.BLACK);
                builder.setView(message);
                builder.create().show();
            }
        });


        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Method used to handle the worker menu creation
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        numberWorker=getIntent().getStringExtra("numberWorker");
        if(item.getItemId()==com.uco.ucodgt.R.id.item1WorkerAddPenalty){

            Intent intent=new Intent(WorkerActivity.this,AddPenaltyActivity.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        }else if(item.getItemId()==com.uco.ucodgt.R.id.item2WorkerCheckVehicle){

            Intent intent=new Intent(WorkerActivity.this, GetVehiclePlate.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);



        }else if(item.getItemId()==com.uco.ucodgt.R.id.item3WorkerCheckUser){

            Intent intent=new Intent(WorkerActivity.this, FindUserActivity.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        }else if(item.getItemId()==com.uco.ucodgt.R.id.item5WorkerGetMyInfo){

            Intent intent=new Intent(WorkerActivity.this, CheckWorkerInfo.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        } else if (item.getItemId()==com.uco.ucodgt.R.id.item4WorkerCancelPenalty) {

            Intent intent=new Intent(WorkerActivity.this, GetVehiclePlate.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        } else if (item.getItemId()==com.uco.ucodgt.R.id.closeSession) {

            Intent intent=new Intent(WorkerActivity.this, MainActivity.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }
        return false;
    }

}
