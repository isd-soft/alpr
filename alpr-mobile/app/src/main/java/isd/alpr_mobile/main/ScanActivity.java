package isd.alpr_mobile.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import isd.alpr_mobile.R;
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

public class ScanActivity extends AppCompatActivity implements SurfaceHolder.Callback, OnPlateFoundListener {

    private SurfaceView cameraView;
    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        cameraView = findViewById(R.id.surface);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startSourceCamera();
    }

    private void startSourceCamera() {
        TextRecognizer textRecognizer = new TextRecognizer
                .Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w("ScanFragment", "Detector dependencies not loaded yet");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(30.0f)
                    .build();

            cameraView.getHolder().addCallback(this);
            textRecognizer.setProcessor(new OCRProcessor(this));
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        Objects.requireNonNull(this),
                        new String[]{Manifest.permission.CAMERA},
                        RequestCode.CAMERA_REQUEST_PERMISSION_ID.ordinal());
                // todo: continue if permission granted, else replace fragment to WriteFragment
                return;
            }
            cameraSource.start(cameraView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
    }

    private void showSnackBack(String message) {

        Snackbar.make(findViewById(R.id.surface), message, Snackbar.LENGTH_LONG)
                .setAction("OK", v -> { })
                .show();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPlateFound(List<LicensePlate> plates) {
        Call<MailResponse> call = APIClient.getClient().create(APIInterface.class).sendEmailNotificationByLicensePlate(plates.get(4));
        call.enqueue(new Callback<MailResponse>() {
            @Override
            public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                if(response.body() != null)
                    showSnackBack(response.body().response);
                else
                    showSnackBack("Email was not sent! Try again");
                finish();
            }

            @Override
            public void onFailure(Call<MailResponse> call, Throwable t) {
                showSnackBack("Error sending message!");
                finish();
            }
        });
    }
}