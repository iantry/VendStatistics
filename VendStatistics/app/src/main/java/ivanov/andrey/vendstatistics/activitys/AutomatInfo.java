package ivanov.andrey.vendstatistics.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.Drink;
import ivanov.andrey.vendstatistics.classes.DrinksAdapter;
import ivanov.andrey.vendstatistics.classes.MyApp;

public class AutomatInfo extends AppCompatActivity {

    Button buttonSaveData;
    ListView listView;
    MyApp myApp;
    Cursor cursor;
    String tableName;
    String drinks, drinksPrice;
    ArrayList<Drink> drinksList;
    DrinksAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automat_info);

        Toolbar toolbar = (Toolbar)findViewById(R.id.automat_info_toolbar);
        toolbar.setTitle(R.string.data_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();
        initViews();

    }


    @Override
    protected void onResume() {
        super.onResume();
        //  myApp.connectToDB();
        // cursor = myApp.readAllData(MyApp.TABLE_MAIN);

        adapter = new DrinksAdapter(AutomatInfo.this, drinksList);    //(AutomatInfo.this, R.layout.item_statistics, , from, to, MyApp.CURSUR_ADAPTER_FLAG);
        listView.setAdapter(adapter);
        //listView.setOnItemClickListener(itemClickListener);
        //  myApp.closeConnectToDB();
    }


    private void initVariables() {
        myApp = MyApp.getfInstance();
        intent = getIntent();
        tableName = intent.getStringExtra(MyApp.TABLE_NAME);
        drinks = intent.getStringExtra(MyApp.COLUMN_DRINKS);
        drinksPrice = intent.getStringExtra(MyApp.COLUMN_DRINKS_PRICE);
        drinksList = getDrinksList();
    }

    private ArrayList<Drink> getDrinksList() {

        ArrayList<Drink> drL = new ArrayList<>();
        String separator = this.getResources().getString(R.string.separator);
        String  dr[] = drinks.split(separator);
        String  drPr[] = drinksPrice.split(separator);

        for(int i = 0; i < dr.length; i++){

            drL.add(new Drink(dr[i], drPr[i]));
        }
        return drL;

    }

    private void initViews() {

        buttonSaveData = (Button)findViewById(R.id.buttonSaveData);
        listView = (ListView) findViewById(R.id.listViewAddData);
    }

}
