package com.riddhi.plugapp.framework.login;

/**
 * Created by ridz1 on 25/07/2017.
 */

public class LoginRequest {

    private String mail="";
    private String pswd="";
    private String req_type="";


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getReq_type() {
        return req_type;
    }

    public void setReq_type(String req_type) {
        this.req_type = req_type;
    }
}
