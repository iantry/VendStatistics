package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ivanov.andrey.vendstatistics.R;
import ivanov.andrey.vendstatistics.activitys.AutomatInfo;


/**
 * Created by Andrey on 17.09.2016.
 */
public class DrinksAdapter extends ArrayAdapter<Drink> {

    Context context;
    ArrayList<Drink> drinks;
    LayoutInflater layoutInflater;
    int item;
    AutomatInfo automatInfo;

    public DrinksAdapter(Context context, ArrayList<Drink> drinks, int item) {
        super(context, item, drinks);

        this.context = context;
        this.drinks = drinks;
        this.item = item;
        automatInfo = (AutomatInfo)context;

        layoutInflater  = LayoutInflater.from(context);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Drink drink = drinks.get(position);

        if(convertView == null){

            convertView = layoutInflater.inflate(item, parent, false);
        }

        TextView textNameDrink = (TextView) convertView.findViewById(R.id.text1);
        TextView textPriceDrink = (TextView) convertView.findViewById(R.id.text2);
        if(convertView.findViewById(R.id.pieces) != null) {
            final EditText editTextPieces = (EditText) convertView.findViewById(R.id.pieces);

            View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        automatInfo.pieces.add(position, editTextPieces.getText().toString());
                        Log.d(MyApp.LOG_TAG, "Данные добавленные в список по позиции - " + position + " = " + automatInfo.pieces.get(position));
                       // Toast.makeText(context, "Потерян фокус у позиции - " + position, Toast.LENGTH_SHORT).show();
                    }
                }
            };

            editTextPieces.setOnFocusChangeListener(focusChangeListener);
        }


        textNameDrink.setText(drink.getName());
        String price = drink.getPrice() + " " + context.getResources().getString(R.string.valuta);
        textPriceDrink.setText(price);

        return convertView;
    }
}
