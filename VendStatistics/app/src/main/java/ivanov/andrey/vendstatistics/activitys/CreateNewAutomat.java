package ivanov.andrey.vendstatistics.activitys;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.Automat;
import ivanov.andrey.vendstatistics.classes.Drink;
import ivanov.andrey.vendstatistics.classes.DrinksAdapter;
import ivanov.andrey.vendstatistics.classes.MyApp;

public class CreateNewAutomat extends AppCompatActivity {


    Button buttonAdd, buttonSave; //buttonRead;
    EditText editTextName, editTextNumber, editTextNameDrink, editTextPrice;
    ListView listViewDrinks;
    DrinksAdapter adapter;
    ArrayList<Drink> drinksList;
    ArrayList<String> listOfExistAutomat;
    String drinks, drinksPrice;
    Automat automat;
    MyApp myApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_automat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.new_automat_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Добавление нового автомата");


        initVariables();
        initViews();
        setAdapter();
        setClickListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // getMenuInflater().inflate(R.menu.menu_create_new_automat, menu);
        return true;
    }

    private void setAdapter() {

        adapter = new DrinksAdapter(this, drinksList);
        listViewDrinks.setAdapter(adapter);
    }

    private void initVariables() {

        drinksList = new ArrayList<>();
        drinks = "";
        myApp = MyApp.getfInstance();
        listOfExistAutomat = getExistAutomats();

    }

    private void setClickListener() {

        buttonAdd.setOnClickListener(onClickListener);
        buttonSave.setOnClickListener(onClickListener);


        //buttonRead.setOnClickListener(onClickListener);
    }

    private void initViews() {

        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextNumber = (EditText)findViewById(R.id.editTextNumber);
        editTextNameDrink = (EditText)findViewById(R.id.editTextNameDrink);
        editTextPrice = (EditText)findViewById(R.id.editTextPrice);
        listViewDrinks = (ListView)findViewById(R.id.listViewDrinks);

        // buttonRead = (Button)findViewById(R.id.buttonRead);

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buttonAdd:
                    addDrink();
                    break;
                case R.id.buttonSave:
                    if(drinksList.isEmpty()){
                        Toast.makeText(CreateNewAutomat.this, "Вы не добавили ни одного напитка", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        addAutomat();
                    }
                    break;
            }
        }
    };


    private void addDrink() {

        String name = editTextNameDrink.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
        if(name.isEmpty()) {
            Toast.makeText(CreateNewAutomat.this, "Вы не ввели название напитка", Toast.LENGTH_SHORT).show();
        }
        else if (price.isEmpty()) {
            Toast.makeText(CreateNewAutomat.this, "Вы не ввели цену напитка", Toast.LENGTH_SHORT).show();
        }
        else {
            Drink drink = new Drink(name, price);
            drinksList.add(drink);
            adapter.notifyDataSetChanged();
            editTextNameDrink.setText("");
            editTextPrice.setText("");
        }
    }

    private void addAutomat() {

        String name = editTextName.getText().toString().trim();
        String number =  editTextNumber.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(CreateNewAutomat.this, "Вы не ввели название автомата", Toast.LENGTH_SHORT).show();
        }
        else if(number.isEmpty()){
            Toast.makeText(CreateNewAutomat.this, "Вы не ввели номер автомата", Toast.LENGTH_SHORT).show();
        }
        else {

            if(listOfExistAutomat.contains("№ " + number)) {

                Toast.makeText(CreateNewAutomat.this, "Автомат с таким номером уже существует", Toast.LENGTH_SHORT).show();
            }
            else {

                myApp.connectToDB();

                automat = new Automat(name, number);
                ContentValues contentValues = new ContentValues();
                prepareDrinksForDB();
                contentValues.put(MyApp.COLUMN_NAME, name);
                contentValues.put(MyApp.COLUMN_NUMBER, "№ " + number);
                contentValues.put(MyApp.COLUMN_DRINKS, drinks);
                contentValues.put(MyApp.COLUMN_DRINKS_PRICE, drinksPrice);

                long rowId = myApp.insertToTable(MyApp.TABLE_MAIN, contentValues);
                Log.d(MyApp.LOG_TAG, "Автомат добавлен ID = " + rowId);
                myApp.createTable(getStringCreateTable());
                Toast.makeText(CreateNewAutomat.this, "Автомат добавлен", Toast.LENGTH_SHORT).show();
                myApp.closeConnectToDB();
                this.finish();
            }
        }
    }

//    private void readFromDB(){
//
//        myApp.connectToDB();
//
//        Log.d(MyApp.LOG_TAG, "--- Rows in listOfAutomats: ---");
//
//        Cursor c = myApp.readAllData(MyApp.TABLE_MAIN);
//
//        if (c.moveToFirst()) {
//
//            int idColIndex = c.getColumnIndex(MyApp.COLUMN_ID);
//            int nameColIndex = c.getColumnIndex(MyApp.COLUMN_NAME);
//            int numberColIndex = c.getColumnIndex(MyApp.COLUMN_NUMBER);
//            int drinksColIndex = c.getColumnIndex(MyApp.COLUMN_DRINKS);
//            int drinks_priceColIndex = c.getColumnIndex(MyApp.COLUMN_DRINKS_PRICE);
//            Log.d(MyApp.LOG_TAG, "Данные из БД");
//            do {
//                Log.d(MyApp.LOG_TAG,
//                                "  ID = " + c.getInt(idColIndex) +
//                                ", name = " + c.getString(nameColIndex) +
//                                ", number = " + c.getString(numberColIndex) +
//                                ", drinks = " + c.getString(drinksColIndex) +
//                                ", drinks_price = " + c.getString(drinks_priceColIndex));
//            } while (c.moveToNext());
//        } else
//            Log.d(MyApp.LOG_TAG, "0 rows");
//
//        myApp.closeConnectToDB();
//    }


    private ArrayList<String> getExistAutomats(){

        ArrayList<String> listOfAutomats = new ArrayList<>();
        myApp.connectToDB();
        Cursor cursor = myApp.readAllData(MyApp.TABLE_MAIN);

        if(cursor.moveToFirst()) {
            int numberColumnIndex = cursor.getColumnIndex(MyApp.COLUMN_NUMBER);
            Log.d(MyApp.LOG_TAG, "--- Список существующих таблиц ---");
            do {
                listOfAutomats.add(cursor.getString(numberColumnIndex));
                Log.d(MyApp.LOG_TAG, "Таблица = " + cursor.getString(numberColumnIndex));
            } while (cursor.moveToNext());
        }
        myApp.closeConnectToDB();

        return listOfAutomats;
    }


    private String getStringCreateTable() {

        String table = "Create table automat" + automat.getNumber() + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, date text, ";
        for(int i = 0; i < drinksList.size(); i++) {

            if(i < (drinksList.size() - 1)) {
                table = table + "drink" + i + " integer, ";
            }
            else table = table + "drink" + i + " integer );";
        }
        Log.d(MyApp.LOG_TAG, "строка сформированная для создания таблицы " + table);

        return table;
    }

//    void replaceSpaceInDrinksName() {
//
//        Drink dr;
//        for(int i = 0; i < drinksList.size(); i++){
//            dr = drinksList.get(i);
//            dr.setName(dr.getName().replace(" ", "_"));
//            drinksList.set(i, dr);
//        }
//    }

    private void prepareDrinksForDB() {


        Log.d(MyApp.LOG_TAG,"сформированные строки");
        drinks = drinksList.get(0).getName();
        drinksPrice = drinksList.get(0).getPrice();

        for(int i = 1; i < drinksList.size(); i++) {

            drinks = drinks + this.getResources().getString(R.string.separator) + drinksList.get(i).getName();
            drinksPrice = drinksPrice + this.getResources().getString(R.string.separator) + drinksList.get(i).getPrice();
        }
        Log.d(MyApp.LOG_TAG, drinks);
        Log.d(MyApp.LOG_TAG, drinksPrice);
    }

}
