package net.senmori.senlib;

import java.util.logging.Logger;

public class LogHandler {

    private static Logger logger = Logger.getLogger("SenLib");

    public static Logger getLogger() {
        return logger;
    }

    public static void info(String message) {
        logger.info(message);
    }
}
