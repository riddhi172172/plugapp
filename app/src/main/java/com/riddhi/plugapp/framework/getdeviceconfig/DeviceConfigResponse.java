
package com.riddhi.plugapp.framework.getdeviceconfig;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceConfigResponse {

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
    private DeviceConfigData data;

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

    public DeviceConfigData getData() {
        return data;
    }

    public void setData(DeviceConfigData data) {
        this.data = data;
    }

}
