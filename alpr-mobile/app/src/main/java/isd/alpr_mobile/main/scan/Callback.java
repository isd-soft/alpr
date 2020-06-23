package isd.alpr_mobile.main.scan;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;

import java.io.IOException;
// todo: rename concrete or implement with ScanFragment
public class Callback implements SurfaceHolder.Callback {

    private CameraSource cameraSource;
    private SurfaceView cameraView;

    public Callback(CameraSource cameraSource, SurfaceView cameraView) {
        this.cameraSource = cameraSource;
        this.cameraView = cameraView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // todo: handle permission not granted case
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
}
