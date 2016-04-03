package com.droidbabies.currentweather;

/**
 * Created by sajidMacPro on 4/3/16.
 */
public class Weather {
    int temp, temp_max, temp_min;
    String mdate, description, icon;

    public Weather(int temp, int temp_max, int temp_min, String mdate, String description, String icon) {
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.mdate = mdate;
        this.description = description;
        this.icon = icon;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
