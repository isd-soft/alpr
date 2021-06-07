package isd.alpr_mobile.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.MainActivity;
import isd.alpr_mobile.main.model.Car;
import isd.alpr_mobile.main.model.ValidateCarDTO;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {

    private final ApiService apiService = APIClient.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViewById(R.id.back_button).setOnClickListener(v -> changeActivity());
        ValidateCarDTO dto = (ValidateCarDTO) getIntent().getSerializableExtra("argument");
        if (Objects.requireNonNull(dto).car != null) {
            if (dto.response.equals("ALLOWED")) {
                ((ImageView) findViewById(R.id.result_image)).setImageResource(R.drawable.ic_allow);
                ((MaterialTextView) findViewById(R.id.result_status)).setText(R.string.status_allow);
                ((MaterialTextView) findViewById(R.id.license_plate)).setText(dto.car.licensePlate);
                ((MaterialTextView) findViewById(R.id.brand)).setText(dto.car.brand + " " + dto.car.model);
                MaterialButton yesButton = findViewById(R.id.button_yes);
                MaterialButton noButton = findViewById(R.id.button_no);
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
                yesButton.setEnabled(true);
                noButton.setEnabled(true);
                ((MaterialTextView) findViewById(R.id.owner)).setText(dto.car.owner);
                if (dto.car.status.equals("OUT")) {
                    ((MaterialTextView) findViewById(R.id.question)).setText(R.string.question_enter);
                    findViewById(R.id.button_yes).setOnClickListener(v -> apiService.changeCarStatus(dto.car.id).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            showToast("Car status is now IN");
                            changeActivity();
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            showToast("Something went wrong");
                            changeActivity();
                        }
                    }));
                } else {
                    ((MaterialTextView) findViewById(R.id.question)).setText(R.string.question_leave);
                    findViewById(R.id.button_yes).setOnClickListener(v -> apiService.changeCarStatus(dto.car.id).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            showToast("Car status is now OUT");
                            changeActivity();
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            showToast("Something went wrong");
                            changeActivity();
                        }
                    }));
                }
                findViewById(R.id.button_no).setOnClickListener(v -> changeActivity());
            } else {
                ((ImageView)findViewById(R.id.result_image)).setImageResource(R.drawable.ic_forbid);
                ((MaterialTextView)findViewById(R.id.result_status)).setText(dto.response);
                ((MaterialTextView) findViewById(R.id.license_plate)).setText(dto.car.licensePlate);
                ((MaterialTextView) findViewById(R.id.brand)).setText(dto.car.brand + " " + dto.car.model);
            }
        } else {
            ((ImageView)findViewById(R.id.result_image)).setImageResource(R.drawable.ic_unkown);
            ((MaterialTextView)findViewById(R.id.result_status)).setText(dto.response);
        }
    }

    private void changeActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}