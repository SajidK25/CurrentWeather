package com.droidbabies.currentweather;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajidMacPro on 4/3/16.
 */
public class Forecast extends AppCompatActivity {
    String forecast_url = "http://api.openweathermap.org/data/2.5/forecast?id=";

    ProgressDialog PD;

    ListView lv;
    WeatherAdapter adapter;
    List<Weather> weatherItems ;

    String place_id, place_name;

    JSONObject mJsonObj;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.forecast);

        lv = (ListView) findViewById(R.id.flv);
        weatherItems = new ArrayList<>();
        place_id = getIntent().getExtras().getString("id");
        place_name = getIntent().getExtras().getString("name");
        if (place_id.length() > 0 && place_name.length() > 0) {
            //getActionBar().setSubtitle(place_name);
            getSupportActionBar().setSubtitle(place_name);

            PD = new ProgressDialog(Forecast.this);
            PD.setMessage("Loading.....");
            PD.setCancelable(false);
            String appID="&appid=fec7a9fdb239c89d79724e223d71edbb";
            String full_url = forecast_url + place_id + appID;
            makejsonreq(full_url);
            adapter = new WeatherAdapter(this, weatherItems);
            lv.setAdapter(adapter);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long id) {

                    Toast.makeText(getApplicationContext(), "Id: " + place_id + "Date: "
                                    + weatherItems.get(position).getMdate(),
                            Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), Interval.class);

                    i.putExtra("placeid", place_id);
                    i.putExtra("name", place_name);

                    i.putExtra("mDate", weatherItems.get(position).getMdate());
                    i.putExtra("jo", mJsonObj.toString());

                    startActivity(i);

                }
            });


        }else {
            Toast.makeText(Forecast.this, "id place name not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void makejsonreq(String url) {

        PD.show();
        JsonObjectRequest jsonObjReqq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mJsonObj = response;
                try {
                    JSONArray jlist = response.getJSONArray("list");

                    String dt = null;
                    String pre_dt = null;

                    for (int i = 0; i < jlist.length(); i++) {
                        dt = jlist.getJSONObject(i).getString("dt_txt")
                                .substring(8, 10);

                        if (i > 1 || i == 1) {
                            pre_dt = jlist.getJSONObject(i - 1)
                                    .getString("dt_txt")
                                    .substring(8, 10);

                            if (!(dt.equals(pre_dt))) {

                                String mdate = jlist.getJSONObject(i)
                                        .getString("dt_txt")
                                        .substring(0, 10);

                                JSONObject main = jlist
                                        .getJSONObject(i)
                                        .getJSONObject("main");

                                int temp, temp_max, temp_min;

                                temp = (int) (main.getDouble("temp") - 273.15);
                                temp_max = (int) (main
                                        .getDouble("temp_max") - 273.15);
                                temp_min = (int) (main
                                        .getDouble("temp_min") - 273.15);

                                JSONArray weather = jlist.getJSONObject(i).getJSONArray("weather");

                                JSONObject jo = weather.getJSONObject(0);

                                String description = jo.getString("description");
                                String icon= jo.getString("icon");
                                Toast.makeText(Forecast.this, description+""+icon, Toast.LENGTH_SHORT).show();
                                Weather wItem = new Weather(temp, temp_max, temp_min, description, icon, mdate);
                                if (wItem!=null) {
                                    weatherItems.add(wItem);


                                }else {
                                    Toast.makeText(Forecast.this, "NO Data arrived", Toast.LENGTH_SHORT).show();
                                }
                            } // if end
                        } // if end
                    } // for end

                } catch (JSONException e) {
                    e.printStackTrace();
                    PD.dismiss();
                }
                adapter.notifyDataSetChanged();
                PD.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        });

        MyApplication.getInstance().addToReqQueue(jsonObjReqq, "jreqq");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

