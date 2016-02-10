package barqsoft.footballscores.changes;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author tham
 */
public class FootballScoreWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG = FootballScoreWidgetProvider.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.v(LOG_TAG, "Inside onUpdate method");
//        for (int appWidgetId : appWidgetIds) {
//            final RemoteViews widgetView = new RemoteViews(context.getPackageName(),
//                    R.layout.appwidget_view);
//
//            final Intent intent = new Intent(context, MainActivity.class);
//            final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            widgetView.setOnClickPendingIntent(R.id.widget_label, pendingIntent);
//
//            appWidgetManager.updateAppWidget(appWidgetId, widgetView);
//        }

        for (int appWidgetId : appWidgetIds) {
            final Intent intent = new Intent(context, FootballFixuresService.class);
            intent.putExtra(Constant.WIDGET_ID, appWidgetId);
            context.startService(intent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.v(LOG_TAG, "Inside onReceive method");
        Log.v(LOG_TAG, "Data available here is: " + intent.getStringExtra(Constant.FIXURES_DATA));

        if (Constant.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            Log.v(LOG_TAG, "Success!!!!");
        }


//        super.onReceive(context, intent);
//
//        if (FootballScoreSyncAdapter.ACTION_DATA_UPDATED.equals(intent.getAction())) {
//            context.startService(new Intent(context, FootballFixuresService.class));
//        }
    }
}
