package com.example.testcase3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Future note: test and fix contents of Selenium FireFox and Selenium Chrome
//FIXME: Selenium Chrome code still needs to be completed for test (see SeleniumChrome for TODO)
//FIXME TODO: test to verify that SeleniumFireFox works
//FIXME TODO: test if UI elements work properly

public class MainActivity extends AppCompatActivity {

    private Button ffBtn, chrBtn;
    private TextView display, display2;

    String displayText = "", displayText2 = "";
    boolean assertResult, assertResult2, buttonPress = false, buttonPress2 = false;

    SeleniumChrome seleniumChrome = new SeleniumChrome();
    SeleniumFireFox seleniumFireFox = new SeleniumFireFox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //Sets app to landscape mode only

        ffBtn = (Button) findViewById(R.id.button);
        chrBtn = (Button) findViewById(R.id.button2);
        display = (TextView) findViewById(R.id.textView3);
        display2 = (TextView) findViewById(R.id.textView4);

        ffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayText = seleniumFireFox.res;
                assertResult = seleniumFireFox.searchResult;
                buttonPress = true;
                buttonPress2 = false;
            }
        });

        chrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayText2 = seleniumChrome.res;
                assertResult2 = seleniumChrome.searchResult;
                buttonPress = false;
                buttonPress2 = true;
            }
        });

        Thread thread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(30000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(buttonPress){
                                    if(buttonPress2) {
                                        display.setText("");
                                        display2.setText("");
                                    }
                                    else{
                                        display.setText(displayText);
                                        display2.setText(String.valueOf(assertResult));
                                    }
                                }
                                else{
                                    if(buttonPress2) {
                                        display.setText(displayText2);
                                        display2.setText(String.valueOf(assertResult2));
                                    }
                                    else{
                                        display.setText("");
                                        display2.setText("");
                                    }
                                }

                            }
                        });

                    } catch (InterruptedException e){

                    }
                }
            }
        };

        thread.start();

    }
}