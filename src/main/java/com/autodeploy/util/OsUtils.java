package com.autodeploy.util;

public final class OsUtils {

    private static String OS;

    public static String getOSName(){
        if (OS == null){
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows(){
        return getOSName().startsWith("Windows");
    }

    public static boolean isUnix(){
        return false;
    }
}
