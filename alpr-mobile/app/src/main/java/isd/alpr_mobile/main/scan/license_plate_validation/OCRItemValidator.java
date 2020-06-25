package isd.alpr_mobile.main.scan.license_plate_validation;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.text.TextBlock;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OCRItemValidator implements Runnable {

    private OnPlateFoundListener listener;
    private SparseArray<TextBlock> items;

    public OCRItemValidator(SparseArray<TextBlock> items,
                            OnPlateFoundListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            TextBlock item = items.valueAt(i);
            stringBuilder.append(item.getValue().trim());
        }
        String string = stringBuilder.toString();
        Log.i("ocr-text", string);

        Pattern pattern = Pattern.compile("([A-Z]{3}\\s\\d{1,3}|[A-Z]{1,2}\\s[A-Z]{2}\\s\\d{1,3})");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            Log.i("ocr-plate", Objects.requireNonNull(matcher.group(1)));
            listener.onPlateFound(matcher.group(1));
        }
    }
}
