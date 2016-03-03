package it.jaschke.alexandria.changes;

import android.Manifest;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;

import java.io.IOException;

/**
 * @author tham
 *
 * Preview class for camera source
 */
public class CameraSourcePreview extends ViewGroup {
    private static final String LOG_TAG = CameraSourcePreview.class.getSimpleName();

    private Context context_;
    private SurfaceView surfaceView_;
    private CameraSource cameraSource_;

    private boolean isStartRequested;
    private boolean isSurfaceAvailable;

    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);

        context_ = context;

        isStartRequested = false;
        isSurfaceAvailable = false;

        surfaceView_ = new SurfaceView(context);
        surfaceView_.getHolder().addCallback(new SurfaceCallback());
        addView(surfaceView_);
    }

    @Override
    protected void onLayout(boolean changed,
                            int left, int top, int right, int bottom) {
        int width = 320;
        int height = 240;

        if (cameraSource_ != null) {
            final Size previewSize = cameraSource_.getPreviewSize();
            if (previewSize != null) {
                width = previewSize.getWidth();
                height = previewSize.getHeight();
            }
        }

        if (isPortraitMode()) {
            int temp = width;
            width = height;
            height = temp;
        }

        final int layoutWidth = right - left;
        final int layoutHeight = bottom - top;
    }

    private boolean isPortraitMode() {
        final int orientation = context_.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }

        Log.i(LOG_TAG, "isPortraitmode returns false by default");
        return false;
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void start(CameraSource cameraSource) throws IOException, SecurityException {
        if (cameraSource == null) {
            stop();
        }

        cameraSource_ = cameraSource;

        if (cameraSource_ != null) {
            isStartRequested = true;
            startIfReady();
        }
    }

    public void stop() {
        if (cameraSource_ != null) {
            cameraSource_.stop();
        }
    }

    public void release() {
        if (cameraSource_ != null) {
            cameraSource_.release();
            cameraSource_ = null;
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    private void startIfReady() throws IOException, SecurityException {
        if (isStartRequested && isSurfaceAvailable) {

        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            isSurfaceAvailable = true;

            try {
                startIfReady();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Could not start camera source", e);
            } catch (SecurityException e) {
                Log.e(LOG_TAG, "Do not have permission start camera", e);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            isSurfaceAvailable = false;
        }
    }
}
