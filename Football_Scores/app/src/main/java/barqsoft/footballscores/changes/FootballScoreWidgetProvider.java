package barqsoft.footballscores.changes;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import barqsoft.footballscores.R;

/**
 * @author tham
 */
public class FootballScoreWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            final RemoteViews widgetView = new RemoteViews(context.getPackageName(),
                    R.layout.appwidget_view);
            appWidgetManager.updateAppWidget(appWidgetId, widgetView);
        }
    }
}
