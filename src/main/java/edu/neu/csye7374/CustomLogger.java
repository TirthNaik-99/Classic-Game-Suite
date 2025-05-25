package edu.neu.csye7374;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger implements GameObserver {
    private static CustomLogger instance;
    private final Logger logger;

    private CustomLogger() {
        this.logger = Logger.getLogger(CustomLogger.class.getName());
    }

    public static synchronized CustomLogger getInstance() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    public void log(String message) {
        logger.log(Level.INFO, message);
    }

    @Override
    public void update(GameEvent event) {
        log("Event: " + event.getType() + " - " + event.getData());
    }
}
