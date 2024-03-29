package mvc.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
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
       setContentView(R.layout.adminmain);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item1AdminAddUser:
                Intent intentAddUser=new Intent(AdminActivity.this, AddUserActivity.class);
                startActivity(intentAddUser);
            case R.id.item4AdminFindUser:
                Intent intentFindUser=new Intent(AdminActivity.this, FindUserActivity.class);
                startActivity(intentFindUser);
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}
