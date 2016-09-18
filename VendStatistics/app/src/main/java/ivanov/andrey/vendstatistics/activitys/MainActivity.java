package ivanov.andrey.vendstatistics.activitys;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.DataBaseHelper;
import ivanov.andrey.vendstatistics.classes.FactoryIntent;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "logTag";
    public static String TABLE_NAME = "New_Table";

    Toolbar toolbar;
    FloatingActionButton fabAddAutomat;
    DataBaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setClickListener();

//        String rows = " (_id integer primary key autoincrement, ";

//        for (int i = 0; i < taybleRows.length; i++) {
//            if (i < (taybleRows.length - 1)) {
//                rows = rows + taybleRows[i] + " text, ";
//            } else {
//                rows = rows + taybleRows[i] + " text );";
//            }
//        }
//
//        Log.d(LOG_TAG, "-- new String --");
//        Log.d(LOG_TAG, rows);
//        //( _id integer primary key autoincrement, name text );

    }

    private void setClickListener() {

        fabAddAutomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Запуск активити добавления нового автомата", Toast.LENGTH_SHORT).show();

                FactoryIntent.openNewAutomatActivity(MainActivity.this);

            }
        });
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        fabAddAutomat = (FloatingActionButton) findViewById(R.id.fabAddAutomat);

        dbHelper = new DataBaseHelper(this);


    }



}
