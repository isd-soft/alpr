package isd.alpr_mobile.main.utility;

import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OCRProcessor implements Detector.Processor<TextBlock> {

    private final TextView licensePlateTextView;

    public OCRProcessor(TextView textView) {
        this.licensePlateTextView = textView;
    }

    @Override
    public void release() {
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        StringBuilder stringBuilder = new StringBuilder();
        final SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); i++) {
            TextBlock item = items.valueAt(i);
            stringBuilder.append(item.getValue().trim());
        }
        String string = stringBuilder.toString();
        Pattern pattern = Pattern.compile("([A-Z]{3}\\s\\d{1,3}|[A-Z]{1,2}\\s[A-Z]{2}\\s\\d{1,3})");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            licensePlateTextView.setText(matcher.group(1));
        }
    }
}
