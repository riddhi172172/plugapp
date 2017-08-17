package com.riddhi.plugapp;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neovisionaries.ws.client.ThreadType;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketListener;
import com.neovisionaries.ws.client.WebSocketState;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements WebSocketListener {


    String TAG = Main2Activity.class.getName();
    private ServerSocket serverSocket;
    private int mLocalPort;
    private NsdManager.RegistrationListener mRegistrationListener;
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager mNsdManager;
    private NsdManager.ResolveListener mResolveListener;
    private String mServiceName = "akshar";

    private EditText txtMsg;
    private Button btn;
    private WebSocket ws;
    private String ip = "";
    private String port = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


//        try {
//            initializeServerSocket();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        registerService(0);

        //initializeResolveListener();
        //  if (discoveryListener != null)

        new init().execute();


    }

    @Override
    public void onStateChanged(WebSocket webSocket, WebSocketState webSocketState) throws Exception {

    }

    @Override
    public void onConnected(WebSocket webSocket, Map<String, List<String>> map) throws Exception {
        Log.e("connected", "connected");
    }

    @Override
    public void onConnectError(WebSocket webSocket, WebSocketException e) throws Exception {
        Log.e("err",e.getMessage() );
    }

    @Override
    public void onDisconnected(WebSocket webSocket, WebSocketFrame webSocketFrame, WebSocketFrame webSocketFrame1, boolean b) throws Exception {

    }

    @Override
    public void onFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onContinuationFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onTextFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onBinaryFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onCloseFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onPingFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onPongFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onTextMessage(WebSocket webSocket, String s) throws Exception {

    }

    @Override
    public void onBinaryMessage(WebSocket webSocket, byte[] bytes) throws Exception {

    }

    @Override
    public void onSendingFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onFrameSent(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onFrameUnsent(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onThreadCreated(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {

    }

    @Override
    public void onThreadStarted(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {

    }

    @Override
    public void onThreadStopping(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception {

    }

    @Override
    public void onError(WebSocket webSocket, WebSocketException e) throws Exception {

    }

    @Override
    public void onFrameError(WebSocket webSocket, WebSocketException e, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onMessageError(WebSocket webSocket, WebSocketException e, List<WebSocketFrame> list) throws Exception {

    }

    @Override
    public void onMessageDecompressionError(WebSocket webSocket, WebSocketException e, byte[] bytes) throws Exception {

    }

    @Override
    public void onTextMessageError(WebSocket webSocket, WebSocketException e, byte[] bytes) throws Exception {

    }

    @Override
    public void onSendError(WebSocket webSocket, WebSocketException e, WebSocketFrame webSocketFrame) throws Exception {

    }

    @Override
    public void onUnexpectedError(WebSocket webSocket, WebSocketException e) throws Exception {

    }

    @Override
    public void handleCallbackError(WebSocket webSocket, Throwable throwable) throws Exception {

    }

    @Override
    public void onSendingHandshake(WebSocket webSocket, String s, List<String[]> list) throws Exception {

    }

//    @Override
//    public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
//        Log.e(TAG, "Resolve failed" + errorCode);
//    }
//
//    @Override
//    public void onServiceResolved(NsdServiceInfo serviceInfo) {
//        Log.d(TAG, "Service discovery success : " + serviceInfo);
//        if (!serviceInfo.getServiceType().equals("ws")) {
//            // Service type is the string containing the protocol and
//            // transport layer for this service.
//            Log.d(TAG, "Unknown Service Type: " + serviceInfo.getServiceType());
//        } else if (serviceInfo.getServiceName().equals("")) {
//            // The name of the service tells the user what they'd be
//            // connecting to. It could be "Bob's Chat App".
//            Log.d(TAG, "Same machine: " + "mServiceName");
//        } else if (serviceInfo.getServiceName().contains("akshar")) {
//            mNsdManager.resolveService(serviceInfo, mResolveListener);
//        }
    //   }

    private class init extends AsyncTask<String, Void, NsdManager> {

        @Override
        protected NsdManager doInBackground(String... params) {

            NsdManager mNsdManager = (NsdManager) getSystemService(Context.NSD_SERVICE);

            return mNsdManager;
        }

        @Override
        protected void onPostExecute(NsdManager nsdManager) {
            super.onPostExecute(nsdManager);

            mNsdManager = nsdManager;
            if (nsdManager != null) {


                initializeDiscoveryListener();

                nsdManager.discoverServices(
                        "_ws._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener);

            }

            else Toast.makeText(Main2Activity.this, nsdManager + "", Toast.LENGTH_SHORT).show();

        }
    }


    public void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        discoveryListener = new NsdManager.DiscoveryListener() {

            //  Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.e(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found!  Do something with it.
                Log.e(TAG, "Service discovery success : " + service);
                if (!service.getServiceType().equals("_ws._tcp.")) {
                    // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.e(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals("")) {
                    // The name of the service tells the user what they'd be
                    // connecting to. It could be "Bob's Chat App".
                    Log.e(TAG, "Same machine: " + "mServiceName");
                } else if (service.getServiceName().contains("akshar")) {
                    Log.e(TAG, "Same machineSucssess: " + "mServiceName");
                    mNsdManager.resolveService(service, new MyResolveListener());
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e(TAG, "service lost" + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.e(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    public void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails.  Use the error code to debug.
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                Log.d(TAG, serviceInfo.getServiceName());
                if (serviceInfo.getServiceName().equals(mServiceName)) {

                    Log.d(TAG, "Same IP.");
                    return;
                }
                NsdServiceInfo mService = serviceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();
            }
        };
    }

    private class MyResolveListener implements NsdManager.ResolveListener {
        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            //your code
            Log.e(TAG, "Resolve failed" + errorCode);
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            //your code

            Log.e(TAG, "Resolve Succeeded. " + serviceInfo);
            Log.e(TAG, "host Succeeded. " + serviceInfo.getHost() + "");
            Log.e(TAG, "port Succeeded. " + serviceInfo.getPort() + "");

            Log.d(TAG, serviceInfo.getServiceName());
            if (serviceInfo.getServiceName().equals(mServiceName)) {

                Log.d(TAG, "Same IP.");
                port = serviceInfo.getPort() + "";
                ip = serviceInfo.getHost() + "";
                new init1().execute();
                return;
            }

            ip = serviceInfo.getHost() + "";
            NsdServiceInfo mService = serviceInfo;
            int port = mService.getPort();
            InetAddress host = mService.getHost();


        }
    }

    private class init1 extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("ip", ip);
        }

        @Override
        protected String doInBackground(String... strings) {


                //  ws = new WebSocketFactory().createSocket("ws://192.168.0.103:1337");

//
//                String  uri = "ws:/"+ip+":"+port;
//                Log.e("uri",uri);
            try {

                URI uri = new URI("ws:/" + "192.168.1.102" + ":" + "5353/client.php");

                ws = new WebSocketFactory().createSocket(uri);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            try {
                ws.connect().addListener(Main2Activity.this);
            } catch (WebSocketException e) {
                e.printStackTrace();
            }



            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Main2Activity.this, "success", Toast.LENGTH_SHORT).show();
        }
    }


}
