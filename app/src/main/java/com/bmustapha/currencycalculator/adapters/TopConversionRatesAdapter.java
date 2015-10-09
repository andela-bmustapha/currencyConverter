package com.bmustapha.currencycalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bmustapha.currencycalculator.R;
import com.bmustapha.currencycalculator.config.Constants;
import com.bmustapha.currencycalculator.models.CurrencyRate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 10/3/15.
 */
public class TopConversionRatesAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private ArrayList<CurrencyRate> list;

    public TopConversionRatesAdapter(Context context, List<CurrencyRate> list) {
        this.list = (ArrayList<CurrencyRate>) list;
        inflater = LayoutInflater.from(context);
    }


    public class ViewHolder {
        TextView currencyName;
        TextView currencyValue;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CurrencyRate getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.single_top_layout, parent, false);

            holder = new ViewHolder();
            holder.currencyName = (TextView) convertView.findViewById(R.id.top_currency);
            holder.currencyValue = (TextView) convertView.findViewById(R.id.top_currency_amount);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CurrencyRate currencyRate = list.get(position);
        holder.currencyName.setText(currencyRate.getCurrencyName());
        holder.currencyValue.setText(String.valueOf(Constants.FORMATTER.format(currencyRate.getCurrencyRate())));

        return convertView;
    }
}
