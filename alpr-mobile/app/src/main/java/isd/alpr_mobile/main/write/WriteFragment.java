package isd.alpr_mobile.main.write;

import android.content.Context;
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

import isd.alpr_mobile.R;


public class WriteFragment extends Fragment
        implements View.OnClickListener {

    private OnWriteFragmentInteractionListener mListener;
    private EditText plateInput;
    private Button checkBtn;
    private TextView statusTitle;
    private TextView status;
    private TextView ownerTitle;
    private TextView owner;
    private TextView companyTitle;
    private TextView company;
    private TextView brandTitle;
    private TextView brand;
    private TextView carModelTitle;
    private TextView carModel;
    private TextView carColorTitle;
    private TextView carColor;
    private ImageView logo;

    public WriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty_write, container, false);
        findViews(view);
        checkBtn.setOnClickListener(this);
        return view;
    }

    private void findViews(View view) {
        plateInput = view.findViewById(R.id.license_plate);
        checkBtn = view.findViewById(R.id.checkBtn);
        statusTitle = view.findViewById(R.id.statusTitle);
        status = view.findViewById(R.id.status);
        ownerTitle = view.findViewById(R.id.ownerTitle);
        owner = view.findViewById(R.id.owner);
        companyTitle = view.findViewById(R.id.companyTitle);
        company = view.findViewById(R.id.company);
        brandTitle = view.findViewById(R.id.brandTitle);
        brand = view.findViewById(R.id.brand);
        carModelTitle = view.findViewById(R.id.carModelTitle);
        carModel = view.findViewById(R.id.carModel);
        carColorTitle = view.findViewById(R.id.carColorTitle);
        carColor = view.findViewById(R.id.carColor);
        logo = view.findViewById(R.id.logo);
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
    }

    private void setDataVisible() {
        plateInput.setVisibility(View.VISIBLE);
        checkBtn.setVisibility(View.VISIBLE);
        statusTitle.setVisibility(View.VISIBLE);
        status.setVisibility(View.VISIBLE);
        ownerTitle.setVisibility(View.VISIBLE);
        owner.setVisibility(View.VISIBLE);
        companyTitle.setVisibility(View.VISIBLE);
        company.setVisibility(View.VISIBLE);
        brandTitle.setVisibility(View.VISIBLE);
        brand.setVisibility(View.VISIBLE);
        carModelTitle.setVisibility(View.VISIBLE);
        carModel.setVisibility(View.VISIBLE);
        carColorTitle.setVisibility(View.VISIBLE);
        carColor.setVisibility(View.VISIBLE);
        logo.setVisibility(View.INVISIBLE);
    }
}
