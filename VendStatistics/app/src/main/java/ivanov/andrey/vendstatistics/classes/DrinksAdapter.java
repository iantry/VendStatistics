package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
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
    Boolean endTextChanged = true;

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

        if (convertView == null) {

            convertView = layoutInflater.inflate(item, parent, false);
        }

        TextView textNameDrink;
        TextView textPriceDrink;

        if(item == R.layout.item_statistics) {
            textNameDrink = (TextView) convertView.findViewById(R.id.text1);
            textPriceDrink = (TextView) convertView.findViewById(R.id.text2);
        }
        else {

            textNameDrink = (TextView) convertView.findViewById(R.id.textName);
            textPriceDrink = (TextView) convertView.findViewById(R.id.textPrice);
        }
        if (convertView.findViewById(R.id.pieces) != null) {
            final EditText editTextPieces = (EditText) convertView.findViewById(R.id.pieces);
            editTextPieces.setTag(position);

        }

        textNameDrink.setText(drink.getName());
        String price = drink.getPrice() + " " + context.getResources().getString(R.string.valuta);
        textPriceDrink.setText(price);

        return convertView;
    }

}