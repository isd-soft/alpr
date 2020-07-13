package isd.alpr_mobile.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.lifecycle.Observer;
import isd.alpr_mobile.R;
import isd.alpr_mobile.main.notify.NotifyFragment;
import isd.alpr_mobile.main.scan.OnScanFragmentInteractionListener;
import isd.alpr_mobile.main.scan.ScanFragment;
import isd.alpr_mobile.main.utility.ConnectionLiveData;
import isd.alpr_mobile.main.utility.ConnectionModel;
import isd.alpr_mobile.main.utility.InternetProblemsDialogFragment;
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


        DialogFragment noInternetDialogFragment = new InternetProblemsDialogFragment();
        getSupportFragmentManager().beginTransaction()
                .add(noInternetDialogFragment, "INTERNET_CONNECTION");
        noInternetDialogFragment.setCancelable(false);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, connection -> {

            if (connection.getIsConnected()) {
                switch (connection.getType()) {
                    case 1:
                        noInternetDialogFragment.dismiss();
                        Toast.makeText(MainActivity.this, "Wifi turned ON", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        noInternetDialogFragment.dismiss();
                        Toast.makeText(MainActivity.this, "Mobile data turned ON", Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {

                noInternetDialogFragment.show(getSupportFragmentManager(),"INTERNET_CONNECTION");
                Toast.makeText(MainActivity.this, "Connections turned OFF", Toast.LENGTH_SHORT).show();
            }
        });

        frameLayout = findViewById(R.id.frames);
        nav = findViewById(R.id.nav);
        nav.setOnNavigationItemSelectedListener(item -> replaceFrameByItem(item));

        scanFragment = new ScanFragment();
        writeFragment = new WriteFragment();
        notifyFragment = new NotifyFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.nav.getSelectedItemId() == R.id.scan_plate_action) {
            replaceFrame(scanFragment);
        } else if(this.nav.getSelectedItemId() == R.id.write_plate_action) {
            replaceFrame(writeFragment);
        } else {
            replaceFrame(notifyFragment);
        }
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
    public void onValidPlate(String licensePlate) { }



}
