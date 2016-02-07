package barqsoft.footballscores.changes;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * @author tham
 *
 * Intent service for fetching football score in the background
 */
public class FootballScoreIntentService extends IntentService {
    private static final String LOG_TAG = FootballScoreIntentService.class.getSimpleName();

    public FootballScoreIntentService() {
        super("FootballScoreIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG_TAG, "Enters onHandleIntent");
    }
}
