
package com.riddhi.plugapp.framework.insertplace;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertPlaceData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("place_id")
    @Expose
    private String placeId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

}
