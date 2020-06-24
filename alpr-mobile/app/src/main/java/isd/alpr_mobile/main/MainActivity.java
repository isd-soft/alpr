package isd.alpr_mobile.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.scan.OnScanFragmentInteractionListener;
import isd.alpr_mobile.main.scan.ScanFragment;
import isd.alpr_mobile.main.write.OnWriteFragmentInteractionListener;
import isd.alpr_mobile.main.write.WriteFragment;

public class MainActivity extends AppCompatActivity
        implements OnScanFragmentInteractionListener,
        OnWriteFragmentInteractionListener {

    private FrameLayout frameLayout;
    private BottomNavigationView nav;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        replaceFrame(new ScanFragment());
    }

    private boolean replaceFrameByItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan_plate_action:
                replaceFrame(new ScanFragment());
                break;
            case R.id.write_plate_action:
                replaceFrame(new WriteFragment());
                break;
        }
        // todo: get stored fragment instead of creating new
        return true;
    }

    private void replaceFrame(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment)
//                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWriteFragmentInteraction() {

    }

    @Override
    public void onValidPlate(String licensePlate) {

    }
}
