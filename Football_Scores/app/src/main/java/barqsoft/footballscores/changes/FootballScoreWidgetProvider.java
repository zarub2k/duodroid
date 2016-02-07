package barqsoft.footballscores.changes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * @author tham
 */
public class FootballScoreWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

        context.startService(new Intent(context, FootballScoreIntentService.class));
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (FootballScoreSyncAdapter.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            context.startService(new Intent(context, FootballScoreIntentService.class));
        }
    }
}
