package org.core.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Cooper (linh.nguyenduy@navercorp.com)
 */
public class CentralLogger {

    private static final String LOG_FILE_PATH = "/Users/YXH07YQ9Q7/Documents/projects/SagaDemo/output.log";
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    private static final Object LOCK = new Object();

    public static void appendLogToFile(String logEntry) {
        synchronized (LOCK) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
                String timestamp = df.format(new Date());
                writer.write(timestamp + "-" + logEntry + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
