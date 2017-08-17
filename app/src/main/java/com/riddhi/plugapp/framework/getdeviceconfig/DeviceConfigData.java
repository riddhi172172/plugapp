
package com.riddhi.plugapp.framework.getdeviceconfig;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceConfigData {

    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("port")
    @Expose
    private String port;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("init_pub")
    @Expose
    private String initPub;
    @SerializedName("init_sub")
    @Expose
    private String initSub;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getInitPub() {
        return initPub;
    }

    public void setInitPub(String initPub) {
        this.initPub = initPub;
    }

    public String getInitSub() {
        return initSub;
    }

    public void setInitSub(String initSub) {
        this.initSub = initSub;
    }

}
