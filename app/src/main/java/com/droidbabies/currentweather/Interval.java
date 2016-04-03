package com.droidbabies.currentweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajidMacPro on 4/3/16.
 */
public class Interval extends AppCompatActivity {
    TextView tv;
    ListView lv;
    WeatherAdapter adapter;
    List<Weather> weatherItems = new ArrayList<>();

    String place_id, place_name, mDate;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.interval);

        tv = (TextView) findViewById(R.id.idate_tv);
        lv = (ListView) findViewById(R.id.ilv);

        adapter = new WeatherAdapter(getApplicationContext(), weatherItems);
        lv.setAdapter(adapter);

        Intent i = getIntent();

        place_id = i.getExtras().getString("placeid");
        place_name = i.getExtras().getString("name");
        mDate = i.getExtras().getString("mDate");
        String jo = i.getExtras().getString("jo");

        getSupportActionBar().setSubtitle(place_name);
        tv.setText(mDate);
        parseJson(jo, mDate);

    }

    private void parseJson(String jobj, String mDate) {

        try {
            JSONObject jsonobj = new JSONObject(jobj);

            JSONArray jlist = jsonobj.getJSONArray("list");

            // tv.append(ja.length() + "");

            for (int i = 0; i < jlist.length(); i++) {

                String dt_txt = jlist.getJSONObject(i).getString("dt_txt");

                String jDate = dt_txt.substring(0, 10);

                if (mDate.equals(jDate)) {

                    JSONObject main = jlist.getJSONObject(i).getJSONObject(
                            "main");

                    int temp, temp_max, temp_min;

                    temp = (int) (main.getDouble("temp") - 273.15);
                    temp_max = (int) (main.getDouble("temp_max") - 273.15);
                    temp_min = (int) (main.getDouble("temp_min") - 273.15);

                    JSONArray weather = jlist.getJSONObject(i).getJSONArray(
                            "weather");

                    String icon = weather.getJSONObject(0).getString("icon");
                    String description = weather.getJSONObject(0).getString(
                            "description");

                    String mtime = dt_txt.substring(11, 16);

                    Weather wItem = new Weather(temp, temp_max, temp_min,description, icon, mtime);

                    weatherItems.add(wItem);

                } // if end

            } // for end

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {

            e.printStackTrace();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

//        if (id == R.id.action_map) {
//            openPreferredLocationInMap();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

}


