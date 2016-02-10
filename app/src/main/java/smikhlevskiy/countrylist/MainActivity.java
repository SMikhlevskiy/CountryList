package smikhlevskiy.countrylist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


import smikhlevskiy.countrylist.adapters.CountrysAdapter;
import smikhlevskiy.countrylist.model.Country;
import smikhlevskiy.countrylist.threads.GetCountrysAsyncTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private CountrysAdapter countrysAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Handler mainHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                ArrayList<Country> countrys = (ArrayList<Country>) msg.obj;
                if (countrys == null) return;

                countrysAdapter.setCountys(countrys);
                countrysAdapter.notifyDataSetChanged();


                super.handleMessage(msg);
            }
        };

        ListView listView = (ListView) findViewById(R.id.listView);
        countrysAdapter= new CountrysAdapter(this);
        listView.setAdapter(countrysAdapter);

        (new GetCountrysAsyncTask(this,mainHandler)).execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_main, menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
