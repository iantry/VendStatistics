package ivanov.andrey.vendstatistics.classes;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ivanov.andrey.vendstatistics.activitys.MainActivity;

/**
 * Created by Andrey on 20.09.2016.
 */
public class MyApplication extends Application {


    public static final String LOG_TAG = "myLog";

    private static MyApplication fInstance;
    DataBaseHelper dbHelper;
    SQLiteDatabase db;



    @Override
    public void onCreate() {
        super.onCreate();

        fInstance = this;
    }

    public static MyApplication getfInstance() {
        return fInstance;
    }

    public SQLiteDatabase connectToDB(Context context) {

        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();

        Log.d(MainActivity.LOG_TAG, "--- Соединение с БД Открыто ---");
        return db;
    }

    public void closeConnectToDB() {

        dbHelper.close();
        db.close();

        Log.d(MainActivity.LOG_TAG, "--- Соединение с БД закрыто ---");

    }


    public Cursor readExistAutomats(Context context){

        db = connectToDB(context);
        Cursor cursor = db.query("listOfAutomats", null, null, null, null, null, null);


        return cursor;
    }

}
