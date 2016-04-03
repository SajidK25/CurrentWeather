package com.droidbabies.currentweather;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by sajidMacPro on 4/3/16.
 */
public class WeatherAdapter extends BaseAdapter {
    Context context;
    List<Weather> WeatherAdapter;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public WeatherAdapter(Context context, List<Weather> WeatherItems) {

        this.context = context;
        this.WeatherAdapter = WeatherAdapter;

    }

    @Override
    public int getCount() {

        return WeatherAdapter.size();
    }

    @Override
    public Object getItem(int position) {

        return WeatherAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {

        return WeatherAdapter.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
        }

        TextView temp_tv, minmax_tv, desc_tv, date_tv;

        NetworkImageView niv;

        temp_tv = (TextView) convertView.findViewById(R.id.ftemp);
        minmax_tv = (TextView) convertView.findViewById(R.id.fmin_max);
        desc_tv = (TextView) convertView.findViewById(R.id.fdesc);
        date_tv = (TextView) convertView.findViewById(R.id.fdate);
        niv = (NetworkImageView) convertView.findViewById(R.id.ficon);

        Weather item = WeatherAdapter.get(position);

        temp_tv.setText(item.getTemp() + "\u2103");
        minmax_tv.setText(item.getTemp_min() + "\u2103" + "\\"
                + item.getTemp_min() + "\u2103");
        desc_tv.setText(item.getDescription() + "");
        date_tv.setText(item.getMdate() + "");

        String icon_url = "http://openweathermap.org/img/w/" + item.getIcon()
                + ".png";

        niv.setImageUrl(icon_url, imageLoader);

        return convertView;
    }

}