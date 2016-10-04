package ivanov.andrey.vendstatistics.activitys;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.FactoryIntent;
import ivanov.andrey.vendstatistics.classes.MyApp;

public class ListOfStatistics extends AppCompatActivity {

    ListView listViewStatisticsDate;
    String tableName;
    SimpleCursorAdapter adapter;
    String[] from;
    int[] to;
    MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_statistics);
        Toolbar toolbar = (Toolbar)findViewById(R.id.automat_info_toolbar);
        setSupportActionBar(toolbar);
        initVariables();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myApp.connectToDB();
        Cursor cursor = myApp.readAllData(tableName);
        adapter = new SimpleCursorAdapter(ListOfStatistics.this, android.R.layout.simple_list_item_1, cursor, from, to, MyApp.CURSUR_ADAPTER_FLAG1);
        listViewStatisticsDate.setAdapter(adapter);
        listViewStatisticsDate.setOnItemClickListener(itemClickListener);
        myApp.closeConnectToDB();

    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            FactoryIntent.openStatisticsByDate(ListOfStatistics.this, position);

        }
    };

    private void initVariables() {

        myApp = MyApp.getfInstance();
        tableName = myApp.getTableName();
        from = new String[] { MyApp.COLUMN_DATE };
        to = new int[] { android.R.id.text1 };

    }

    private void initViews() {

        listViewStatisticsDate = (ListView)findViewById(R.id.listViewStatisticsDate);
    }
}
