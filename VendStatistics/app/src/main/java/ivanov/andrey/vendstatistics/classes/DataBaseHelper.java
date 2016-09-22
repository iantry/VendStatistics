package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andrey on 17.09.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {

        super(context,MyApp.DATA_BASE_NAME , null, MyApp.VERSION_DATA_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d(MyApp.LOG_TAG, "--- onCreate database ---");
        sqLiteDatabase.execSQL(MyApp.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}
