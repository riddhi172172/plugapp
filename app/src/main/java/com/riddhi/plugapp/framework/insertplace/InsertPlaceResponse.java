
package com.riddhi.plugapp.framework.insertplace;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertPlaceResponse {

    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private InsertPlaceData data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InsertPlaceData getData() {
        return data;
    }

    public void setData(InsertPlaceData data) {
        this.data = data;
    }

}
