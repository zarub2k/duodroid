package barqsoft.footballscores.changes;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * @author tham
 *
 * Intent service for fetching football score in the background
 */
public class FootballScoreService extends IntentService {
    private static final String LOG_TAG = FootballScoreService.class.getSimpleName();

    public FootballScoreService() {
        super("FootballScoreService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG_TAG, "Enters onHandleIntent");

        final String fixuresJson = FootballUriConnector.getInstance().getJson(this, "n2");
        FixuresJsonProcessor.getInstance().getFixures(fixuresJson);
    }
}
