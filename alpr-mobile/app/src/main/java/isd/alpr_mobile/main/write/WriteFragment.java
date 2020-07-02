package isd.alpr_mobile.main.write;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Collections;

import isd.alpr_mobile.DisplayMessageActivity;
import isd.alpr_mobile.R;
import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicenseValidationResponse;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WriteFragment extends Fragment
        implements View.OnClickListener {

    private OnWriteFragmentInteractionListener mListener;
    private EditText plateInput;
    private Button checkBtn;
    private Retrofit retrofit;
    private APIInterface api;

    public WriteFragment() {
        // Required empty public constructor
        retrofit = APIClient.getClient();
        api = retrofit.create(APIInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        findViews(view);
        checkBtn.setOnClickListener(this);
        return view;
    }

    private void findViews(View view) {
        plateInput = view.findViewById(R.id.license_plate);
        checkBtn = view.findViewById(R.id.checkBtn);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnWriteFragmentInteractionListener) {
            mListener = (OnWriteFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnWriteFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        setDataVisible();
        String licensePlate = plateInput.getText().toString();
        Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
        intent.putExtra("plates", Collections.singletonList(new LicensePlate(licensePlate)).toArray());
        startActivity(intent);
    }

    private void setDataVisible() {
        plateInput.setVisibility(View.VISIBLE);
        checkBtn.setVisibility(View.VISIBLE);
    }
}
