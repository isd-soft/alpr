package isd.alpr_mobile.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.activity.ResultActivity;
import isd.alpr_mobile.main.model.LicensePlateRequest;
import isd.alpr_mobile.main.model.ValidateCarDTO;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.ApiService;
import isd.alpr_mobile.main.utility.HttpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WriteFragment extends Fragment {
   private final ApiService apiService;

    public WriteFragment() {
        apiService = APIClient.getClient().create(ApiService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_write, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.input_button).setOnClickListener(v -> {
            String text = Objects.requireNonNull(((TextInputEditText) view.findViewById(R.id.license_plate_input)).getText()).toString().toUpperCase();
            apiService.getCarByLicensePlate(new LicensePlateRequest(text)).enqueue(new Callback<HttpResponse<ValidateCarDTO>>() {
                @Override
                public void onResponse(@NonNull Call<HttpResponse<ValidateCarDTO>> call, @NonNull Response<HttpResponse<ValidateCarDTO>> response) {
                    changeActivity(Objects.requireNonNull(response.body()).data);
                }

                @Override
                public void onFailure(@NonNull Call<HttpResponse<ValidateCarDTO>> call, @NonNull Throwable t) {
                    showToast();
                }
            });
        });
    }


    private void changeActivity(ValidateCarDTO argument) {
        Intent intent = new Intent(requireActivity(), ResultActivity.class);
        intent.putExtra("argument", argument);
        startActivity(intent);
    }

    private void showToast() {
        Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
    }
}
