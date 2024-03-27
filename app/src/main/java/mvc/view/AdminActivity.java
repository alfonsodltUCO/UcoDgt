package mvc.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

public class AdminActivity extends AppCompatActivity{
    String typeofuser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Intent intent=getIntent();
        typeofuser=intent.getStringExtra("type");
    }

    public boolean onCreateOptionsMenu(Menu menu){
       Toast.makeText(AdminActivity.this,"Welcome ADMIN, here is your menu of the app",Toast.LENGTH_LONG).show();
       getMenuInflater().inflate(R.menu.adminmenu,menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item1AdminAddUser:
                Intent intentAddUser=new Intent(AdminActivity.this,AddUserActivity.class);
                intentAddUser.putExtra("typeofuser","admin");
                startActivity(intentAddUser);
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}
