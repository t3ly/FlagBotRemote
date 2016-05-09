package com.tyler.flagbotremote;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Tyler on 5/9/16.
 */
public class FlagbotCommand {
    OutputStream sender;

    public FlagbotCommand(BluetoothSocket btSocket){
        try {
            sender = btSocket.getOutputStream();
        }
        catch(IOException e){}
    }

    public void forward(){
        try {
            sender.write(Constants.MOVE_FORWARD);
        }
        catch(IOException e){}
    }

    public void backward(){
        try {
            sender.write(Constants.MOVE_BACKWARD);
        }
        catch(IOException e){}
    }

    public void turnRight(){

        try {
            sender.write(Constants.MOVE_RIGHT);
        }
        catch(IOException e){}
    }

    public void turnLeft(){
        try {
            sender.write(Constants.MOVE_LEFT);
        }
        catch(IOException e){}
    }

    public void stop(){
        try {
            sender.write(Constants.MOVE_STOP);
        }
        catch(IOException e){}
    }

    public void flagStart(){
        try {
            sender.write(Constants.FLAG_START);
        }
        catch(IOException e){}
    }

    public void flagStop(){
        try {
            sender.write(Constants.FLAG_STOP);
        }
        catch(IOException e){}
    }
}
