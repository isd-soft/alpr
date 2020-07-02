package isd.alpr_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicenseValidationResponse;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayMessageActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<LicensePlate> plates = new ArrayList<>();
        Object[] platesArray = (Object[]) getIntent().getSerializableExtra("plates");
        for (Object o : platesArray) {
            plates.add((LicensePlate) o);
        }

        Call<LicenseValidationResponse> call = APIClient.getClient().create(APIInterface.class).validateLicensePlate(plates);
        call.enqueue(new Callback<LicenseValidationResponse>() {
            @Override
            public void onResponse(Call<LicenseValidationResponse> call, Response<LicenseValidationResponse> response) {
                LicenseValidationResponse validationResponse = response.body();
                Log.i("response", validationResponse.toString());
                if(validationResponse.getStatus().equals("Forbidden")) {
                    ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.ic_forbidden);
                    ((TextView)findViewById(R.id.status)).setText("Forbidden");
                    ((TextView)findViewById(R.id.status)).setTextColor(getResources().getColor(R.color.colorAccent));
                    ((TextView)findViewById(R.id.license_plate)).setText(validationResponse.getCar().licensePlate);
                    ((TextView)findViewById(R.id.car)).setText("");
                    ((TextView)findViewById(R.id.user)).setText("");
                    ((TextView)findViewById(R.id.company)).setText("");
                }
                else if(validationResponse.getStatus().equals("Allowed")){
                    ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.ic_allowed);
                    ((TextView)findViewById(R.id.status)).setText("Allowed");
                    ((TextView)findViewById(R.id.status)).setTextColor(getResources().getColor(R.color.colorPrimary));
                    ((TextView)findViewById(R.id.license_plate)).setText(validationResponse.getCar().licensePlate);
                    ((TextView)findViewById(R.id.car)).setText(validationResponse.getCar().brand + " " + validationResponse.getCar().model);
                    ((TextView)findViewById(R.id.user)).setText(validationResponse.getCar().ownerName);
                    ((TextView)findViewById(R.id.company)).setText(validationResponse.getCar().ownerCompany);
                } else if(validationResponse.getStatus().equals("Unknown")){
                    ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.ic_unkown);
                    ((TextView)findViewById(R.id.status)).setText("Unknown");
                    ((TextView)findViewById(R.id.status)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    ((TextView)findViewById(R.id.license_plate)).setText(validationResponse.getCar().licensePlate);
                } else {
                    ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.ic_goodbye);
                    ((TextView)findViewById(R.id.status)).setText("Good Bye");
                    ((TextView)findViewById(R.id.status)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    ((TextView)findViewById(R.id.license_plate)).setText(validationResponse.getCar().licensePlate);
                }
            }

            @Override
            public void onFailure(Call<LicenseValidationResponse> call, Throwable t) {
                Log.i("ocr-plate", "Error " + t.toString());
                ((TextView)findViewById(R.id.status)).setText("Oops");
            }
        });
    }
}