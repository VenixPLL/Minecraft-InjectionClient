package me.dickmeister.common.util;

import me.dickmeister.client.ClientMain;

public class LogUtil {

    public static void log(String msg, Object... args){
        if(!ClientMain.DEBUG) return;
        System.out.println(String.format(msg, args));
    }

    public static void err(String msg, Object... args){
        if(!ClientMain.DEBUG) return;
        System.err.println(String.format(msg, args));
    }

}
