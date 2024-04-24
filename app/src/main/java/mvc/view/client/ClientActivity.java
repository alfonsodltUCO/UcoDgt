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

import mvc.controller.client.vehicle.CheckVehiclesToListForClient;
import mvc.view.MainActivity;
import mvc.view.client.penalty.IntroducePenaltyToFInd;

/**
 * Class used to handle all the client options
 * @author Alfonso de la Torre
 * The dni will be passed due do be able to manage the client
 * Also to not ask for each operation against his options
 */

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

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
        getMenuInflater().inflate(R.menu.clientmenu,menu);
        setContentView(R.layout.clientmain);
        closeSession=findViewById(R.id.closeSession);
        closeSession.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Method used to handle the client menu creation
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dni=getIntent().getStringExtra("dni");
        if(item.getItemId()==R.id.item1ClientListVehicles){
            Intent intent=new Intent(ClientActivity.this, CheckVehiclesToListForClient.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
            finish();

        }else if(item.getItemId()==R.id.item2ClientListPenalties){
            Intent intent=new Intent(ClientActivity.this, IntroducePenaltyToFInd.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
            finish();
        }else if(item.getItemId()==R.id.item3ClientPayPenalty){
            Intent intent=new Intent(ClientActivity.this, IntroducePenaltyToFInd.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
            finish();
        }else if(item.getItemId()==R.id.item4ClientCheckMyPoints){//user info
            Intent intent=new Intent(ClientActivity.this, IntroducePenaltyToFInd.class);
            intent.putExtra("dni",dni);
            startActivity(intent);
            finish();
        }
        return false;
    }

    /**
     * Method used to handle the event of button clicked
     */
    public void onClick(View v) {

        if(v.getId()==R.id.closeSession){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
