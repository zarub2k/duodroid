package it.jaschke.alexandria.changes;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.vision.CameraSource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tham
 */
public class GraphicOverlay<T extends GraphicOverlay.Graphic> extends View {
    private final Object lock_ = new Object();
    private int previewWidth_;
    private float widthScaleFactor_ = 1.0f;

    private int previewHeight_;
    private float heightScaleFactor_ = 1.0f;

    private int cameraFacing_ = CameraSource.CAMERA_FACING_BACK;
    private Set<T> graphics_ = new HashSet<>();
    private T firstGraphic_;



    public GraphicOverlay(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public static abstract class Graphic {
        private GraphicOverlay overlay_;

        public Graphic(GraphicOverlay overlay) {
            overlay_ = overlay;
        }

        public abstract void draw(Canvas canvas);

        public float scaleX(float horizontal) {
            return horizontal * overlay_.widthScaleFactor_;
        }

        public float scaleY(float vertical) {
            return vertical * overlay_.heightScaleFactor_;
        }

        public float translateX(float x) {
            if (overlay_.cameraFacing_ == CameraSource.CAMERA_FACING_FRONT) {
                return overlay_.getWidth() - scaleX(x);
            }

            return scaleX(x);
        }

        public float translateY(float y) {
            return scaleY(y);
        }

        public void postInvalidate() {
            overlay_.postInvalidate();
        }
    }

    public void clear() {
        synchronized (lock_) {
            graphics_.clear();
            firstGraphic_ = null;
        }

        postInvalidate();
    }

    public void add(T graphic) {
        synchronized (lock_) {
            graphics_.add(graphic);

            if (firstGraphic_ == null) {
                firstGraphic_ = graphic;
            }
        }

        postInvalidate();
    }

    public void remove(T graphic) {
        synchronized (lock_) {
            graphics_.remove(graphic);

            if (firstGraphic_ != null && firstGraphic_.equals(graphic)) {
                firstGraphic_ = null;
            }
        }

        postInvalidate();
    }
}
