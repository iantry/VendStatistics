package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.content.Intent;

import ivanov.andrey.vendstatistics.activitys.AutomatInfo;
import ivanov.andrey.vendstatistics.activitys.CreateNewAutomat;

/**
 * Created by Andrey on 17.09.2016.
 */
public class FactoryIntent {

    public static void openNewAutomatActivity(Context context) {

        Intent intent = new Intent(context, CreateNewAutomat.class);
        context.startActivity(intent);
    }


    public static void openAutomatInfoActivity(Context context, String tableName, String drinks, String drinks_price) {

        Intent intent = new Intent(context, AutomatInfo.class);
        intent.putExtra(MyApp.TABLE_NAME, tableName);
        intent.putExtra(MyApp.COLUMN_DRINKS, drinks);
        intent.putExtra(MyApp.COLUMN_DRINKS_PRICE, drinks_price);

        context.startActivity(intent);
    }

}
