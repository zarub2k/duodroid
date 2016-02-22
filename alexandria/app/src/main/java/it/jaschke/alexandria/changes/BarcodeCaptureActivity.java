package it.jaschke.alexandria.changes;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.vision.barcode.BarcodeDetector;

/**
 * @author tham
 */
public class BarcodeCaptureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
    }
}
