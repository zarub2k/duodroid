package barqsoft.footballscores.changes;

/**
 * @author tham
 */
public class FootballUriConnector {
    private static final String LOG_TAG = FootballUriConnector.class.getSimpleName();
    private static final FootballUriConnector INSTANCE = new FootballUriConnector();

    private FootballUriConnector() {}

    public static final synchronized FootballUriConnector getInstance() {
        return INSTANCE;
    }

    public String getJson(String timeFrame) {
        return null;
    }
}
