
package com.riddhi.plugapp.framework.getwifi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Network {

    @SerializedName("ssid")
    @Expose
    private String ssid;
    @SerializedName("rssi")
    @Expose
    private String rssi;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

}
