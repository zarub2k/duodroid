package it.jaschke.alexandria.changes;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import it.jaschke.alexandria.R;

/**
 * @author tham
 *
 * Activity to capture the Barcode using camera functionality.
 */
public class BarcodeCaptureActivity extends Activity {
    private static final String LOG_TAG = BarcodeCaptureActivity.class.getSimpleName();

    private static final int HANDLE_GMS = 9001;
    private static final int HANDLE_CAMERA_PERMISSION = 2;

    private static final String AUTOFOCUS = "AutoFocus";
    private static final String USEFLASH = "UseFlash";
    private static final String BARCODE_OBJECT = "Barcode";

    private CameraSource cameraSource;
    private CameraSourcePreview cameraSourcePreview;
    private GraphicOverlay<BarcodeGraphic> graphicOverlay;

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        startCameraPreview();

        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
    }

    private void startCameraPreview() {
        Log.i(LOG_TAG, "Enters startCameraPreview()");

        cameraSourcePreview = (CameraSourcePreview) findViewById(R.id.preview);
        graphicOverlay = (GraphicOverlay<BarcodeGraphic>) findViewById(R.id.graphicOverlay);

        final boolean hasAutoFocus = getIntent().getBooleanExtra(AUTOFOCUS, false);
        final boolean canUseFlash = getIntent().getBooleanExtra(USEFLASH, false);

        final int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(hasAutoFocus, canUseFlash);
        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        Log.w(LOG_TAG, "Camera permission is NOT granted; Requesting permission");
    }

    private void createCameraSource(boolean hasAutoFocus, boolean canUseFlash) {
        Log.i(LOG_TAG, "Camera permission is already granted; Creating camera source");
    }
}
