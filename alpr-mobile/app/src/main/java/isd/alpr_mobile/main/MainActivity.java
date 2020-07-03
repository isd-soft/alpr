package isd.alpr_mobile.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.notify.NotifyFragment;
import isd.alpr_mobile.main.scan.OnScanFragmentInteractionListener;
import isd.alpr_mobile.main.scan.ScanFragment;
import isd.alpr_mobile.main.write.OnWriteFragmentInteractionListener;
import isd.alpr_mobile.main.write.WriteFragment;

public class MainActivity extends AppCompatActivity
        implements OnScanFragmentInteractionListener,
        OnWriteFragmentInteractionListener {

    private FrameLayout frameLayout;
    private BottomNavigationView nav;
    private ScanFragment scanFragment;
    private WriteFragment writeFragment;
    private NotifyFragment notifyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frames);
        nav = findViewById(R.id.nav);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return replaceFrameByItem(item);
            }
        });

        scanFragment = new ScanFragment();
        writeFragment = new WriteFragment();
        notifyFragment = new NotifyFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        replaceFrame(scanFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        replaceFrame(scanFragment);
    }

    private boolean replaceFrameByItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan_plate_action:
                replaceFrame(scanFragment);
                break;
            case R.id.write_plate_action:
                replaceFrame(writeFragment);
                break;
            case R.id.notify_action:
                replaceFrame(notifyFragment);
                break;
        }
        // todo: get stored fragment in getSupportFragmentManager() instead of creating new
        return true;
    }

    private void replaceFrame(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(frameLayout.getId(), fragment, fragment.getTag())
                .commitNow();
    }

    @Override
    public void onWriteFragmentInteraction() {

    }

    @Override
    public void onValidPlate(String licensePlate) {
        // todo: handle API server response (change to WriteFragment, fill edit text with valid number, show given data)
    }
}
