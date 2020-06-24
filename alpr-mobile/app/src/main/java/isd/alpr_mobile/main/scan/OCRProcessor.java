package isd.alpr_mobile.main.scan;

import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

public class OCRProcessor implements Detector.Processor<TextBlock> {
    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        final SparseArray<TextBlock> items = detections.getDetectedItems();
        if (items.size() >= 0) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }
                    System.out.println(stringBuilder.toString());
                    // todo: user OCRItemValidator instead of Runnable
                }
            };

            runnable.run();
        }
    }
}
