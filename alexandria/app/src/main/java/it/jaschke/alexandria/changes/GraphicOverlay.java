package it.jaschke.alexandria.changes;

import android.content.Context;
import android.graphics.Camera;
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
    private final Object object = new Object();
    private int previewWidth_;
    private float widthScaleFactor_ = 1.0f;

    private int previewHeight_;
    private float heightScaleFactor = 1.0f;

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
            return horizontal * overlay_.mWi
        }
    }
}
