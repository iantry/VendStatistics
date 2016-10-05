package ivanov.andrey.vendstatistics.activitys;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.Drink;
import ivanov.andrey.vendstatistics.classes.DrinksAdapter;
import ivanov.andrey.vendstatistics.classes.FactoryIntent;
import ivanov.andrey.vendstatistics.classes.MyApp;

public class AutomatInfo extends AppCompatActivity {

    Button buttonSaveData;
    ListView listView;
    MyApp myApp;
    String tableName;
    ArrayList<Drink> drinksList;
    DrinksAdapter adapter;
    public int[] piecesOfDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automat_info);

        Toolbar toolbar = (Toolbar)findViewById(R.id.automat_info_toolbar);
        toolbar.setTitle(R.string.data_add);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        initVariables();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = new DrinksAdapter(AutomatInfo.this, drinksList , R.layout.item_statistics, null);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_automat_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.listOfStatistics:
                FactoryIntent.openListOfStatistics(AutomatInfo.this);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private void initVariables() {

        myApp = MyApp.getfInstance();
        tableName = myApp.getTableName();
        drinksList = myApp.getDrinksList();
        piecesOfDrink = new int[drinksList.size()];

    }

    private void initViews() {

        buttonSaveData = (Button)findViewById(R.id.buttonSaveData);
        buttonSaveData.setOnClickListener(onClickListener);
        listView = (ListView) findViewById(R.id.listViewAddData);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String day, month, year, date;
            Calendar calendar = Calendar.getInstance();
            day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            month = String.valueOf(1 + calendar.get(Calendar.MONTH));
            year = String.valueOf(calendar.get(Calendar.YEAR));
            date = day + "." + month + "." + year;

            ContentValues contentValues = new ContentValues();
            contentValues.put(MyApp.COLUMN_DATE, date);

            View view;
            for (int i = 0; i < drinksList.size(); i++) {
                view = listView.getChildAt(i);
                EditText editTexPieces = (EditText) view.findViewById(R.id.pieces);

                if (!editTexPieces.getText().toString().isEmpty()) {
                    piecesOfDrink[i] = Integer.parseInt(editTexPieces.getText().toString());
                    contentValues.put(MyApp.COLUMN_DRINK + i, piecesOfDrink[i]);
                    Log.d(MyApp.LOG_TAG, "Колличетсво " + i + " напитка = " +  piecesOfDrink[i]);
                }
                else {
                    piecesOfDrink[i] = 0;
                    contentValues.put(MyApp.COLUMN_DRINK + i, piecesOfDrink[i]);
                    Log.d(MyApp.LOG_TAG, "Пустое значение");
                }
            }
            myApp.connectToDB();
            myApp.insertToTable(tableName, contentValues);
            myApp.closeConnectToDB();
        }
    };

}
