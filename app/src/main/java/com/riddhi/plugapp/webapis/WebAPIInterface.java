package com.riddhi.plugapp.webapis;

import com.riddhi.plugapp.framework.getdeviceconfig.DeviceConfigResponse;
import com.riddhi.plugapp.framework.getwifi.GetWifiResponse;
import com.riddhi.plugapp.framework.insertplace.InsertPlaceResponse;
import com.riddhi.plugapp.framework.login.LoginResponse;
import com.riddhi.plugapp.framework.mikecheck.MikeCheckResponse;
import com.riddhi.plugapp.framework.savewifi.SaveWifiResponse;
import com.riddhi.plugapp.framework.signup.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by James P. Zimmerman II on 3/4/16.
 */
public interface WebAPIInterface {

    @FormUrlEncoded
    @POST(Endpoint.POST_URL)
    Call<LoginResponse> login(@Field("mail") String api_secret,
                              @Field("pswd") String pswd,
                              @Field("req_type") String req_type);

    @FormUrlEncoded
    @POST(Endpoint.POST_URL)
    Call<SignupResponse> signup(@Field("full_name") String full_name,
                                @Field("phone") String phone,
                                @Field("mail") String mail,
                                @Field("pswd") String pswd,
                                @Field("bd") String bd,
                                @Field("type") String type,
                                @Field("req_type") String req_type
    );

    @FormUrlEncoded
    @POST(Endpoint.POST_URL)
    Call<InsertPlaceResponse> insert_place(@Field("user_id") String user_id,
                                           @Field("place_name") String place_name,
                                           @Field("req_type") String req_type);
    @FormUrlEncoded
    @POST(Endpoint.POST_URL)
    Call<DeviceConfigResponse> get_device_config(@Field("user_id") String user_id,
                                                 @Field("place_id") String place_name,
                                                 @Field("req_type") String req_type);
    @FormUrlEncoded
    @POST(Endpoint.POST_URL)
    Call<MikeCheckResponse> mikeCheck(@Field("req_type") String req_type);

    @FormUrlEncoded
    @POST(Endpoint.POST_URL)
    Call<GetWifiResponse> getWifiNames(@Field("req_type") String req_type);

    @FormUrlEncoded
    @POST(Endpoint.POST_URL)
    Call<SaveWifiResponse> saveWifi(@Field("s") String s,
                                    @Field("p") String p,
                                    @Field("server") String server,
                                    @Field("port") String port,
                                    @Field("user") String user,
                                    @Field("pswd") String pswd,
                                    @Field("req_type") String req_type);


    class Endpoint {
        public static final String POST_URL = "api/v_api.php";
    }
}
