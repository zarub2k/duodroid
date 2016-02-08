package barqsoft.footballscores.changes;

import java.util.List;

/**
 * @author tham
 */
public class FixuresJsonProcessor {
    private static final String LOG_TAG = FixuresJsonProcessor.class.getSimpleName();
    private static final FixuresJsonProcessor INSTANCE = new FixuresJsonProcessor();

    private FixuresJsonProcessor() {}

    public static synchronized FixuresJsonProcessor getInstance() {
        return INSTANCE;
    }

    public List<Fixure> getFixures(String fixuresJson) {
        return null;
    }

    private Fixure getFixure(String fixureJson) {
        return null;
    }
}
