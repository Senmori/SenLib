package net.senmori.senlib.util;

public class StringUtils {

    public static boolean equals(String string, String other) {
        if(string == null && other == null) {
            return true;
        }
        if((string != null && other != null) && string.isEmpty() && other.isEmpty()) {
            return true;
        }
        return string != null && other != null && (!string.isEmpty() && !other.isEmpty()) && string.equals(other);
    }
}
