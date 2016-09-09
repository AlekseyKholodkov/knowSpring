import beans.Client;
import events.Event;
import loggers.EventLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    private Client client;
    private EventLogger eventLogger;

    public App() {
    }

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public void logEvent(Event event) {
        event.setMsg("Event for user " + client.getId());
        String newMsg = event.getMsg().replaceAll(String.valueOf(client.getId()), client.getFullName());
        event.setMsg(newMsg);
        eventLogger.logEvent(event);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = ctx.getBean("app", App.class);
        Event event = ctx.getBean("event", Event.class);
        app.logEvent(event);
    }
}
