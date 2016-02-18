package barqsoft.footballscores.changes;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViewsService;

/**
 * @author tham
 *
 * Fixures remote view implementation for widgets list view
 */
public class FixuresRemoteViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FixuresRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
