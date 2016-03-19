package it.jaschke.alexandria.changes;

import android.util.Log;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * @author tham
 */
public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private static final String LOG_TAG = BarcodeTrackerFactory.class.getSimpleName();

    private GraphicOverlay<BarcodeGraphic> overlay;

    public BarcodeTrackerFactory(GraphicOverlay<BarcodeGraphic> overlay) {
        this.overlay = overlay;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        Log.i(LOG_TAG, "Enters create()");
        BarcodeGraphic graphic = new BarcodeGraphic(overlay);
        return new BarcodeGraphicTracker(overlay, graphic);
    }
}
