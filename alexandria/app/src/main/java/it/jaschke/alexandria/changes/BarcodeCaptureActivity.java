package it.jaschke.alexandria.changes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.vision.barcode.BarcodeDetector;

import it.jaschke.alexandria.R;

/**
 * @author tham
 *
 * Activity to capture the Barcode using camera functionality.
 */
public class BarcodeCaptureActivity extends Activity {
    private static final String LOG_TAG = BarcodeCaptureActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        startCameraPreview();

        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
    }

    private void startCameraPreview() {
        Log.i(LOG_TAG, "Enters startCameraPreview()");
        
        CameraSourcePreview preview = (CameraSourcePreview) findViewById(R.id.preview);
        findViewById(R.id.graphicOverlay);
    }
}
