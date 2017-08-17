package com.riddhi.plugapp.framework.getdeviceconfig;

/**
 * Created by ridz1 on 01/08/2017.
 */

public class DeviceConfigRequest {

    private String place_id="";
    private String user_id="";

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
