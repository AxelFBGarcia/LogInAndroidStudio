package com.axelbgarcia.testrfid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.zebra.rfid.api3.TagData;
import com.axelbgarcia.testrfid.RFIDHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements RFIDHandler.ResponseHandlerInterface{
    static final String TAG = "RFID_SAMPLE";

    RFIDHandler rfidHandler;
    public TextView textrfid;
    public TextView statusTextViewRFID = null;
    int tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RFIDHandler rFIDHandler = new RFIDHandler();
        this.rfidHandler = rFIDHandler;
        rFIDHandler.onCreate(this);
        setContentView(R.layout.activity_main);
        this.textrfid = (TextView) findViewById(R.id.textView);
        this.statusTextViewRFID = (TextView) findViewById(R.id.textStatus);
        this.rfidHandler = rFIDHandler;
        rFIDHandler.onCreate(this);


    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.rfidHandler.onPause();
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        this.statusTextViewRFID.setText(this.rfidHandler.onResume());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.rfidHandler.onDestroy();
    }

    @Override
    public void handleTagdata(TagData[] tagData) {

    }

    @Override
    public void handleTriggerPress(boolean pressed) {
        if (pressed) {
            runOnUiThread(new Runnable() {
                public void run() {
                    MainActivity.this.textrfid.setText("hello");
                }
            });
            this.rfidHandler.performInventory();
            return;
        }else{
            System.out.println(tag);
        }
        this.rfidHandler.stopInventory();
    }

}