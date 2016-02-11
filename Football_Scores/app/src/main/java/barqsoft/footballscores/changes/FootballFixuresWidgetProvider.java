package barqsoft.footballscores.changes;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author tham
 */
public class FootballFixuresWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG = FootballFixuresWidgetProvider.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
        Log.v(LOG_TAG, "Inside onReceive method: " + intent.getAction());

        if (Constant.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            final String fixuresJson = intent.getStringExtra(Constant.FIXURES_DATA);
            Log.v(LOG_TAG, "Fixures Json available here is: " + fixuresJson);
            Log.v(LOG_TAG, "Success!!!!");
        }

        super.onReceive(context, intent);
    }
}
