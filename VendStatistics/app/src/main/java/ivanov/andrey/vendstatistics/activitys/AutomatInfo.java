package ivanov.andrey.vendstatistics.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.Drink;
import ivanov.andrey.vendstatistics.classes.DrinksAdapter;
import ivanov.andrey.vendstatistics.classes.MyApp;

public class AutomatInfo extends AppCompatActivity {

    Button buttonSaveData;
    ListView listView;
    MyApp myApp;
    String tableName;
    String drinks, drinksPrice;
    ArrayList<Drink> drinksList;
    DrinksAdapter adapter;
    Intent intent;
    public int[] piecesOfDrink;



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


        adapter = new DrinksAdapter(AutomatInfo.this, drinksList , R.layout.item_statistics);    //(AutomatInfo.this, R.layout.item_statistics, , from, to, MyApp.CURSUR_ADAPTER_FLAG);
        listView.setAdapter(adapter);


        LinearLayout linearLayout = new LinearLayout(this);
        ViewGroup viewGroup = linearLayout;
        View view = adapter.getView(0, null, viewGroup);
        TextView editText = (TextView) view.findViewById(R.id.text1);
        String drinkName = editText.getText().toString();
        Log.d(MyApp.LOG_TAG, "ИМЯ ПЕРВОГО НАПИТКА  - " + drinkName);

    }


    private void initVariables() {

        myApp = MyApp.getfInstance();
        intent = getIntent();
        tableName = intent.getStringExtra(MyApp.TABLE_NAME);
        drinks = intent.getStringExtra(MyApp.COLUMN_DRINKS);
        drinksPrice = intent.getStringExtra(MyApp.COLUMN_DRINKS_PRICE);
        drinksList = getDrinksList();
        piecesOfDrink = new int[drinksList.size()];

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
        buttonSaveData.setOnClickListener(onClickListener);
        listView = (ListView) findViewById(R.id.listViewAddData);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            View view;
            for (int i = 0; i < drinksList.size(); i++) {
                view = listView.getChildAt(i);
                EditText editTexPieces = (EditText) view.findViewById(R.id.pieces);

                if (!editTexPieces.getText().toString().isEmpty()) {
                    piecesOfDrink[i] = Integer.parseInt(editTexPieces.getText().toString());

                    Log.d(MyApp.LOG_TAG, "Колличетсво " + i + " напитка = " +  piecesOfDrink[i]);
                }
                else {
                    piecesOfDrink[i] = 0;
                    Log.d(MyApp.LOG_TAG, "Пустое значение");
                }
            }
        }
    };

}
