package isd.alpr_mobile.main.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Objects;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.activity.ResultActivity;
import isd.alpr_mobile.main.model.LicensePlateRequest;
import isd.alpr_mobile.main.model.ValidateCarDTO;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.ApiService;
import isd.alpr_mobile.main.utility.OCRProcessor;
import isd.alpr_mobile.main.utility.RequestCode;
import isd.alpr_mobile.main.utility.HttpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanFragment extends Fragment implements SurfaceHolder.Callback {
    private SurfaceView cameraView;
    private CameraSource cameraSource;
    private final ApiService apiService;

    public ScanFragment() {
        apiService = APIClient.getClient().create(ApiService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        cameraView = view.findViewById(R.id.surfaceView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startSourceCamera();
        view.findViewById(R.id.scan_ready_button).setOnClickListener(v -> {
            String text = ((TextView)view.findViewById(R.id.license_plate_text_view)).getText().toString();
            apiService.getCarByLicensePlate(new LicensePlateRequest(text)).enqueue(new Callback<HttpResponse<ValidateCarDTO>>() {
                @Override
                public void onResponse(@NonNull Call<HttpResponse<ValidateCarDTO>> call, @NonNull Response<HttpResponse<ValidateCarDTO>> response) {
                    changeActivity(Objects.requireNonNull(response.body()).data);
                }

                @Override
                public void onFailure(@NonNull Call<HttpResponse<ValidateCarDTO>> call, @NonNull Throwable t) {
                    showToast();
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
            textRecognizer.setProcessor(new OCRProcessor(requireActivity().findViewById(R.id.license_plate_text_view)));
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        Objects.requireNonNull(getActivity()),
                        new String[]{Manifest.permission.CAMERA},
                        RequestCode.CAMERA_REQUEST_PERMISSION_ID.ordinal());
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

    private Context getApplicationContext() {
        return Objects.requireNonNull(getActivity()).getApplicationContext();
    }

    private void changeActivity(ValidateCarDTO argument) {
        Intent intent = new Intent(requireActivity(), ResultActivity.class);
        intent.putExtra("argument", argument);
        startActivity(intent);
    }

    private void showToast() {
        Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
    }
}

