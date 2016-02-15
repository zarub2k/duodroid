package barqsoft.footballscores.changes;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.R;

/**
 * @author tham
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FixuresRemoteViewFactory implements RemoteViewsFactory {
    private static final String LOG_TAG = FixuresRemoteViewFactory.class.getSimpleName();

    private Context context;
    private int widgetId;

    List<Fixure> fixures = new ArrayList<>(10);

    public FixuresRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        fixures.add(getFixure("H1", "A1"));
        fixures.add(getFixure("H2", "A2"));
        fixures.add(getFixure("H3", "A3"));
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return fixures.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.appwidget_item);
        final Fixure fixure = fixures.get(i);
        remoteViews.setTextViewText(R.id.home_name, fixure.getHomeTeamName());
        remoteViews.setTextViewText(R.id.away_name, fixure.getAwayTeamName());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Fixure getFixure(String homeTeam, String awayTeam) {
        final Fixure fixure = new Fixure();
        fixure.setHomeTeamName(homeTeam);
        fixure.setAwayTeamName(awayTeam);
        return fixure;
    }
}
