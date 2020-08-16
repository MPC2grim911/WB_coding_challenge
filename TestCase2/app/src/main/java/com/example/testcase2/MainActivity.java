package com.example.testcase2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.res.Configuration.KEYBOARD_QWERTY;

public class MainActivity extends AppCompatActivity {
    //Future Notes:
    //  -needs to refine detection of connected/attached keyboards so that virtual keyboard doesn't
    //      show on clicking EditText
    //  -for app permissions
    //      *Fix app crash that happens when enabling app permissions in settings

    //global variables
    private WifiManager wifiManager;
    private List<ScanResult> results;
    private ScanResult res = null;

    private String wifiSSID, wifiExists; //wifi ssid strings
    private String wifiStrgth = "0 (0 dBm)", wifiAvg = "0.0 (0.0 dBm)";

    private EditText wifiName; //display variables
    private TextView wifiExst, wifiStrength, wifiAverage;// appLocOn;
    private Button submitBtn;
    private ProgressBar strgthWifi, avgWifi;

    private int count = 0, levelTotal = 0, wifiTotal = 0, p1 = 5, p2 = 5; //Math variables
    private Double levelAvg = 0.0, signalAvg = 0.0;

    private boolean existWifi = false, wifiRedo = false, barUI = false; // additional Handlers
    private Handler handler = new Handler();
    DecimalFormat dForm = new DecimalFormat("0.0#");
    DecimalFormat dForm2 = new DecimalFormat("-0.0#");

    //private LocationManager locationManager;
    //String provider = "Pleasee enable locations";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Sets app to landscape mode only

        //variable to app id
        wifiName = (EditText) findViewById(R.id.editTextWifiName); //wifi SSID id

        wifiExst = (TextView) findViewById(R.id.textViewWifiExist);
        //appLocOn= (TextView) findViewById(R.id.textViewWifiExist2);
        wifiStrength = (TextView) findViewById(R.id.textViewWifiStrength);
        wifiAverage = (TextView) findViewById(R.id.TextViewDecimalAverage);

        strgthWifi = (ProgressBar) findViewById(R.id.progressBar);
        avgWifi = (ProgressBar) findViewById(R.id.progressBar2);

        submitBtn = (Button) findViewById(R.id.button); //submit button id


        //set parameters
        wifiExst.setText("");
        wifiStrength.setText(String.valueOf(wifiStrgth));
        wifiAverage.setText(String.valueOf(wifiAvg));

        strgthWifi.setProgress(5);
        avgWifi.setProgress(5);


        /*
        //This section is formal format for checking app permissions
        //Resolved issue with personal (brute force) method as seen in Thread tUI and related functions

        if (ContextCompat.checkSelfPermission(
                CONTEXT, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            appLocOn.setVisibility(View.GONE);
        } else if (shouldShowRequestPermissionRationale(...)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            showInContextUI(...);
            Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(myIntent);
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissions(CONTEXT,
                    new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_CODE);

        }*/

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        getLocation();








        //wifiManager credentials
        wifiManager= (WifiManager) getSystemService(Context.WIFI_SERVICE);

        statusCheck(); //Future Note: this may not be related to enable location for in app permissions

        //if scan results are saved (fixed) see fixed postnote in submitBtn onClickListener
        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() { //wifi broacast credentials
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    scanSuccess();
                } else {
                    // scan failure handling
                    scanFailure();
                }

            }
        };
        IntentFilter intentFilter = new IntentFilter(); //wifi scan credentials
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiScanReceiver, intentFilter);

        final boolean scanSuccess = wifiManager.startScan();
        if (!scanSuccess) {
            // scan failure handling
            scanFailure();
        }
        else{
            scanSuccess();
        }

        //Edit Text settings: more after button click
        wifiName.setOnClickListener(new View.OnClickListener() { //wifi scanner user input UI
            @Override
            public void onClick(View view) {
                wifiName.setCursorVisible(true);

                if(isKeyboardConnected()){
                    hideSoftKeyBoard();
                }
                else{
                    showSoftKeyboard();
                }
            }
        });



        // Thread Declarations

        //UI+permission update: every second //Note: not necessary anymore due to reread and modifications
        /*final Thread tUI = new Thread(){ //updates page with app location permissions every seccond
            @Override
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1000); //updates every second
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                scanFailure();

                                if(barUI) {//updates progress bar since number text goes to 0 when disabled
                                    p1 = 5;
                                    p2 = 5;
                                    strgthWifi.setProgress(p1);
                                    avgWifi.setProgress(p2);
                                    barUI = false;
                                }
                            }
                        });

                    }catch (InterruptedException u){
                    }
                }
            }
        };*/



        //main task screen update - every second
        final Thread tWifi = new Thread(){ //this enables on screen text to update every minute
            @Override
            public void run(){
                while(!isInterrupted()){
                    try{
                        Thread.sleep(1000);//60 seconds = 60000 ms, 1 sec = 1000
                        runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.R)
                            @Override
                            public void run() {
                                //update wifi results
                                //List<ScanResult> wifiScan = wifiManager.getScanResults();
                                if(wifiRedo){
                                    scanSuccess(); //Future check: db values might not be updating
                                    wifiRedo = false;
                                }
                                else{
                                    scanFailure();
                                }

                                updateScanInfo(wifiSSID);

                                if(existWifi) {
                                    count++;
                                    //get wifi strength
                                    int sigLevel = getSignalLevel(res.level);


                                    if(count >= 60) { //change to average
                                        //calculate average
                                        signalAvg = averageWifiStrength(res.level);
                                        levelAvg = averagelevelStrength(sigLevel);

                                        wifiAvg = dForm.format(levelAvg) + " (" + dForm.format(signalAvg) + " dBm)";

                                        p2 = progressLevel(signalAvg);
                                        p1 = 5;
                                        wifiStrgth = "";
                                    }
                                    else{
                                        wifiStrgth = String.valueOf(sigLevel) + " (" + String.valueOf(res.level)+" dBm)"; //see getSignalLevel function for fixed postnote

                                        //update progress bar
                                        p1 = progressLevel(res.level);
                                        p2 = 5;

                                        wifiTotal += res.level;
                                        levelTotal += sigLevel;
                                        wifiAvg = "";
                                    }
                                }

                                if(barUI) {//updates progress bar since number text goes to 0 when disabled
                                    p1 = 5;
                                    p2 = 5;
                                    strgthWifi.setProgress(p1);
                                    avgWifi.setProgress(p2);
                                    barUI = false;
                                }


                                //update text here

                                wifiExst.setText(wifiExists);

                                wifiStrength.setText(wifiStrgth);
                                wifiAverage.setText(wifiAvg);

                                //update progress bar
                                strgthWifi.setProgress(p1);
                                avgWifi.setProgress(p2);
                            }
                        });

                    } catch (InterruptedException e){
                    }
                }
            }
        };

        //Thread enabling
        //tUI.start(); //new threads need a start function to enable
        tWifi.start(); //Note: could also use function with the declarations above but makes code look messy


        //Button settings and functions
        submitBtn.setOnClickListener(new View.OnClickListener() { //code to get user input of wifi SSID
            @Override
            public void onClick(View view) {
                wifiName.setCursorVisible(false); //UI
                hideSoftKeyBoard();

                String newSSID = wifiName.getText().toString();

                //resets for every click
                if(!newSSID.equals(wifiSSID)){ //if new SSID, then reset counter variables
                    wifiTotal = 0;
                    levelTotal = 0;
                    count = 0;
                    p1 = 5;
                    p2 = 5;
                    wifiSSID = newSSID;
                    //wifiStrgth = "wait";
                    //wifiAvg = "";
                }

                //DEBUG
                /*if(results.isEmpty()){
                    System.out.println("results are empty!");
                }
                else{
                    System.out.println("results is full");
                }*/

                //check if location permissions are on
                if(results.isEmpty()){ //Fixed Postnote: apparently the device also needs to give permission to run (--)
                    wifiExists = "Please turn on location in app permissions & submit again";
                    wifiRedo = true;
                    //appLocOn.setVisibility(View.GONE);
                }
                else {
                    //check if wifi exists or not
                    if (existingSSID(wifiSSID)) {
                        existWifi = true; //Note: probably merge this with if statement
                        wifiExists = "";
                    } else {
                        wifiExists = "SSID name does not exist";
                        wifiStrgth = "0 (0 dBm)";
                        wifiAvg = "0.0 (0.0 dBm)";
                        existWifi = false;
                        wifiTotal = 0;
                        levelTotal = 0;
                        p1 = 5;
                        p2 = 5;
                        //appLocOn.setVisibility(View.GONE);
                    }


                    /*if(tWifi.isAlive()){ //delete old thread to start new thread
                        tWifi.interrupt(); //Note: this format section doesn't work
                    }
                    tWifi.start();
                    */
                }
            }
        });


        //Editor text UI: keyboard done/enter enables submit button click
        wifiName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submitBtn.performClick();
                    return true;
                }
                return false;
            }
        });



    }




    //get access to location permission
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "your message", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //Get location
    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        }
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {



            locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates((LocationListener) this);
        }
    }*/


    //wifi manager scanning functions
    private void scanSuccess() { //store list of results
        results = wifiManager.getScanResults();
        scanCheck();
    }

    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        results = wifiManager.getScanResults();
        scanCheck();
    }

    //UI visuals and app permisssions check
    private void scanCheck(){ //if app location is on or not
        if(results.isEmpty()) {
            wifiExists = "Please turn on location in app permissions";
            barUI = true;
            return;
        }
        wifiExists = "";
        //appLocOn.setVisibility(View.GONE);
    }


    //Future Note: the following two functions may not be related to enable location for in app permissions
    //wifi location service check
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    //Wifi scanner UI for keyboards refinement if app is on (phone)
    public void showSoftKeyboard(){
        View view = this.getCurrentFocus();
        if(view.requestFocus()){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private boolean isKeyboardConnected() {
        return this.getResources().getConfiguration().keyboard == KEYBOARD_QWERTY;
    }


    //following two functions check SSIDs to existing ScanResult list
    private boolean existingSSID(String w){ //check scan list for existing ssids
        for (int i = 0; i < results.size(); i++) {
            String ssid =  results.get(i).SSID;
            if(ssid.equals(w)){
                res = results.get(i); //save information
                return true;
            }
        }
        return false;
    }

    private void updateScanInfo(String w){ //update scan list info
        for (int i = 0; i < results.size(); i++) {
            String ssid =  results.get(i).SSID;
            if(ssid.equals(w)){
                res = results.get(i); //save information
                return;
            }
        }
    }


    //Wifi signal get signal level from db values
    private int getSignalLevel(int i){
        //wifiManager.calculateSignalLevel(i); //Fixed postnote: for some reason calculate signal level crashes the app
        if(Math.abs(i) <= 30){
            return 4;
        }
        else if((Math.abs(i)> 30) && (Math.abs(i) <= 67)){
            return 3;
        }
        else if((Math.abs(i) >67) && (Math.abs(i) <= 70)){
            return 2;
        }
        else if((Math.abs(i)>70) && (Math.abs(i)<=80)){
            return 1;
        }
        else{
            return 0;
        }
    }

    //following two functions are for visual progress bar
    private int progressLevel(int i){
        if(Math.abs(i) <= 30){
            return 98;
        }
        else if((Math.abs(i)> 30) && (Math.abs(i) <= 67)){
            return 75;
        }
        else if((Math.abs(i) >67) && (Math.abs(i) <= 70)){
            return 45;
        }
        else if((Math.abs(i)>70) && (Math.abs(i)<=80)){
            return 15;
        }
        else{
            if(Math.abs(i) <= 100) {
                return 100 + i;
            }
            else{
                return 0;
            }

        }
    }

    private int progressLevel(double i){
        if(Math.abs(i) <= 30.0){
            return 98;
        }
        else if((Math.abs(i)> 30.0) && (Math.abs(i) <= 67.0)){
            return 75;
        }
        else if((Math.abs(i) >67.0) && (Math.abs(i) <= 70.0)){
            return 45;
        }
        else if((Math.abs(i)>70.0) && (Math.abs(i)<=80.0)){
            return 15;
        }
        else{
            if(Math.abs(i) <= 100) {
                return 100 + (int)i;
            }
            else{
                return 0;
            }
        }
    }


    //following two calculate Wifi SSID average strength
    private double averageWifiStrength(int i){ //calculates wifi SSID average
        wifiTotal += i;
        return (double) wifiTotal/count;
    }

    private double averagelevelStrength(int i){ //calculates wifi SSID level average
        levelTotal += i;
        return (double) levelTotal/count;
    }
}