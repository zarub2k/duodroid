package it.jaschke.alexandria.changes;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.vision.barcode.BarcodeDetector;

import it.jaschke.alexandria.R;

/**
 * @author tham
 *
 * Activity to capture the Barcode using camera module
 */
public class BarcodeCaptureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        startCameraPreview();

        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
    }

    private void startCameraPreview() {
//        CameraSourcePreview preview = (CameraSourcePreview) findViewById(R.id.preview);
        findViewById(R.id.graphicOverlay);
    }
}
