package log;

import org.apache.log4j.*;

import java.io.IOException;


public class ThothLoggerConfigurator {

    private static Layout thothLoggerLayout = new PatternLayout("[%p] %c - %m - Data wpisu: %d %n");

    public static void configure() {
        BasicConfigurator.configure();
    }

    public static Appender getFileAppender() {
        Appender fileAppender = null;

        try {
            String pathBase = System.getProperty("user.dir");
            pathBase = pathBase + "\\log\\logs";
            fileAppender = new FileAppender(thothLoggerLayout, pathBase);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return fileAppender;
    }

    public static Appender getFileAppender(String path) {
        Appender fileAppender = null;

        try {
            fileAppender = new FileAppender(thothLoggerLayout, path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return fileAppender;
    }

    public static Appender getConsoleAppender() {
        Appender consoleAppender = null;

        try {
            consoleAppender = new ConsoleAppender(thothLoggerLayout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return consoleAppender;
    }
}