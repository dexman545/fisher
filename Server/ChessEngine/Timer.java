package Server.ChessEngine;

/**
 * Created by Deximus-Maximus on 12/7/2016.
 */



public class Timer {
    public static void timer(int...args) {
        //handle timing, but not the actual "master" clock. Simply something to keep track of time, for use in pad and other such

    }

    public static boolean pad(int...args) {
        //handle padding.
       if (args[0] > 0) {
           timer(args[0]);
           return true;
        } else return false;

    }

    public static void clock(float...args) {
        //actually handle player's time. May need 2 functions to handle different players

    }

}
