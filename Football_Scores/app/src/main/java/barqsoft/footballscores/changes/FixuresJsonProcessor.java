package barqsoft.footballscores.changes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tham
 */
public class FixuresJsonProcessor {
    private static final String LOG_TAG = FixuresJsonProcessor.class.getSimpleName();
    private static final FixuresJsonProcessor INSTANCE = new FixuresJsonProcessor();

    private FixuresJsonProcessor() {}

    public static synchronized FixuresJsonProcessor getInstance() {
        return INSTANCE;
    }

    public List<Fixure> getFixures(String fixuresJson) {
        if (fixuresJson == null) {
            return new ArrayList<>(2);
        }

        try {
            JSONArray fixuresJsonArray = new JSONObject(fixuresJson)
                    .getJSONArray(FixureJsonKeys.FIXURES.getKey());
            return getFixures(fixuresJsonArray);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error happened while parsing fixures: " + e);
        }

        return new ArrayList<>(2);
    }

    private List<Fixure> getFixures(JSONArray fixuresJsonArray) throws JSONException {
        List<Fixure> fixures = new ArrayList<Fixure>(fixuresJsonArray.length());
        for (int i = 0; i < fixuresJsonArray.length(); i++) {
            fixures.add(getFixure(fixuresJsonArray.getJSONObject(i)));
        }

        return fixures;
    }

    private Fixure getFixure(JSONObject fixureJson) throws JSONException {
        final Fixure fixure = new Fixure();
        fixure.setHomeTeamName(fixureJson.getString(FixureJsonKeys.HOME_TEAM_NAME.getKey()));
        fixure.setAwayTeamName(fixureJson.getString(FixureJsonKeys.AWAY_TEAM_NAME.getKey()));
        return fixure;
    }
}
