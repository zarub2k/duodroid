package barqsoft.footballscores.changes;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViewsService;

/**
 * @author tham
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FootballFixuresRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return null;
    }
}
