package it.jaschke.alexandria.changes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * @author tham
 *
 * Graphic generated from scan
 */
public class BarcodeGraphic extends GraphicOverlay.Graphic {
    private static final String LOG_TAG = BarcodeGraphic.class.getSimpleName();

    //Indicates the possible colors
    private static final int[] colors = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN
    };

    private int id;

    private static int currentColorIndex = 0;

    private Paint rectPaint;
    private Paint textPaint;

    private volatile Barcode barcode_;

    public BarcodeGraphic(GraphicOverlay overlay) {
        super(overlay);

        currentColorIndex = (currentColorIndex + 1) % colors.length;
        final int selectedColor = colors[currentColorIndex];

        rectPaint = new Paint();
        rectPaint.setColor(selectedColor);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(4.0f);

        textPaint = new Paint();
        textPaint.setColor(selectedColor);
        textPaint.setTextSize(36.0f);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Barcode getBarcode() {
        return barcode_;
    }

    void updateItem(Barcode barcode) {
        barcode_ = barcode;
        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        Barcode barcode = barcode_;
        if (barcode == null) {
            return;
        }

        final RectF rect = new RectF(barcode.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);

        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);

        canvas.drawRect(rect, rectPaint);
        canvas.drawText(barcode.rawValue, rect.left, rect.bottom, textPaint);
    }
}
