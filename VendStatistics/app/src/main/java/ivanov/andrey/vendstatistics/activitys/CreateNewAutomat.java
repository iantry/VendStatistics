package ivanov.andrey.vendstatistics.activitys;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.Automat;
import ivanov.andrey.vendstatistics.classes.DataBaseHelper;
import ivanov.andrey.vendstatistics.classes.Drink;
import ivanov.andrey.vendstatistics.classes.DrinksAdapter;

public class CreateNewAutomat extends AppCompatActivity {


    public static final String LOG_TAG = "myLog";

    Button buttonAdd, buttonSave, buttonRead;
    EditText editTextName, editTextNumber, editTextNameDrink, editTextPrice;
    ListView listViewDrinks;
    DrinksAdapter adapter;
    ArrayList<Drink> drinksList;
    String drinks, drinksPrice;
    Automat automat;
    DataBaseHelper dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_automat);

        initVariables();
        initViews();
        setAdapter();
        setClickListener();

    }

    private void setAdapter() {

        adapter = new DrinksAdapter(this, drinksList);
        listViewDrinks.setAdapter(adapter);
    }

    private void initVariables() {

        drinksList = new ArrayList<>();
        dbHelper = new DataBaseHelper(this);
        drinks = "";

         db = dbHelper.getWritableDatabase();
    }

    private void setClickListener() {

        buttonAdd.setOnClickListener(onClickListener);
        buttonSave.setOnClickListener(onClickListener);
        buttonRead.setOnClickListener(onClickListener);
    }

    private void initViews() {

        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextNumber = (EditText)findViewById(R.id.editTextNumber);
        editTextNameDrink = (EditText)findViewById(R.id.editTextNameDrink);
        editTextPrice = (EditText)findViewById(R.id.editTextPrice);
        listViewDrinks = (ListView)findViewById(R.id.listViewDrinks);

        buttonRead = (Button)findViewById(R.id.buttonRead);

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buttonAdd:
                    addDrink();
                    break;
                case R.id.buttonSave:
                    addAutomat();
                    break;
                case R.id.buttonRead:
                    readFromDB();
                    break;
            }
        }
    };


    private void readFromDB(){
        Log.d(LOG_TAG, "--- Rows in listOfAutomats: ---");

        Cursor c = db.query("listOfAutomats", null, null, null, null, null, null);

        if (c.moveToFirst()) {


            int idColIndex = c.getColumnIndex("i_d");
            int nameColIndex = c.getColumnIndex("name");
            int numberColIndex = c.getColumnIndex("number");
            int drinksColIndex = c.getColumnIndex("drinks");
            int drinks_priceColIndex = c.getColumnIndex("drinks_price");
            Log.d(LOG_TAG, "Данные из БД");
            do {
// получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", number = " + c.getString(numberColIndex) +
                                ", drinks = " + c.getString(drinksColIndex) +
                                ", drinks_price = " + c.getString(drinks_priceColIndex));
// переход на следующую строку
// а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
    }


    private void addAutomat() {

        String name = editTextName.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(CreateNewAutomat.this, "Вы не ввели название автомата", Toast.LENGTH_SHORT).show();
        }
        else if(number.isEmpty()){
            Toast.makeText(CreateNewAutomat.this, "Вы не ввели номер автомата", Toast.LENGTH_SHORT).show();
        }
        else {
            automat = new Automat(name, number);
            ContentValues contentValues = new ContentValues();

            createRowDrinks();

            contentValues.put("name", automat.getName());
            contentValues.put("number", automat.getNumber());
            contentValues.put("drinks", drinks);
            contentValues.put("drinks_price", drinksPrice);

            long rowId = db.insert("listOfAutomats", null , contentValues);

            Log.d(LOG_TAG, "Автомат добавлен ID = " + rowId);

            db.execSQL(createTable());

        }

    }

    private String createTable() {

        String table = "Create table automat" + automat.getNumber() + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, date text, ";

        replaceSpaceInDrinksName();

        for(int i = 0; i < drinksList.size(); i++) {

            if(i < (drinksList.size() - 1)) {
                table = table + drinksList.get(i).getName() + " integer, ";
            }
            else table = table + drinksList.get(i).getName() + " integer );";
        }

        Log.d(LOG_TAG, "строка сформированная для создания таблицы " + table);
        return table;
    }

     void replaceSpaceInDrinksName() {

         Drink dr;
         for(int i = 0; i < drinksList.size(); i++){
             dr = drinksList.get(i);
             dr.setName(dr.getName().replace(" ", "_"));
             drinksList.set(i, dr);
         }

     }

    private void createRowDrinks() {

        Log.d(LOG_TAG,"сформированные строки");
        drinks = drinksList.get(0).getName();
        drinksPrice = drinksList.get(0).getPrice();

        for(int i = 1; i < drinksList.size(); i++) {

            drinks = drinks + "|" + drinksList.get(i).getName();
            drinksPrice = drinksPrice + "|" + drinksList.get(i).getPrice();
        }

        Log.d(LOG_TAG, drinks);
        Log.d(LOG_TAG, drinksPrice);

    }

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
}
