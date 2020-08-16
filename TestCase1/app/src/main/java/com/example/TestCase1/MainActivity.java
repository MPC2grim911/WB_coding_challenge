package com.example.TestCase1;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    //Future note:
    // -change TextView message for installation into a pop up tab message

    private ImageButton Bopen; //wifiman
    private TextView text; //text


    @Override
    protected void onCreate(Bundle savedInstanceState) { //on create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Sets app to landscape mode only

        //Define variables to view ids
        Bopen=(ImageButton) findViewById(R.id.imageButton); //wifiMan page Id
        Bopen.setScaleType(ImageView.ScaleType.FIT_XY); //scale to size

        text = (TextView) findViewById(R.id.textView2);

        //check if package exists
        if(isPackageInstalled("com.ubnt.usurvey",getPackageManager())){
            text.setVisibility(View.GONE);
        }
        else {

            text.setVisibility(View.VISIBLE);
        }

        //testcase 1: launch wifiman
        Bopen.setOnClickListener(new View.OnClickListener() { //code to launch WiFiman
            @Override
            public void onClick(View view) {


                Intent i=getPackageManager().getLaunchIntentForPackage("com.ubnt.usurvey"); //Fixed Postnote: Difficult package name TT

                //Note: Always make sure app exists before starting activity
                if(i.resolveActivity(getPackageManager()) != null) { //Future note: pretty sure this might be the wrong fromat
                    startActivity(i);
                }
            }
        });

    }

    //Check if application is installed
    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}