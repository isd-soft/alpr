package isd.alpr_mobile.main.notify;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.model.Car;
import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicenseValidationResponse;
import isd.alpr_mobile.main.model.Mail;
import isd.alpr_mobile.main.model.MailResponse;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.APIInterface;
import isd.alpr_mobile.main.scan.OCRProcessor;
import isd.alpr_mobile.main.scan.RequestCode;
import isd.alpr_mobile.main.scan.license_plate_validation.OnPlateFoundListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyFragment extends Fragment implements SurfaceHolder.Callback, OnPlateFoundListener {

    private TableLayout tableLayout;
    private List<Car> cars;

    public NotifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notify, container, false);
        tableLayout = (TableLayout) view.findViewById(R.id.table);
        Call<List<Car>> call = APIClient.getClient().create(APIInterface.class).getAllInCars();
        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                cars = response.body();
                Log.i("ocr-cars", cars.toString());
                for(Car car : cars) {
                    TableRow row = new TableRow(getContext());
                    TextView textView = new TextView(getContext());
                    textView.setText(car.licensePlate);

                    row.addView(textView);
                    Button button = new Button(getContext());
                    button.setText(">");
                    button.setTag(car.ownerEmail);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("ocr-cars", v.getTag().toString());
                            Call<MailResponse> call2 = APIClient.getClient().create(APIInterface.class).sendEmailNotification(new Mail(v.getTag().toString()));
                            call2.enqueue(new Callback<MailResponse>() {
                                @Override
                                public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                                    Log.i("ocr-cars", response.body().response);
                                    Snackbar.make(view, response.body().response, Snackbar.LENGTH_LONG)
                                            .setAction("OK", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            })
                                            .show();
                                }

                                @Override
                                public void onFailure(Call<MailResponse> call, Throwable t) {
                                    Log.i("ocr-cars", "error " + t.toString());
                                }
                            });
                        }
                    });
                    row.addView(button);
                    tableLayout.addView(row);
                }
            }

            public void onClick(View view) {
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                //error
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPlateFound(List<LicensePlate> plates) { }
}