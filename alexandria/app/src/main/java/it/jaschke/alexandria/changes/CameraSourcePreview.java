package it.jaschke.alexandria.changes;

import android.Manifest;
import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

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

    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);

        context_ = context;
        surfaceView_ = new SurfaceView(context);
        surfaceView_.getHolder().addCallback(new SurfaceCallback());
        addView(surfaceView_);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void start(CameraSource cameraSource) throws IOException, SecurityException {
        if (cameraSource == null) {
            stop();
        }

        cameraSource_ = cameraSource;
    }

    public void stop() {

    }

    private class SurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
