package com.riddhi.plugapp.webapis;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.riddhi.plugapp.framework.getdeviceconfig.DeviceConfigRequest;
import com.riddhi.plugapp.framework.getdeviceconfig.DeviceConfigResponse;
import com.riddhi.plugapp.framework.getwifi.GetWifiResponse;
import com.riddhi.plugapp.framework.insertplace.InsertPlaceRequest;
import com.riddhi.plugapp.framework.insertplace.InsertPlaceResponse;
import com.riddhi.plugapp.framework.login.LoginRequest;
import com.riddhi.plugapp.framework.login.LoginResponse;
import com.riddhi.plugapp.framework.mikecheck.MikeCheckResponse;
import com.riddhi.plugapp.framework.savewifi.SaveWifiRequest;
import com.riddhi.plugapp.framework.savewifi.SaveWifiResponse;
import com.riddhi.plugapp.framework.signup.SignupRequest;
import com.riddhi.plugapp.framework.signup.SignupResponse;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by James P. Zimmerman II on 3/4/16.
 */
public class WebAPIClient {
    private static final String TAG = WebAPIClient.class.getSimpleName();
    private Retrofit jsonClient;

    public static String baseUrl = "http://vegg.co.in/";
    private final String api_id = "5a73e15315708a9b2d4ec2081ac5a0a9";
    private final String api_secret = "5884b91fa1b55d3008cc3b4657891552";

    public WebAPIClient(Retrofit jsonClient) {
        this.jsonClient = jsonClient;
    }

    public static WebAPIClient getInstance(Context c) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(60, TimeUnit.MINUTES);
        builder.connectTimeout(60, TimeUnit.MINUTES);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        fixSSL(builder);
        OkHttpClient httpClient = builder.build();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Retrofit json = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        WebAPIClient webAPIClient = new WebAPIClient(json);
        return webAPIClient;
    }

    public static void fixSSL(OkHttpClient.Builder builder) {
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private WebAPIInterface json() {
        return jsonClient.create(WebAPIInterface.class);
    }

    //   1.----------------------        Login     -----------------------------------------------------------------
    public void login(LoginRequest request, Callback<LoginResponse> callback) {
        json().login(request.getMail(), request.getPswd(), "log_in").enqueue(callback);
    }

    //2.----------------------      signup      ----------------------------------------------------------------
    public void signup(SignupRequest request, Callback<SignupResponse> callback) {
        json().signup(request.getFull_name(), request.getPhone(), request.getMail(), request.getPswd(), request.getBd(), request.getType(), "sign_up").enqueue(callback);
    }

    //3.----------------------   insert lace        ------------------------------------------------

    public void insert_place(InsertPlaceRequest request, Callback<InsertPlaceResponse> callback) {
        json().insert_place(request.getUser_id(), request.getPlace_name(), "insert_place").enqueue(callback);
    }

    //4.----------------------   get device config        ------------------------------------------------

    public void get_device_config(DeviceConfigRequest request, Callback<DeviceConfigResponse> callback) {
        json().get_device_config(request.getUser_id(), request.getPlace_id(), "device_config_info").enqueue(callback);
    }

    //5.----------------------   get device config        ------------------------------------------------

    public void mikeCheck(Callback<MikeCheckResponse> callback) {
        json().mikeCheck("mike_check").enqueue(callback);
    }

    //6.----------------------   get device config        ------------------------------------------------

    public void getWifiNames(Callback<GetWifiResponse> callback) {
        json().getWifiNames("wifi_names").enqueue(callback);
    }
    //7.----------------------   get device config        ------------------------------------------------

    public void saveWifi(SaveWifiRequest saveWifiRequest, Callback<SaveWifiResponse> callback) {
        json().saveWifi(saveWifiRequest.getS(), saveWifiRequest.getP(), saveWifiRequest.getServer(), saveWifiRequest.getPort(), saveWifiRequest.getUser(), saveWifiRequest.getPswd(), "wifisave").enqueue(callback);
    }

}
