package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.content.Intent;

import ivanov.andrey.vendstatistics.activitys.AutomatInfo;
import ivanov.andrey.vendstatistics.activitys.CreateNewAutomat;
import ivanov.andrey.vendstatistics.activitys.ListOfStatistics;
import ivanov.andrey.vendstatistics.activitys.StatisticsByDate;

/**
 * Created by Andrey on 17.09.2016.
 */
public class FactoryIntent {

    public static void openNewAutomatActivity(Context context) {

        Intent intent = new Intent(context, CreateNewAutomat.class);
        context.startActivity(intent);
    }


    public static void openAutomatInfoActivity(Context context) {

        Intent intent = new Intent(context, AutomatInfo.class);

        context.startActivity(intent);
    }

    public static void openListOfStatistics(Context context) {

        Intent intent = new Intent(context, ListOfStatistics.class);

        context.startActivity(intent);
    }


    public static void openStatisticsByDate(Context context, int position) {

        Intent intent = new Intent(context, StatisticsByDate.class);
        intent.putExtra("position", position);

        context.startActivity(intent);
    }

}
