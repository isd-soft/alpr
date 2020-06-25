package isd.alpr_mobile.main.scan;

import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import isd.alpr_mobile.main.scan.license_plate_validation.OCRItemValidator;
import isd.alpr_mobile.main.scan.license_plate_validation.OnPlateFoundListener;

public class OCRProcessor implements Detector.Processor<TextBlock> {

    private OnPlateFoundListener listener;

    public OCRProcessor(OnPlateFoundListener listener) {
        this.listener = listener;
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        final SparseArray<TextBlock> items = detections.getDetectedItems();
        if (items.size() >= 0) {
            OCRItemValidator ocrItemValidator = new OCRItemValidator(items, listener);
            ocrItemValidator.run();
        }
    }
}
