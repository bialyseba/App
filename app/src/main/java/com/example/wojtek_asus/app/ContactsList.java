package com.example.wojtek_asus.app;

import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

//import com.example.messagingtutorialskeleton.R;

public class ContactsList extends AppCompatActivity {

    protected Call call;
    Button btcall;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
        }
        @Override
        public void onCallEstablished(Call establishedCall) {
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
           // Intent intent = new Intent(getApplicationContext(), CallActivity.class);
            //startActivity(intent);
            //incoming CallActivity was picked up
        }
        @Override
        public void onCallProgressing(Call progressingCall) {
            //CallActivity is ringing
        }
        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            //don't worry about this right now
        }

    }

    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            //Pick up the call!
            call = incomingCall;
           // Intent intent = new Intent(getApplicationContext(),CallActivity.class);
            call.answer();
            call.addCallListener(new SinchCallListener());
            btcall.setText("Hang Up");
            Toast.makeText(getApplicationContext(), "DZWONIO!!!!!!",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btcall = (Button) findViewById(R.id.btcall);


        final SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                        .userId(User.getInstance().username)
                //.userId("wojtekkwa@o2.pl")
                .applicationKey("3cc0c725-63fb-4410-a505-c438eeea1041")
                .applicationSecret("2V4L1bcagE+VcapWvc8gig==")
                .environmentHost("sandbox.sinch.com")
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.start();

        //Rozpoczęcie nasłuchiwania połączeń przychodzących
        sinchClient.startListeningOnActiveConnection();

        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());

        btcall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View vie3w) {
                if (call == null) {
                    call = sinchClient.getCallClient().callUser("call-recipient-id");
                    btcall.setText("Zawieś");
                } else {
                    call.hangup();
                    btcall.setText("Zadzwon");
                }
                call.addCallListener(new SinchCallListener());
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ContactsList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.wojtek_asus.app/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ContactsList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.wojtek_asus.app/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    // SimpleAdapter adapter = new SimpleAdapter(this, );


}

