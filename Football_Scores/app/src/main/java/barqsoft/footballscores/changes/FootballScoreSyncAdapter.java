package barqsoft.footballscores.changes;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * @author tham
 */
public class FootballScoreSyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String LOG_TAG = FootballScoreSyncAdapter.class.getSimpleName();

    public static final String ACTION_DATA_UPDATED = "barqsoft.footballscores.changes.ACTION_DATA_UPDATED";

    public FootballScoreSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s,
                              ContentProviderClient contentProviderClient,
                              SyncResult syncResult) {

    }
}
