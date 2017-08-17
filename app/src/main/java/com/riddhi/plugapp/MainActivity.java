package com.riddhi.plugapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neovisionaries.ws.client.ProxySettings;
import com.neovisionaries.ws.client.ThreadType;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketListener;
import com.neovisionaries.ws.client.WebSocketState;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, WebSocketListener {

    private EditText txtMsg;
    private Button btn;
    private WebSocket ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        //SSLContext context = NaiveSSLContext.getInstance("TLS");
//        WebSocketFactory factory = new WebSocketFactory();
//        factory.setVerifyHostname(false);
//        ProxySettings settings = factory.getProxySettings();
//        settings.setServer("https://proxy.example.com");

        new init().execute();

    }

    private class init extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
              //  ws = new WebSocketFactory().createSocket("ws://192.168.0.103:1337");
                ws = new WebSocketFactory().createSocket("ws://770hf5h94g017i449221g322gi695kj2.local:81");

                ws.connect().addListener(MainActivity.this);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (WebSocketException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
        }
    }

    private class sendMsg extends AsyncTask<String, Void, String> {

        String txt = txtMsg.getText().toString();

        @Override
        protected String doInBackground(String... strings) {
            if (ws != null)
                ws.sendText(txt).addListener(MainActivity.this);

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, "send msg success", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn) {
            new sendMsg().execute();
        }
    }

    private void findViews() {
        txtMsg = (EditText) findViewById(R.id.txtMsg);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);
    }

    @Override
    public void onStateChanged(WebSocket webSocket, WebSocketState webSocketState) throws Exception {

    }

    @Override
    public void onConnected(WebSocket webSocket, Map<String, List<String>> map) throws Exception {
        Log.e("tag", "connected");
    }

    @Override
    public void onConnectError(WebSocket webSocket, WebSocketException e) throws Exception {
        e.printStackTrace();
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
        Log.e("msg",s);
        // Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
        e.printStackTrace();
    }

    @Override
    public void onFrameError(WebSocket webSocket, WebSocketException e, WebSocketFrame webSocketFrame) throws Exception {
        e.printStackTrace();
    }

    @Override
    public void onMessageError(WebSocket webSocket, WebSocketException e, List<WebSocketFrame> list) throws Exception {
        e.printStackTrace();
    }

    @Override
    public void onMessageDecompressionError(WebSocket webSocket, WebSocketException e, byte[] bytes) throws Exception {
        e.printStackTrace();
    }

    @Override
    public void onTextMessageError(WebSocket webSocket, WebSocketException e, byte[] bytes) throws Exception {
        e.printStackTrace();
    }

    @Override
    public void onSendError(WebSocket webSocket, WebSocketException e, WebSocketFrame webSocketFrame) throws Exception {
        e.printStackTrace();
    }

    @Override
    public void onUnexpectedError(WebSocket webSocket, WebSocketException e) throws Exception {
        e.printStackTrace();
    }

    @Override
    public void handleCallbackError(WebSocket webSocket, Throwable throwable) throws Exception {
        throwable.printStackTrace();
    }


    @Override
    public void onSendingHandshake(WebSocket webSocket, String s, List<String[]> list) throws Exception {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

}
