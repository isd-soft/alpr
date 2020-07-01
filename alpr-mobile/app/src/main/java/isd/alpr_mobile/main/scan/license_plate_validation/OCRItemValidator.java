package isd.alpr_mobile.main.scan.license_plate_validation;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.RequiresApi;

import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicenseValidationResponse;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OCRItemValidator implements Runnable {

    private OnPlateFoundListener listener;
    private SparseArray<TextBlock> items;
    private Retrofit retrofit;
    private APIInterface api;
    public static List<LicensePlate> plates = new ArrayList<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public OCRItemValidator(SparseArray<TextBlock> items,
                            OnPlateFoundListener listener) {
        this.items = items;
        this.listener = listener;
        this.retrofit = APIClient.getClient();
        this.api = retrofit.create(APIInterface.class);
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
        if (matcher.find() && plates.size() < 5) {
            readWriteLock.writeLock().lock();
            plates.add(new LicensePlate(matcher.group(1)));
            Log.i("ocr-plate", Objects.requireNonNull(matcher.group(1)));
            readWriteLock.writeLock().unlock();
        }
        if(plates.size() >= 5) {
            Call<LicenseValidationResponse> call = api.validateLicensePlate(plates);
            call.enqueue(new Callback<LicenseValidationResponse>() {
                @Override
                public void onResponse(Call<LicenseValidationResponse> call, Response<LicenseValidationResponse> response) {
                    LicenseValidationResponse validationResponse = response.body();
                    listener.onPlateFound(validationResponse);
                }

                @Override
                public void onFailure(Call<LicenseValidationResponse> call, Throwable t) {
                    Log.i("ocr-plate", "Error " + t.toString());
                }
            });
        }
    }
}
