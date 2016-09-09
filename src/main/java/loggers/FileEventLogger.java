package loggers;

import events.Event;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {
    protected static final String WRITE_ENCODING = "UTF-8";
    protected static final boolean APPEND_TO_FILE = true;
    final String newLineDelimiter = System.getProperty("line.separator");

    private String fileName;
    protected File file;

    protected FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    public void logEvent(Event event) {
        try {
            String logMessage = event.toString() + newLineDelimiter;
            FileUtils.writeStringToFile(file, logMessage, WRITE_ENCODING, APPEND_TO_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        this.file = new File(fileName);
        if (!file.canWrite()) throw new IOException("Can not write to file=" + fileName);
    }
}
