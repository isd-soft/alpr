package isd.alpr_mobile.main.utility;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.MainActivity;
import isd.alpr_mobile.main.model.Car;
import isd.alpr_mobile.main.model.Mail;
import isd.alpr_mobile.main.model.MailResponse;
import isd.alpr_mobile.main.retrofit.APIClient;
import isd.alpr_mobile.main.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Car> cars;

    public Adapter(Context context, List<Car> cars) {
        this.layoutInflater = LayoutInflater.from(context);
        this.cars = cars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_view, parent, false);
        return new ViewHolder(view);
    }

    private void confirmSendEmail(final View view, String licensePlate, final String email) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Do you really want to send an email to the driver of the car " + licensePlate + " ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendEmail(view, email);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void sendEmail(final View view, String email) {
        Call<MailResponse> call = APIClient.getClient().create(APIInterface.class).sendEmailNotification(new Mail(email));
        call.enqueue(new Callback<MailResponse>() {
            @Override
            public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
                showSnackbar(view);
                Log.i("ocr-cars", "Sent!");
            }
            @Override
            public void onFailure(Call<MailResponse> call, Throwable t) {
                Log.i("ocr-cars", "Fail");
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String licensePlate = cars.get(position).licensePlate;
        holder.textView.setText(licensePlate);
        holder.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i("ocr-cars", cars.get(position).ownerEmail);
//                Call<MailResponse> call = APIClient.getClient().create(APIInterface.class).sendEmailNotification(new Mail(cars.get(position).ownerEmail));
//                call.enqueue(new Callback<MailResponse>() {
//                    @Override
//                    public void onResponse(Call<MailResponse> call, Response<MailResponse> response) {
//                        showSnackbar(v);
//                        Log.i("ocr-cars", "Sent!");
//                    }
//                    @Override
//                    public void onFailure(Call<MailResponse> call, Throwable t) {
//                        Log.i("ocr-cars", "Fail");
//                    }
//                });
                confirmSendEmail(v, cars.get(position).licensePlate, cars.get(position).ownerEmail);
            }

        });

        holder.callBtn.setOnClickListener(v -> makePhoneCall(v, cars.get(position).ownerTelephone));
    }

    private void showSnackbar(View v) {
        Snackbar.make(v, "The email was sent!", Snackbar.LENGTH_LONG)
                .setAction("OK", v1 -> { })
                .show();
    }

    private void makePhoneCall(View view, String telephone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + telephone));
        view.getContext().startActivity(callIntent);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button callBtn, sendBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.license_plate);
            callBtn = itemView.findViewById(R.id.callBtn);
            sendBtn = itemView.findViewById(R.id.sendBtn);
        }
    }
}
