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

    private GraphicOverlay graphicOverlay_;

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

        int childWidth = layoutWidth;
        int childHeight = (int)(((float) layoutWidth / (float) width) * height);

        if (childHeight > layoutHeight) {
            childHeight = layoutHeight;
            childWidth = (int)(((float) layoutHeight / (float) height) * width);
        }

        for (int i = 0; i < getChildCount(); ++i) {
            getChildAt(i).layout(0, 0, childWidth, childHeight);
        }

        try {
            startIfReady();
        } catch (SecurityException se) {
            Log.e(LOG_TAG,"Do not have permission to start the camera", se);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not start camera source.", e);
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void start(CameraSource cameraSource, GraphicOverlay overlay) throws IOException, SecurityException {
        if (cameraSource == null) {
            stop();
        }

        cameraSource_ = cameraSource;
        graphicOverlay_ = overlay;

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
            cameraSource_.start(surfaceView_.getHolder());
            if (graphicOverlay_ != null) {
                final Size previewSize = cameraSource_.getPreviewSize();
                int min = Math.min(previewSize.getWidth(), previewSize.getHeight());
                int max = Math.max(previewSize.getWidth(), previewSize.getHeight());

                if (isPortraitMode()) {
                    graphicOverlay_.setCameraInfo(min, max, cameraSource_.getCameraFacing());
                } else {
                    graphicOverlay_.setCameraInfo(max, min, cameraSource_.getCameraFacing());
                }

                graphicOverlay_.clear();
            }

            isStartRequested = false;
        }
    }

    private boolean isPortraitMode() {
        final int orientation = context_.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }

        Log.i(LOG_TAG, "isPortrait mode returns false by default");
        return false;
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            isSurfaceAvailable = true;

            try {
                startIfReady();
            } catch (SecurityException se) {
                Log.e(LOG_TAG,"Do not have permission to start the camera", se);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Could not start camera source.", e);
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