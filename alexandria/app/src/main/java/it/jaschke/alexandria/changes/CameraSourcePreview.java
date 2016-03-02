package it.jaschke.alexandria.changes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author tham
 *
 * Preview class for camera source
 */
public class CameraSourcePreview extends ViewGroup {
    private Context context_;

    public CameraSourcePreview(Context context, AttributeSet attrs) {
        super(context, attrs);

        context_ = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
