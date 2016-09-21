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
import ivanov.andrey.vendstatistics.classes.MyApplication;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "myLog";
    private static final int FLAG = 1233217;

    Toolbar toolbar;
    FloatingActionButton fabAddAutomat;
    ListView listView;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    String[] from;
    int[] to;
    MyApplication myApplication;


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

        cursor = myApplication.readExistAutomats(MainActivity.this);
        adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.item_automat, cursor, from, to, FLAG);
        listView.setAdapter(adapter);
        myApplication.closeConnectToDB();
    }

    private void initVariables() {

        from = new String[] {"name", "number" };
        to = new int[] { R.id.automatName, R.id.automatNumber };
        myApplication = MyApplication.getfInstance();
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
