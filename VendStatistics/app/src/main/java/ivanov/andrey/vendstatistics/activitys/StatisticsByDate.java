package ivanov.andrey.vendstatistics.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_by_date);

        initVariables();
        initView();
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
        tableName = myApp.getTableName();
        position = intent.getIntExtra("position", -1);
        drinkList = myApp.getDrinksList();
        piecesOfDrinks = getPiecesOfDrinks();
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
            pieces[i] = cursor.getString(numberColumnIndex);
        }
        return pieces;
    }


    private void initView() {

        listViewStatistics = (ListView) findViewById(R.id.listViewStatistics);
    }
}
