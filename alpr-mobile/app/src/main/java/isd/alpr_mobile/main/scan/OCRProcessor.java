package isd.alpr_mobile.main.scan;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.APIInterface;
import isd.alpr_mobile.main.scan.license_plate_validation.OnPlateFoundListener;
import retrofit2.Retrofit;

public class OCRProcessor implements Detector.Processor<TextBlock> {

    private OnPlateFoundListener listener;
    private Retrofit retrofit;
    private APIInterface api;
    public List<LicensePlate> foundPlates;

    public OCRProcessor(OnPlateFoundListener listener) {
        this.listener = listener;
        this.retrofit = APIClient.getClient();
        this.api = retrofit.create(APIInterface.class);
        this.foundPlates = new ArrayList<>();

    }

    @Override
    public void release() {
        foundPlates.clear();
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        StringBuilder stringBuilder = new StringBuilder();
        if (foundPlates.size() == 5) {
            List<LicensePlate> plates = new ArrayList<>(foundPlates);
            listener.onPlateFound(plates);
            foundPlates.clear();
        } else {
            final SparseArray<TextBlock> items = detections.getDetectedItems();
            for (int i = 0; i < items.size(); i++) {
                TextBlock item = items.valueAt(i);
                stringBuilder.append(item.getValue().trim());
            }
            String string = stringBuilder.toString();
            Pattern pattern = Pattern.compile("([A-Z]{3}\\s\\d{1,3}|[A-Z]{1,2}\\s[A-Z]{2}\\s\\d{1,3})");
            Matcher matcher = pattern.matcher(string);
            if (matcher.find()) {
                foundPlates.add(new LicensePlate(matcher.group(1)));
            }
        }

    }
}
