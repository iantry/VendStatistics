package ivanov.andrey.vendstatistics.classes;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

    private Context context;
    private ArrayList<Drink> drinks;
    private LayoutInflater layoutInflater;
    private int item;
    private String[] piecesOfDrinks;
    private ArrayList<String> items = new ArrayList<>();
    private AutomatInfo automatInfo;

    public DrinksAdapter(Context context, ArrayList<Drink> drinks, int item, String[] piecesOfDrinks) {
        super(context, item, drinks);
        this.context = context;
        this.drinks = drinks;
        this.item = item;
        this.piecesOfDrinks = piecesOfDrinks;
        layoutInflater  = LayoutInflater.from(context);
        for(int i = 0;i < drinks.size(); i++)
        {
            items.add("");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Drink drink = drinks.get(position);
        ViewHolder holder;

        if (convertView == null) {

            convertView = layoutInflater.inflate(item, parent, false);

            holder = new ViewHolder();
            holder.textNameDrink = (TextView) convertView.findViewById(R.id.text1);
            holder.textPriceDrink = (TextView) convertView.findViewById(R.id.text2);

            if (convertView.findViewById(R.id.pieces) != null) {
                holder.editTextPieces = (EditText) convertView.findViewById(R.id.pieces);
                holder.textWatcher = new EditTextWatcher();
                holder.editTextPieces.addTextChangedListener(holder.textWatcher);
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
            holder.textWatcher.setTarget(position);
            holder.editTextPieces.setText(items.get(position));
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

    private class EditTextWatcher implements TextWatcher {

        private int target;

        void setTarget(int target) {
            this.target = target;
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            automatInfo = (AutomatInfo) context;
            items.set(target, str);
            if(!str.isEmpty()) {
                automatInfo.piecesOfDrink[target] = Integer.parseInt(s.toString());
            }
            else
                automatInfo.piecesOfDrink[target] = 0;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }

    private class ViewHolder {
        TextView textNameDrink, textPriceDrink, textPieces;
        EditText editTextPieces;
        EditTextWatcher textWatcher;
    }

}