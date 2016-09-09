package loggers;

import events.Event;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache = new ArrayList<Event>();

    private CacheFileEventLogger(String fileName, Integer cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);
        if (cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void writeEventsFromCache() {
        StringBuilder sb = new StringBuilder();
        for (Event event : cache) {
            sb.append(event.toString()).append(newLineDelimiter);
        }
        try {
            FileUtils.writeStringToFile(file, sb.toString(), WRITE_ENCODING, APPEND_TO_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void destroy() {
        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }
}
