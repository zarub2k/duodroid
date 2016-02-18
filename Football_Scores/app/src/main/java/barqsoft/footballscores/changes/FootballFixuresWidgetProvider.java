package barqsoft.footballscores.changes;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

import barqsoft.footballscores.R;

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
        Log.v(LOG_TAG, "Inside onReceive method: " + intent.getAction());

        if (Constant.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            final String fixuresJson = intent.getStringExtra(Constant.FIXURES_DATA);
            Log.v(LOG_TAG, "Fixures Json available here is: " + fixuresJson);
            Log.v(LOG_TAG, "Success!!!!");

            final int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            updateWidgetUi(context, widgetId, fixuresJson);
        }

        super.onReceive(context, intent);
    }

    private void updateWidgetUi(Context context, int widgetId, String fixuresJson) {
        final List<Fixure> fixures = FixuresJsonProcessor.getInstance().getFixures(fixuresJson);
        if (fixures == null || fixures.isEmpty()) {
            Log.v(LOG_TAG, "Fixures list is empty");
            return;
        }

        Log.v(LOG_TAG, "Size of fixures available: " + fixures.size());

        final Fixure fixure = fixures.get(0);
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.appwidget_item);
        remoteViews.setTextViewText(R.id.home_name, fixure.getHomeTeamName());
        remoteViews.setTextViewText(R.id.away_name, fixure.getAwayTeamName());

        final Intent intent = new Intent(context, FixuresRemoteViewService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        remoteViews.setRemoteAdapter(widgetId, intent);

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
}
