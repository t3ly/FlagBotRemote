package com.tyler.flagbotremote;

/**
 * Created by Tyler on 5/8/16.
 * Help from Lambda class
 * MUST MATCH UP WITH CONSTANTS IN ARDUINO CODE!!!!
 */
public interface Constants {

    //Bluetooth connect constants
    public static final int BLUETOOTH_CONNECT = 1;
    public static final int BLUETOOTH_DISCONNECTED = 2;
    public static final int CONNECTION_SUCCESS = 3;
    public static final int CONNECTION_FAILED = 4;

    //Draw constants
    public static final int MOVE_FORWARD   = 11;
    public static final int MOVE_BACKWARD  = 12;
    public static final int MOVE_RIGHT     = 13;
    public static final int MOVE_LEFT      = 14;
    public static final int MOVE_STOP      = 15;
    public static final int FLAG_START     = 16;
    public static final int FLAG_STOP      = 17;
    public static final int FLAG_LEFT      = 18;
    public static final int FLAG_RIGHT     = 19;
}
