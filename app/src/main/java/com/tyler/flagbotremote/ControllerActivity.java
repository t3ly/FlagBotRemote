package com.tyler.flagbotremote;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Tyler on 5/8/16.
 */
public class ControllerActivity extends Activity{

    private Handler handler;

    private ImageButton bUp, bDown, bRight, bLeft, fGo, fStop;


    private BluetoothDevice desiredDevice;
    private FlagbotCommand controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        init();
        startDPadlisteners();
    }

    private void init(){

        // Set up a handler to handle Bluetooth messages
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case Constants.BLUETOOTH_CONNECT:
                        if(msg.arg1 == Constants.CONNECTION_SUCCESS){
                            Toast.makeText(getApplicationContext(), "ESTA LIT!", Toast.LENGTH_SHORT).show();
                            System.out.println("Handler is working");
                        }
                        else if(msg.arg1 == Constants.CONNECTION_FAILED){
                            Toast.makeText(getApplicationContext(), "N'EST PAS LIT!", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        };

        //Get the base application (which has always been running) and initialize
        //an outputstream using the global BTsocket
        desiredDevice = getIntent().getExtras().getParcelable("DESIRED_DEVICE");
        connect(desiredDevice);

        //Initialize the UI elements
        bUp = (ImageButton)findViewById(R.id.buttonForward);
        bDown = (ImageButton)findViewById(R.id.buttonDown);
        bRight = (ImageButton)findViewById(R.id.buttonRight);
        bLeft = (ImageButton)findViewById(R.id.buttonLeft);
        fGo = (ImageButton)findViewById(R.id.flagStart);
        fStop = (ImageButton)findViewById(R.id.flagStop);
    }

		/*
		 * Set Touch Listeners for each button
	   */	 
    private void startDPadlisteners() {

        bUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        controller.forward();
                        break;
                    case MotionEvent.ACTION_UP:
                        controller.stop();
                        break;
                }

                return true;
            }
        });

        bDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        controller.backward();
                        break;
                    case MotionEvent.ACTION_UP:
                        controller.stop();
                        break;
                }

                return true;
            }
        });

        bRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        controller.turnRight();
                        break;
                    case MotionEvent.ACTION_UP:
                        controller.stop();
                        break;
                }

                return true;
            }
        });

        bLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        controller.turnLeft();
                        break;
                    case MotionEvent.ACTION_UP:
                        controller.stop();
                        break;
                }

                return true;
            }
        });

        fGo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        controller.flagStart();
                        break;
                }

                return true;
            }
        });

        fStop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        controller.flagStop();
                        break;
                }

                return true;
            }
        });

    }

		/*
		 * Connects to specified bluetooth device which should be connected to
		 * Arduino
		 */	 
    private synchronized void connect(BluetoothDevice tmp){
        Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_LONG).show();
        FlagbotConnectThread connectDrawbot = new FlagbotConnectThread(tmp, handler);
        connectDrawbot.start();
        controller = new FlagbotCommand(connectDrawbot.getBTSocket());
    }
}
