package com.riddhi.plugapp.framework.insertplace;

/**
 * Created by ridz1 on 01/08/2017.
 */

public class InsertPlaceRequest {

    private String user_id="";
    private String place_name="";

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }
}
