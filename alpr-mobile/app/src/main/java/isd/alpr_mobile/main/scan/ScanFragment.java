package isd.alpr_mobile.main.scan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import isd.alpr_mobile.DisplayMessageActivity;
import isd.alpr_mobile.R;
import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicenseValidationResponse;
import isd.alpr_mobile.main.scan.license_plate_validation.OnPlateFoundListener;

public class ScanFragment extends Fragment implements SurfaceHolder.Callback, OnPlateFoundListener {
    private OnScanFragmentInteractionListener mListener;
    private SurfaceView cameraView;
    private CameraSource cameraSource;

    public ScanFragment() {
        // Required empty public constructor
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
    }

    @Override
    public void onResume() {
        super.onResume();
        startSourceCamera();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnScanFragmentInteractionListener) {
            mListener = (OnScanFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnScanFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                        Objects.requireNonNull(getActivity()),
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

    private Context getApplicationContext() {
        return Objects.requireNonNull(getActivity()).getApplicationContext();
    }

    @Override
    public void onPlateFound(List<LicensePlate> plates) {
        Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
        intent.putExtra("plates", plates.toArray());
        startActivity(intent);
    }
}

