package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ivanov.andrey.vendstatistics.activitys.MainActivity;

/**
 * Created by Andrey on 17.09.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "vendStatisticsDB";
    private static final String TABLE_MAIN = "listOfAutomat";
    private static final String COLUMN_ID = "_id integer primary key autoincrement";
    private static final String COLUMN_NAME = "name text";
    private static final String COLUMN_NUMBER = "number text";
    private static final String COLUMN_COLUMNS = "columns text";

    public static final int VERSION_DATA_BASE = 1;

    public DataBaseHelper(Context context) {

        super(context,DATA_BASE_NAME , null, VERSION_DATA_BASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d(MainActivity.LOG_TAG, "--- onCreate database ---");
// создаем таблицу с полями
        sqLiteDatabase.execSQL("create table " + TABLE_MAIN + " ( " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_NUMBER + ", " + COLUMN_COLUMNS + " );");

    }
}
