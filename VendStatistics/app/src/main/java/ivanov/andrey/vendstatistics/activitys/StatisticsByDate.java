package ivanov.andrey.vendstatistics.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.Drink;
import ivanov.andrey.vendstatistics.classes.DrinksAdapter;
import ivanov.andrey.vendstatistics.classes.MyApp;

public class StatisticsByDate extends AppCompatActivity {

    ListView listViewStatistics;
    String tableName;
    int position;
    Intent intent;
    MyApp myApp;
    DrinksAdapter adapter;
    ArrayList<Drink> drinkList;
    String[] piecesOfDrinks;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_by_date);
        Toolbar toolbar = (Toolbar)findViewById(R.id.automat_info_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        initVariables();
        initView();
        ab.setTitle(getString(R.string.dataOn) + date);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new DrinksAdapter(StatisticsByDate.this, drinkList, R.layout.item_statistics_by_date, piecesOfDrinks);
        listViewStatistics.setAdapter(adapter);

    }

    private void initVariables() {

        myApp = MyApp.getfInstance();
        intent = getIntent();
        position = myApp.getPosition();
        tableName = myApp.getTableName();
        drinkList = myApp.getDrinksList();
        piecesOfDrinks = getPiecesOfDrinks();
        date = myApp.getRecord(tableName, position, MyApp.COLUMN_DATE);
    }

    private String[] getPiecesOfDrinks() {

        Cursor cursor;
        myApp.connectToDB();
        cursor = myApp.readAllData(tableName);
        cursor.moveToPosition(position);
        int columnCount = cursor.getColumnCount() - 2;
        String[] pieces = new String[columnCount];
        for(int i = 0; i < columnCount; i++) {
            int numberColumnIndex = cursor.getColumnIndex(MyApp.COLUMN_DRINK+i);

            if(!cursor.isNull(numberColumnIndex)) {
                pieces[i] = cursor.getString(numberColumnIndex);
            }
            else
                pieces[i] = "0";
        }
        return pieces;
    }


    private void initView() {

        listViewStatistics = (ListView) findViewById(R.id.listViewStatistics);
    }
}
