package it.jaschke.alexandria.changes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

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
    public static final String BARCODE_OBJECT = "Barcode";

    private CameraSource cameraSource;
    private CameraSourcePreview cameraSourcePreview;
    private GraphicOverlay<BarcodeGraphic> graphicOverlay;

    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        startCameraPreview(); //Starting the camera view

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

    private void startCameraPreview() {
        Log.i(LOG_TAG, "Enters startCameraPreview()");

        cameraSourcePreview = (CameraSourcePreview) findViewById(R.id.preview);
        graphicOverlay = (GraphicOverlay<BarcodeGraphic>) findViewById(R.id.graphicOverlay);

        final boolean hasAutoFocus = getIntent().getBooleanExtra(AUTOFOCUS, false);
        final boolean canUseFlash = getIntent().getBooleanExtra(USEFLASH, false);

        final int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        Log.i(LOG_TAG, "Camera permission: " + cameraPermission);

        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(hasAutoFocus, canUseFlash);
        } else {
            requestCameraPermission();
        }
    }

    /**
     * Method to request camera permission
     */
    private void requestCameraPermission() {
        Log.w(LOG_TAG, "Camera permission is NOT granted; Requesting permission");

        final String[] permissions = new String[] {Manifest.permission.CAMERA};
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, HANDLE_CAMERA_PERMISSION);
            return;
        }

        final Activity current = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(current, permissions, HANDLE_CAMERA_PERMISSION);
            }
        };

        Snackbar.make(graphicOverlay, "Requesting camera",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok", listener)
                .show();
    }

    /**
     * Method to create camera source based on the given values of auto focus and flash
     *
     * @param hasAutoFocus
     * @param canUseFlash
     */
    private void createCameraSource(boolean hasAutoFocus, boolean canUseFlash) {
        Log.i(LOG_TAG, "Camera permission is already granted; Creating camera source");

        final Context context = getApplicationContext();
        final BarcodeTrackerFactory trackerFactory = new BarcodeTrackerFactory(graphicOverlay);
        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(trackerFactory).build());

        if (!barcodeDetector.isOperational()) {
            Log.w(LOG_TAG, "Detector dependencies are NOT yet available!");
        }

        final IntentFilter intentFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
        boolean hasLowStorage = registerReceiver(null, intentFilter) != null;
        Log.i(LOG_TAG, "Has low storage: " + hasLowStorage);

        if (hasLowStorage) {
            Toast.makeText(this, "Low storage", Toast.LENGTH_LONG).show();
        }

        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

//        cameraSource = builder
//                .setAutoFocusEnabled(hasAutoFocus)
//                .build();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            builder = builder.setFocusMode(
//                    canUseFlash ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
//        }
//
        cameraSource = builder
                .setFlashMode(canUseFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (cameraSourcePreview != null) {
            cameraSourcePreview.stop();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean b = scaleGestureDetector.onTouchEvent(e);

        boolean c = gestureDetector.onTouchEvent(e);

        return b || c || super.onTouchEvent(e);
    }

    /**
     * Method to start a camera
     * @throws SecurityException
     */
    private void startCameraSource() throws SecurityException {
        final int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (code != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance()
                    .getErrorDialog(this, code, HANDLE_GMS)
                    .show();
        }

        if (cameraSource != null) {
            try {
                cameraSourcePreview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Unable to start camera source", e);

                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    private boolean onTap(float rawX, float rawY) {
        BarcodeGraphic graphic = graphicOverlay.getFirstGraphic();
        Barcode barcode = null;
        if (graphic != null) {
            barcode = graphic.getBarcode();
            if (barcode != null) {
                Intent data = new Intent();
                data.putExtra(BARCODE_OBJECT, barcode);
                setResult(CommonStatusCodes.SUCCESS, data);
                finish();
            }
            else {
                Log.d(LOG_TAG, "barcode data is null");
            }
        }
        else {
            Log.d(LOG_TAG,"no barcode detected");
        }
        return barcode != null;
    }

    /**
     * Listener to handle on touch event
     */
    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    /**
     * Listener to handle the scale event
     */
    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            cameraSource.doZoom(detector.getScaleFactor());
        }
    }
}
