package ivanov.andrey.vendstatistics.activitys;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.FactoryIntent;
import ivanov.andrey.vendstatistics.classes.MyApp;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabAddAutomat;
    ListView listView;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    String[] from;
    int[] to;
    MyApp myApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        initView();
        setClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        cursor = myApp.readAllData(MyApp.TABLE_MAIN);
        adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.item_automat, cursor, from, to, MyApp.CURSUR_ADAPTER_FLAG);
        listView.setAdapter(adapter);
        myApp.closeConnectToDB();
    }

    private void initVariables() {

        from = new String[] { MyApp.COLUMN_NAME, MyApp.COLUMN_NUMBER };
        to = new int[] { R.id.automatName, R.id.automatNumber };
        myApp = MyApp.getfInstance();
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        fabAddAutomat = (FloatingActionButton) findViewById(R.id.fabAddAutomat);
        listView = (ListView) findViewById(R.id.automatListView);
    }

    private void setClickListener() {

        fabAddAutomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FactoryIntent.openNewAutomatActivity(MainActivity.this);

            }
        });
    }


}
