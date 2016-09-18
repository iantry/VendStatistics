package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.content.Intent;

import ivanov.andrey.vendstatistics.activitys.CreateNewAutomat;

/**
 * Created by Andrey on 17.09.2016.
 */
public class FactoryIntent {

    public static void openNewAutomatActivity(Context context){

        Intent intent = new Intent(context, CreateNewAutomat.class);
        context.startActivity(intent);
    }


}
