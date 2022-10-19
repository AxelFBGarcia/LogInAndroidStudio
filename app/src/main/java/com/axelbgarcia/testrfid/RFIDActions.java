package com.axelbgarcia.testrfid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.axelbgarcia.testrfid.intefaces.login.LogIn;
import com.zebra.rfid.api3.TagData;

public class RFIDActions extends AppCompatActivity implements RFIDHandler.ResponseHandlerInterface{
    static final String TAG = "RFID_SAMPLE";

    RFIDHandler rfidHandler;
    public TextView textrfid;
    public TextView statusTextViewRFID = null;
    Button out;
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences reset = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = reset.edit();

        RFIDHandler rFIDHandler = new RFIDHandler();
        this.rfidHandler = rFIDHandler;
        rFIDHandler.onCreate(this);
        setContentView(R.layout.activity_main);
        this.textrfid = (TextView) findViewById(R.id.textView);
        this.statusTextViewRFID = (TextView) findViewById(R.id.textConnection);
        this.rfidHandler = rFIDHandler;
        rFIDHandler.onCreate(this);

        SharedPreferences preferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);

        str = preferences.getString("remember", "false");
        if (str == "true"){
            editor.putString("resChBox", "true");
            editor.commit();
        }

        resetCheckedBox();
    }

    public void resetCheckedBox(){
        out = findViewById(R.id.logOut);
        SharedPreferences reset = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = reset.edit();

        out.setOnClickListener(view -> {
            editor.clear();
            editor.commit();

            finish();
            startActivity(new Intent(RFIDActions.this, LogIn.class));
        });
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

                }
            });
            this.rfidHandler.performInventory();
            return;
        }else{

        }
        this.rfidHandler.stopInventory();
    }

}