package isd.alpr_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import isd.alpr_mobile.main.model.LicenseValidationResponse;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        LicenseValidationResponse response = (LicenseValidationResponse) getIntent().getSerializableExtra("response");
        if(response.getStatus().equals("Forbidden")) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.ic_baseline_clear_24);
        }
        else{
            TextView textView = (TextView)findViewById(R.id.name);
            textView.setText(response.getCar().ownerName);
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}