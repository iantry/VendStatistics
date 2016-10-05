package ivanov.andrey.vendstatistics.activitys;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.FactoryIntent;
import ivanov.andrey.vendstatistics.classes.MyApp;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fabAddAutomat;
    ListView listView;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    String[] from;
    int[] to;
    MyApp myApp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.app_name);
        initVariables();
        initView();
        setClickListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
        myApp.connectToDB();
        cursor = myApp.readAllData(MyApp.TABLE_MAIN);
        adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.item_automat, cursor, from, to, MyApp.CURSUR_ADAPTER_FLAG);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);
        myApp.closeConnectToDB();
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
            Toast.makeText(getApplicationContext(), "Item Click id - " + id + "\n position - " + position, Toast.LENGTH_LONG).show();

            myApp.connectToDB();
            myApp.setDrinksList(position); // передается позиция для получения листа
            myApp.setTableName(itemClicked); // передается View для установки имени таблицы с которой мы будем работать
            myApp.closeConnectToDB();
            FactoryIntent.openAutomatInfoActivity(MainActivity.this);

        }
    };

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
                Toast.makeText(MainActivity.this, info.id + " EDIT", Toast.LENGTH_SHORT).show();
                myApp.connectToDB();
                myApp.setId(info.id);                // получаем в глобальной переменной id in table
                myApp.setDrinksList(info.position);  // получаем в глобальной переменной список напитков по позиции
                myApp.setTableName(info.targetView); // получаем название таблицы в которой будут происходить изменения
                FactoryIntent.openEditAutomat(MainActivity.this);
                myApp.closeConnectToDB();
                return true;
            case R.id.item_delete:
                myApp.connectToDB();
                myApp.deleteTable(getTableName(info.targetView));
                myApp.deleteRow(MyApp.TABLE_MAIN, info.id);
                cursor = myApp.readAllData(MyApp.TABLE_MAIN);
                adapter.swapCursor(cursor);
                myApp.closeConnectToDB();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private String getTableName(View view) {

        TextView automatNumber = (TextView) view.findViewById(R.id.automatNumber);
        String tableName = automatNumber.getText().toString();

        tableName = tableName.replace("№ ", "automat");

        return tableName;
    }

    private void initVariables() {

        from = new String[] { MyApp.COLUMN_NAME, MyApp.COLUMN_NUMBER };
        to = new int[] { R.id.automatName, R.id.automatNumber };
        myApp = MyApp.getfInstance();
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        fabAddAutomat = (FloatingActionButton) findViewById(R.id.fabAddAutomat);
        listView = (ListView) findViewById(R.id.automatListView);
        registerForContextMenu(listView);
    }

    private void setClickListener() {

        fabAddAutomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FactoryIntent.openNewAutomatActivity(MainActivity.this);
            }
        });
    }
}
