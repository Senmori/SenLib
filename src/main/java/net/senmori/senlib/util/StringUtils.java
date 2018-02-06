package net.senmori.senlib.util;

public class StringUtils {

    public static boolean equals(String string, String other) {
        return string != null && other != null && (!string.isEmpty() && !other.isEmpty()) && string.equals(other);
    }
}
