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
    ArrayList<Drink> oldDrinkList , newDrinkList;
    ArrayList<String> listOfExistAutomat;
    String drinks, drinksPrice;
    Automat automat;
    MyApp myApp;
    Boolean isEdit , isAddNewDrink, isDeleteDrink;
    int index;
    String currentNumber, currentName;
    ArrayList<Integer> removePosition;



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

    private void setAdapter() {

        adapter = new DrinksAdapter(this, newDrinkList, R.layout.item_drink, null);
        listViewDrinks.setAdapter(adapter);
    }

    private void initVariables() {

        drinks = "";
        myApp = MyApp.getfInstance();
        listOfExistAutomat = getExistAutomats();
        oldDrinkList = new ArrayList<>();
        oldDrinkList = myApp.getDrinksList();
        newDrinkList = new ArrayList<>(oldDrinkList);
        isEdit = false;
        index = -1;
        isAddNewDrink = false;
        isDeleteDrink = false;
        myApp.connectToDB();
        currentName = myApp.getRecord(MyApp.TABLE_MAIN, myApp.getPosition(), MyApp.COLUMN_NAME);
        currentNumber = myApp.getRecord(MyApp.TABLE_MAIN, myApp.getPosition(), MyApp.COLUMN_NUMBER);
        myApp.closeConnectToDB();
        removePosition = new ArrayList<>();
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
        editTextName.setText(currentName);
        editTextNumber.setText(currentNumber);

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
                editTextNameDrink.setText(newDrinkList.get(index).getName());
                editTextPrice.setText(newDrinkList.get(index).getPrice());
                newDrinkList.remove(index);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.item_delete:
                isDeleteDrink = true;
                Toast.makeText(EditAutomat.this, "Delete - " + info.id, Toast.LENGTH_SHORT).show();
                newDrinkList.remove(info.position);
                removePosition.add(info.position);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonAdd:
                    addDrink();
                    break;
                case R.id.buttonSave:
                    editExistAutomat();
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

            if(!isEdit) {
                newDrinkList.add(drink);
                isAddNewDrink = true;
            }
            else newDrinkList.add(index, drink);

            adapter.notifyDataSetChanged();
            editTextNameDrink.setText("");
            editTextPrice.setText("");
            index = -1;
            isEdit = false;
        }
    }

    private void editExistAutomat() {

        String name = editTextName.getText().toString().trim();
        String number =  editTextNumber.getText().toString().trim();

        if(name.isEmpty()) {
            Toast.makeText(EditAutomat.this, "Вы не ввели название автомата", Toast.LENGTH_SHORT).show();
        }
        else if(number.isEmpty()){
            Toast.makeText(EditAutomat.this, "Вы не ввели номер автомата", Toast.LENGTH_SHORT).show();
        }
        else {
            if(currentNumber.equals(number))
                editInDB(name, number);
            else if(listOfExistAutomat.contains(number)) Toast.makeText(EditAutomat.this, "Автомат с таким номером уже существует", Toast.LENGTH_SHORT).show();
            else editInDB(name, number);
        }
    }


    public void editInDB(String name, String number) {

        myApp.connectToDB();
        automat = new Automat(name, number.replace("№ ", ""));
        ContentValues contentValues = new ContentValues();
        prepareDrinksForDB();
        contentValues.put(MyApp.COLUMN_NAME, name);
        contentValues.put(MyApp.COLUMN_NUMBER, number);
        contentValues.put(MyApp.COLUMN_DRINKS, drinks);
        contentValues.put(MyApp.COLUMN_DRINKS_PRICE, drinksPrice);

        // обнавление таблицы с автоматами
        myApp.updateRow(MyApp.TABLE_MAIN, contentValues, MyApp.COLUMN_NUMBER + " = ?", new String[] {currentNumber});

        // если некоторые напитки были удаленны, то из таблицы нужно удалить столбцы
        // но SQLite не поддерживает удаление столбцов, поэтому создается временная таблица, туда копируются данные из старой таблицы
        // затем удалятся старая таблица, создается новая таблица с новым колличеством столбцов, в которой учитываются и старые оставшиеся столбцы и новые
        // и копируются туда данные с временной таблицы в те столбцы, которые остались от старой таблицы. В конце временная таблица удаляется.
        if(isDeleteDrink) {

            String tempTable = "tempAutomat" + automat.getNumber();
            String newTable = "automat" + automat.getNumber();
            myApp.createTable(getStringCreateTable(true));
            myApp.copyTableInNewTable(getNameOfTable(currentNumber), tempTable);
            myApp.deleteTable(newTable);
            myApp.createTable(getStringCreateTable(false));
            myApp.copyTableInNewTable(tempTable, getAvailableColumns(), newTable, getNewColumns());
            myApp.deleteTable(tempTable);

        }

        // если добавлены новые напитки, то в таблицу автомата, нужно добавить столбцы
        if(isAddNewDrink && !isDeleteDrink) {
            for(int i = 1; oldDrinkList.size() + i <= newDrinkList.size(); i++) {

                myApp.addColomn(getNameOfTable(currentNumber), MyApp.COLUMN_DRINK + (oldDrinkList.size() + i) + " integer");
            }
        }

        // если изменен номер автомата, то нужно поменять название таблицы
        if(!currentNumber.equals(number)) {
            myApp.renameTable(getNameOfTable(currentNumber), getNameOfTable(number));
        }


        Toast.makeText(EditAutomat.this, "Автомат изменен", Toast.LENGTH_SHORT).show();
        myApp.closeConnectToDB();
        this.finish();
    }

    private String getAvailableColumns() {

        String columns = MyApp.COLUMN_ID + ", " + MyApp.COLUMN_DATE;

        for(int i = 0; i < oldDrinkList.size();  i++ ) {
            //если номер напитка не содержится в удаленных позициях, то столбец включаем в список для копирования
            if (!removePosition.contains(i))
                columns = columns + ", drink" + i;
        }

        return columns;
    }

    private String getNewColumns() {

        String columns = MyApp.COLUMN_ID + ", " + MyApp.COLUMN_DATE;
           // из колличества старых напитков вычитаем колличество удаленных напитков получаем колличество оставшихся напитков
        for(int i = 0; i < oldDrinkList.size() - removePosition.size(); i++)
            columns = columns + ", drink" + i;

        return columns;
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

    private String getNameOfTable(String number) {

        String name = number.replace("№ ", "automat");
        return name;
    }

    private String getStringCreateTable(Boolean isTemp) {

        String tableName;
        ArrayList<Drink> drinksList;
        if(isTemp) {
            tableName = "tempAutomat";
            drinksList = oldDrinkList;
        }
        else {
            tableName = "automat";
            drinksList = newDrinkList;
        }

        String table = "Create table " + tableName + automat.getNumber() + " ( " + MyApp.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MyApp.COLUMN_DATE + " text, ";
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
        drinks = newDrinkList.get(0).getName();
        drinksPrice = newDrinkList.get(0).getPrice();

        for(int i = 1; i < newDrinkList.size(); i++) {

            drinks = drinks + this.getString(R.string.separator) + newDrinkList.get(i).getName();
            drinksPrice = drinksPrice + this.getString(R.string.separator) + newDrinkList.get(i).getPrice();
        }
        Log.d(MyApp.LOG_TAG, drinks);
        Log.d(MyApp.LOG_TAG, drinksPrice);
    }

}
