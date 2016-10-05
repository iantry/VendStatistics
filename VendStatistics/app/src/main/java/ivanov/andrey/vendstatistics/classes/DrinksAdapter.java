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


/**
 * Created by Andrey on 17.09.2016.
 */
public class DrinksAdapter extends ArrayAdapter<Drink> {

    Context context;
    ArrayList<Drink> drinks;
    LayoutInflater layoutInflater;
    int item;
    String[] piecesOfDrinks;

    public DrinksAdapter(Context context, ArrayList<Drink> drinks, int item, String[] piecesOfDrinks) {
        super(context, item, drinks);

        this.context = context;
        this.drinks = drinks;
        this.item = item;
        this.piecesOfDrinks = piecesOfDrinks;

        layoutInflater  = LayoutInflater.from(context);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Drink drink = drinks.get(position);

        if (convertView == null) {

            convertView = layoutInflater.inflate(item, parent, false);
        }

        TextView textNameDrink = (TextView) convertView.findViewById(R.id.text1);
        TextView textPriceDrink = (TextView) convertView.findViewById(R.id.text2);

        if (convertView.findViewById(R.id.pieces) != null) {
            EditText editTextPieces = (EditText) convertView.findViewById(R.id.pieces);
            editTextPieces.setTag(position);
        }

        if (piecesOfDrinks != null) {

            TextView textPieces = (TextView)convertView.findViewById(R.id.textViewPieces);
            String text = piecesOfDrinks[position] + " " + context.getString(R.string.pcs);
            textPieces.setText(text);
        }

        textNameDrink.setText(drink.getName());
        String price = drink.getPrice() + " " + context.getString(R.string.valuta);
        textPriceDrink.setText(price);

        return convertView;
    }

}