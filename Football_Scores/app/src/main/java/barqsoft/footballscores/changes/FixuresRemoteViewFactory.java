package barqsoft.footballscores.changes;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.R;

/**
 * @author tham
 *
 * Factory class implementation for remote view
 */
public class FixuresRemoteViewFactory implements RemoteViewsFactory {
    private static final String LOG_TAG = FixuresRemoteViewFactory.class.getSimpleName();

    private Context context;
    private int widgetId;
    private Intent intent_;

    List<Fixure> fixures_ = new ArrayList<>(10);

    public FixuresRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        intent_ = intent;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        populateFixures(intent);
    }

    @Override
    public void onCreate() {
        populateFixures(intent_);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return fixures_.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.v(LOG_TAG, "getView called for " + i);

        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.appwidget_item);
        final Fixure fixure = fixures_.get(i);
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
        return fixures_.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void populateFixures(Intent intent) {
        final String fixuresJson = intent.getStringExtra(Constant.FIXURES_DATA);
        final List<Fixure> fixures = FixuresJsonProcessor.getInstance().getFixures(fixuresJson);
        if (fixures == null || fixures.isEmpty()) {
            return;
        }

        fixures_.addAll(fixures);
        fixures_.add(getFixure("H1", "A1"));
//        fixures.add(getFixure("H2", "A2"));
//        fixures.add(getFixure("H3", "A3"));
    }

    private Fixure getFixure(String homeTeam, String awayTeam) {
        final Fixure fixure = new Fixure();
        fixure.setHomeTeamName(homeTeam);
        fixure.setAwayTeamName(awayTeam);
        return fixure;
    }
}
