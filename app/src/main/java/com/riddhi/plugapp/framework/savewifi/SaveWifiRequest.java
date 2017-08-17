package com.riddhi.plugapp.framework.savewifi;

/**
 * Created by ridz1 on 03/08/2017.
 */

public class SaveWifiRequest {

    private String s = "";
    private String p = "";
    private String server = "";
    private String port = "";
    private String user = "";
    private String pswd = "";

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}
