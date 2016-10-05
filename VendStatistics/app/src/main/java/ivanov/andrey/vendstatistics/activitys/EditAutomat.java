package ivanov.andrey.vendstatistics.activitys;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class EditAutomat extends AppCompatActivity {


    Button buttonAdd, buttonSave;
    EditText editTextName, editTextNumber, editTextNameDrink, editTextPrice;
    ListView listViewDrinks;
    DrinksAdapter adapter;
    ArrayList<Drink> drinksList;
    ArrayList<String> listOfExistAutomat;
    String drinks, drinksPrice;
    Automat automat;
    MyApp myApp;
    Boolean isEdit;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_automat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.new_automat_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.editAutomat);

        initVariables();
        initViews();
        setAdapter();
        setClickListener();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.item_edit:
                Toast.makeText(EditAutomat.this, "Edit - " + info.id, Toast.LENGTH_SHORT).show();
                index = info.position;
                isEdit = true;
                editTextNameDrink.setText(drinksList.get(index).getName());
                editTextPrice.setText(drinksList.get(index).getPrice());
                drinksList.remove(index);
                adapter.notifyDataSetChanged();

                return true;
            case R.id.item_delete:
                Toast.makeText(EditAutomat.this, "Delete - " + info.id, Toast.LENGTH_SHORT).show();
                drinksList.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void setAdapter() {

        adapter = new DrinksAdapter(this, drinksList, R.layout.item_drink, null);
        listViewDrinks.setAdapter(adapter);
    }

    private void initVariables() {

        drinks = "";
        myApp = MyApp.getfInstance();
        listOfExistAutomat = getExistAutomats();
        drinksList = new ArrayList<>();
        drinksList = myApp.getDrinksList();
        isEdit = false;
        index = -1;
    }

    private void setClickListener() {

        buttonAdd.setOnClickListener(onClickListener);
        buttonSave.setOnClickListener(onClickListener);
    }

    private void initViews() {

        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        editTextNameDrink = (EditText)findViewById(R.id.editTextNameDrink);
        editTextPrice = (EditText)findViewById(R.id.editTextPrice);
        listViewDrinks = (ListView)findViewById(R.id.listViewDrinks);
        registerForContextMenu(listViewDrinks);


        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextNumber = (EditText)findViewById(R.id.editTextNumber);
        myApp.connectToDB();
        editTextName.setText(myApp.getRecord(MyApp.TABLE_MAIN, myApp.getPosition(), MyApp.COLUMN_NAME));
        editTextNumber.setText(myApp.getRecord(MyApp.TABLE_MAIN, myApp.getPosition(), MyApp.COLUMN_NUMBER));
        myApp.closeConnectToDB();

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonAdd:
                    addDrink();
                    break;
                case R.id.buttonSave:
                    if (drinksList.isEmpty()) {
                        Toast.makeText(EditAutomat.this, "Вы не добавили ни одного напитка", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(EditAutomat.this, "Вы не ввели название напитка", Toast.LENGTH_SHORT).show();
        }
        else if (price.isEmpty()) {
            Toast.makeText(EditAutomat.this, "Вы не ввели цену напитка", Toast.LENGTH_SHORT).show();
        }
        else {
            Drink drink = new Drink(name, price);
            if(!isEdit) drinksList.add(drink);
            else drinksList.add(index, drink);
            adapter.notifyDataSetChanged();
            editTextNameDrink.setText("");
            editTextPrice.setText("");
            index = -1;
            isEdit = false;
        }
    }

    private void editAutomat() {

        String name = editTextName.getText().toString().trim();
        String number =  editTextNumber.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(EditAutomat.this, "Вы не ввели название автомата", Toast.LENGTH_SHORT).show();
        }
        else if(number.isEmpty()){
            Toast.makeText(EditAutomat.this, "Вы не ввели номер автомата", Toast.LENGTH_SHORT).show();
        }
        else {

            if (listOfExistAutomat.contains("№ " + number)) {

                Toast.makeText(EditAutomat.this, "Автомат с таким номером уже существует", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void addAutomat() {

        String name = editTextName.getText().toString().trim();
        String number =  editTextNumber.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(EditAutomat.this, "Вы не ввели название автомата", Toast.LENGTH_SHORT).show();
        }
        else if(number.isEmpty()){
            Toast.makeText(EditAutomat.this, "Вы не ввели номер автомата", Toast.LENGTH_SHORT).show();
        }
        else {

            if(listOfExistAutomat.contains("№ " + number)) {

                Toast.makeText(EditAutomat.this, "Автомат с таким номером уже существует", Toast.LENGTH_SHORT).show();
            }
            else {

                myApp.connectToDB();

                automat = new Automat(name, number);
                ContentValues contentValues = new ContentValues();
                prepareDrinksForDB();
                contentValues.put(MyApp.COLUMN_NAME, name);
                contentValues.put(MyApp.COLUMN_NUMBER, "№ " +  number);
                contentValues.put(MyApp.COLUMN_DRINKS, drinks);
                contentValues.put(MyApp.COLUMN_DRINKS_PRICE, drinksPrice);

                long rowId = myApp.insertToTable(MyApp.TABLE_MAIN, contentValues);
                Log.d(MyApp.LOG_TAG, "Автомат добавлен ID = " + rowId);
                myApp.createTable(getStringCreateTable());
                Toast.makeText(EditAutomat.this, "Автомат добавлен", Toast.LENGTH_SHORT).show();
                myApp.closeConnectToDB();
                this.finish();
            }
        }
    }


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

        String table = "Create table automat" + automat.getNumber() + " ( " + MyApp.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MyApp.COLUMN_DATE + " text, ";
        for(int i = 0; i < drinksList.size(); i++) {

            if(i < (drinksList.size() - 1)) {
                table = table + MyApp.COLUMN_DRINK + i + " integer, ";
            }
            else table = table + MyApp.COLUMN_DRINK + i + " integer );";
        }
        Log.d(MyApp.LOG_TAG, "строка сформированная для создания таблицы " + table);

        return table;
    }


    private void prepareDrinksForDB() {


        Log.d(MyApp.LOG_TAG,"сформированные строки");
        drinks = drinksList.get(0).getName();
        drinksPrice = drinksList.get(0).getPrice();

        for(int i = 1; i < drinksList.size(); i++) {

            drinks = drinks + this.getString(R.string.separator) + drinksList.get(i).getName();
            drinksPrice = drinksPrice + this.getString(R.string.separator) + drinksList.get(i).getPrice();
        }
        Log.d(MyApp.LOG_TAG, drinks);
        Log.d(MyApp.LOG_TAG, drinksPrice);
    }

}
