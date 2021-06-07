package isd.alpr_mobile.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.fragment.ListFragment;
import isd.alpr_mobile.main.fragment.ScanFragment;
import isd.alpr_mobile.main.utility.ConnectionLiveData;
import isd.alpr_mobile.main.utility.InternetProblemsDialogFragment;
import isd.alpr_mobile.main.fragment.WriteFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView nav;
    private ScanFragment scanFragment;
    private WriteFragment writeFragment;
    private ListFragment listFragment;

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
                noInternetDialogFragment.show(getSupportFragmentManager(), "INTERNET_CONNECTION");
                Toast.makeText(MainActivity.this, "Connections turned OFF", Toast.LENGTH_SHORT).show();
            }
        });

        frameLayout = findViewById(R.id.fragment_container);
        nav = findViewById(R.id.navigation);
        nav.setOnNavigationItemSelectedListener(this::replaceFrameByItem);

        scanFragment = new ScanFragment();
        writeFragment = new WriteFragment();
        listFragment = new ListFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.nav.getSelectedItemId() == R.id.navigate_scan) {
            replaceFrame(scanFragment);
        } else if (this.nav.getSelectedItemId() == R.id.navigate_write) {
            replaceFrame(writeFragment);
        } else {
            replaceFrame(listFragment);
        }
    }

    private boolean replaceFrameByItem(MenuItem item) {
        if (item.getItemId() == R.id.navigate_scan) {
            replaceFrame(scanFragment);
        } else if (item.getItemId() == R.id.navigate_write) {
            replaceFrame(writeFragment);
        } else {
            replaceFrame(listFragment);
        }
        return true;
    }

    private void replaceFrame(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(frameLayout.getId(), fragment, fragment.getTag())
                .commitNow();
    }

}
