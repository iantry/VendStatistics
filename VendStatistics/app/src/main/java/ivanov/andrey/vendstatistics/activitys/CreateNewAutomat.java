package ivanov.andrey.vendstatistics.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.classes.Drink;
import ivanov.andrey.vendstatistics.classes.DrinksAdapter;

public class CreateNewAutomat extends AppCompatActivity {


    Button buttonAdd, buttonSave;
    EditText editTextName, editTextNumber, editTextNameDrink, editTextPrice;
    ListView listViewDrinks;
    DrinksAdapter adapter;
    ArrayList<Drink> drinksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_automat);

        initVariables();
        initViews();
        setAdapter();
        setClickListener();

    }

    private void setAdapter() {

        adapter = new DrinksAdapter(this, drinksList);
        listViewDrinks.setAdapter(adapter);
    }

    private void initVariables() {

        drinksList = new ArrayList<>();
    }

    private void setClickListener() {

        buttonAdd.setOnClickListener(onClickListener);
        buttonSave.setOnClickListener(onClickListener);
    }

    private void initViews() {

        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextNumber = (EditText)findViewById(R.id.editTextNumber);
        editTextNameDrink = (EditText)findViewById(R.id.editTextNameDrink);
        editTextPrice = (EditText)findViewById(R.id.editTextPrice);
        listViewDrinks = (ListView)findViewById(R.id.listViewDrinks);

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buttonAdd:
                    addDrink();
                    break;
                case R.id.buttonSave:
                    break;
            }
        }
    };

    private void addDrink() {

        String name = editTextNameDrink.getText().toString().replaceAll(" ","");
        String price = editTextPrice.getText().toString().replaceAll(" ", "");
        if(name.isEmpty()) {
            Toast.makeText(CreateNewAutomat.this, "Вы не ввели название напитка", Toast.LENGTH_SHORT).show();
        }
        else if (price.isEmpty()) {
            Toast.makeText(CreateNewAutomat.this, "Вы не ввели цену напитка", Toast.LENGTH_SHORT).show();
        }
        else {
            Drink drink = new Drink(editTextNameDrink.getText().toString(), editTextPrice.getText().toString());
            drinksList.add(drink);
            adapter.notifyDataSetChanged();
            editTextNameDrink.setText("");
            editTextPrice.setText("");
        }

    }
}
