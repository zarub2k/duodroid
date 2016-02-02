package barqsoft.footballscores.changes;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import barqsoft.footballscores.R;

/**
 * @author tham
 *
 * Uri connector class for Football application
 */
public class FootballUriConnector {
    private static final String LOG_TAG = FootballUriConnector.class.getSimpleName();
    private static final FootballUriConnector INSTANCE = new FootballUriConnector();

    private static final String BASE_URL = "http://api.football-data.org/alpha/fixtures";
    private static final String QUERY_TIME_FRAME = "timeFrame";

    private FootballUriConnector() {}

    public static final synchronized FootballUriConnector getInstance() {
        return INSTANCE;
    }

    public String getJson(Context context, String timeFrame) {
        Log.v(LOG_TAG, "Fetch JSON for " + timeFrame);
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder jsonStringBuilder = new StringBuilder();

        try {
            final URL url = new URL(getFixuresUri(timeFrame).toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("X-Auth-Token", context.getString(R.string.api_key));
            urlConnection.connect();

            final InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(line + "\n");
            }

            if (jsonStringBuilder.length() == 0) {
                return null;
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error happened while fetching data: " + e.getMessage());
        } finally {
            closeResource(urlConnection, bufferedReader);
        }

        Log.v(LOG_TAG, "JSON response: " + jsonStringBuilder.toString());
        return jsonStringBuilder.toString();
    }

    /**
     * Method to close the connection once the data is read from the connection
     *
     * @param connection
     * @param reader
     */
    private void closeResource(HttpURLConnection connection, BufferedReader reader) {
        if (connection != null) {
            connection.disconnect();
        }

        if (reader == null) {
            return;
        }

        try {
            reader.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while closing the stream");
        }
    }

    private Uri getFixuresUri(String timeFrame) {
        Uri fixuresUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_TIME_FRAME, timeFrame)
                .build();

        return fixuresUri;
    }
}
