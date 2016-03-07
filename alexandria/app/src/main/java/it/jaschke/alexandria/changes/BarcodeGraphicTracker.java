package it.jaschke.alexandria.changes;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * @author tham
 */
public class BarcodeGraphicTracker extends Tracker<Barcode> {
    private static final String LOG_TAG = BarcodeGraphicTracker.class.getSimpleName();

    private GraphicOverlay<BarcodeGraphic> overlay;
    private BarcodeGraphic graphic;

    public BarcodeGraphicTracker(GraphicOverlay<BarcodeGraphic> overlay, BarcodeGraphic graphic) {
        this.overlay = overlay;
        this.graphic = graphic;
    }

    @Override
    public void onNewItem(int id, Barcode barcode) {
        graphic.setId(id);
    }

    @Override
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode item) {
        overlay.add(graphic);
        graphic.updateItem(item);
    }

    @Override
    public void onMissing(Detector.Detections<Barcode> detectionResults) {
        overlay.remove(graphic);
    }

    @Override
    public void onDone() {
        overlay.remove(graphic);
    }
}
