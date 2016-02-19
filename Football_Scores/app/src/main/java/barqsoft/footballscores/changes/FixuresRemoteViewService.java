package barqsoft.footballscores.changes;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * @author tham
 *
 * Fixures remote view implementation for widgets list view
 */
public class FixuresRemoteViewService extends RemoteViewsService {
    private static final String LOG_TAG = FixuresRemoteViewService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.v(LOG_TAG, "onGetViewFactory method is called");
        return new FixuresRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
