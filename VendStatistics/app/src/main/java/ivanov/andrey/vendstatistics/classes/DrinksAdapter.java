package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

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
    String[] piecesOfDrinks;
    String[] editUpdatePieces;
    public static HashMap<Integer,String> myList;
    AutomatInfo automatInfo;

    public DrinksAdapter(Context context, ArrayList<Drink> drinks, int item, String[] piecesOfDrinks) {
        super(context, item, drinks);
        this.context = context;
        this.drinks = drinks;
        this.item = item;
        this.piecesOfDrinks = piecesOfDrinks;
        layoutInflater  = LayoutInflater.from(context);
        editUpdatePieces = new String[drinks.size()];
        myList = new HashMap<>();
        for(int i = 0;i < drinks.size(); i++)
        {
            myList.put(i,"");
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Drink drink = drinks.get(position);
        final int pos = position;
        ViewHolder holder;

        if (convertView == null) {

            convertView = layoutInflater.inflate(item, parent, false);

            holder = new ViewHolder();
            holder.textNameDrink = (TextView) convertView.findViewById(R.id.text1);
            holder.textPriceDrink = (TextView) convertView.findViewById(R.id.text2);

            if (convertView.findViewById(R.id.pieces) != null) {
                holder.editTextPieces = (EditText) convertView.findViewById(R.id.pieces);
                //holder.editTextPieces.setTag(position);
            }
            if (piecesOfDrinks != null) {
                holder.textPieces = (TextView) convertView.findViewById(R.id.textViewPieces);
            }
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (convertView.findViewById(R.id.pieces) != null) { //для активити AutomatInfo
            automatInfo = (AutomatInfo) context;
            holder.editTextPieces.setText(editUpdatePieces[pos]);
            holder.editTextPieces.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                        EditText et = (EditText) v.findViewById(R.id.pieces);
                        if(!et.getText().toString().isEmpty()) automatInfo.piecesOfDrink[pos] = Integer.parseInt(et.getText().toString());
                        else automatInfo.piecesOfDrink[pos] = 0;
                        editUpdatePieces[pos] = et.getText().toString();
                }
            });
        }

        if (piecesOfDrinks != null) { //если список количества напитков не пуст выводится статистика(для Активии StatisticByDate)

            String text = piecesOfDrinks[position] + " " + context.getString(R.string.pcs);
            holder.textPieces.setText(text);
        }

        holder.textNameDrink.setText(drink.getName());
        String price = drink.getPrice() + " " + context.getString(R.string.valuta);
        holder.textPriceDrink.setText(price);


        return convertView;
    }

    class ViewHolder {
        public TextView textNameDrink, textPriceDrink, textPieces;
        public EditText editTextPieces;
    }

}