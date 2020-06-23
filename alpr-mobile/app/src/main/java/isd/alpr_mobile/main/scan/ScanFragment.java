package isd.alpr_mobile.main.scan;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.Objects;

import isd.alpr_mobile.R;

public class ScanFragment extends Fragment {

    private final int REQUEST_PERMISSION_ID = 1000;
    private OnScanFragmentInteractionListener mListener;
    private SurfaceView cameraView;
    private CameraSource cameraSource;

    public ScanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        cameraView = view.findViewById(R.id.surfaceView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startSourceCamera();
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onScanFragmentInteraction();
        }
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

    @Override
    public void onStart() {
        super.onStart();

        //startSourceCamera();
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

            cameraView.getHolder().addCallback(new Callback(cameraSource, cameraView));

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
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
                            }
                        };

                        runnable.run();
                    }
                }
            });
        }
    }

    private Context getApplicationContext() {
        return Objects.requireNonNull(getActivity()).getApplicationContext();
    }
}

