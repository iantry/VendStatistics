package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    public DrinksAdapter(Context context, ArrayList<Drink> drinks) {
        super(context, R.layout.item_drink,drinks);

        this.context = context;
        this.drinks = drinks;

        layoutInflater  = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       Drink drink = drinks.get(position);

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.item_drink, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.text1)).setText(drink.getName());
        ((TextView) convertView.findViewById(R.id.text2)).setText(drink.getPrice());

        return convertView;
    }
}
