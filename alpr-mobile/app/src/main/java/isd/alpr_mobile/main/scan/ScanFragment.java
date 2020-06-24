package isd.alpr_mobile.main.scan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import java.util.Objects;

import isd.alpr_mobile.R;

public class ScanFragment extends Fragment implements SurfaceHolder.Callback {
    private OnScanFragmentInteractionListener mListener;
    private SurfaceView cameraView;
    private CameraSource cameraSource;
    // todo: add an queue of items to process to have the ability to discard all of them when an valid plate number found


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
            textRecognizer.setProcessor(new OCRProcessor());
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
}

