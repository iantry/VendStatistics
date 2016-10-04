package ivanov.andrey.vendstatistics.classes;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;

/**
 * Created by Andrey on 20.09.2016.
 */
public class MyApp extends Application {

    public static final String LOG_TAG = "myLog";
    public static final int CURSUR_ADAPTER_FLAG = 1233210;
    public static final int CURSUR_ADAPTER_FLAG1 = 1233211;
    public static final String TABLE_NAME = "tableName";

    public static final String DATA_BASE_NAME = "vendStatisticsDB";
    public static final int VERSION_DATA_BASE = 1;
    public static final String TABLE_MAIN = "listOfAutomats";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_DRINKS = "drinks";
    public static final String COLUMN_DRINKS_PRICE = "drinks_price";
    public static final String CREATE_TABLE = "create table " + TABLE_MAIN + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text, "
            + COLUMN_NUMBER + " text, "
            + COLUMN_DRINKS + " text, "
            + COLUMN_DRINKS_PRICE + " text );";

    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DRINK ="drink";



    private static MyApp fInstance;
    DataBaseHelper dbHelper;
    SQLiteDatabase db;
    private ArrayList<Drink> drinksList;
    private String tableName;



    @Override
    public void onCreate() {
        super.onCreate();

        fInstance = this;
    }

    public static MyApp getfInstance() {
        return fInstance;
    }

    public void connectToDB() {

        dbHelper = new DataBaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Log.d(LOG_TAG, "--- Соединение с БД Открыто ---");
    }

    public void closeConnectToDB() {

        dbHelper.close();
        db.close();

        Log.d(LOG_TAG, "--- Соединение с БД закрыто ---");

    }

    public Cursor readAllData(String tableFrom){

        Cursor cursor = db.query(tableFrom, null, null, null, null, null, null);

        return cursor;
    }

    public void createTable(String table){

        db.execSQL(table);

    }

    public long insertToTable(String table , ContentValues contentValues){

        return db.insert(table, null, contentValues);

    }

    public void deleteRow(String table, long id){

        db.delete(table, COLUMN_ID + " = " + id, null);
    }

    public void deleteTable(String table) {

        db.execSQL("DROP TABLE IF EXISTS " + table);
    }

    public String getRecord(String table, int position, String colomnName) {

        Cursor c = readAllData(table);
        c.moveToPosition(position);
        int numberColumnIndex = c.getColumnIndex(colomnName);
        String str =  c.getString(numberColumnIndex);
        return str;

    }


    public void setDrinksList(int position) {

        drinksList = new ArrayList<>();
        String drinks = getRecord(TABLE_MAIN, position, COLUMN_DRINKS);
        String drinksPrice = getRecord(TABLE_MAIN, position, COLUMN_DRINKS_PRICE);
        String separator = this.getResources().getString(R.string.separator);
        String  dr[] = drinks.split(separator);
        String  drPr[] = drinksPrice.split(separator);

        for(int i = 0; i < dr.length; i++){

            drinksList.add(new Drink(dr[i], drPr[i]));
        }

    }

    public ArrayList<Drink> getDrinksList() {
        return drinksList;
    }


    public void setTableName(View view) {

        TextView automatNumber = (TextView) view.findViewById(R.id.automatNumber);
        tableName = automatNumber.getText().toString();
        tableName = tableName.replace("№ ", "automat");
    }

    public String getTableName() {
        return tableName;
    }

}
