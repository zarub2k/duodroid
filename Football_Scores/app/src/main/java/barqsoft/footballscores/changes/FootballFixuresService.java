package barqsoft.footballscores.changes;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * @author tham
 *
 * Intent service for fetching football score in the background
 */
public class FootballFixuresService extends IntentService {
    private static final String LOG_TAG = FootballFixuresService.class.getSimpleName();

    public FootballFixuresService() {
        super("FootballFixuresService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG_TAG, "Enters onHandleIntent");

        final String fixuresJson = FootballUriConnector.getInstance().getJson(this, "n3");
//        FixuresJsonProcessor.getInstance().getFixures(fixuresJson);

        final int widgetId = intent.getIntExtra(Constant.WIDGET_ID, 0);
        Log.v(LOG_TAG, "Widget Id in onHandleIntent: " + widgetId);

//        final Intent broadcastIntent = new Intent(Constant.ACTION_DATA_UPDATED);
//        broadcastIntent.putExtra(Constant.FIXURES_DATA, fixuresJson);
//        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
//        broadcastManager.sendBroadcast(broadcastIntent);

//        final AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
//        widgetManager.updateAppWidget();

        final Intent broadcastIntent = new Intent(Constant.ACTION_DATA_UPDATED);
        broadcastIntent.putExtra(Constant.FIXURES_DATA, fixuresJson);
        broadcastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        sendBroadcast(broadcastIntent);

        //Stop the current service execution
        this.stopSelf();
    }
}
