package isd.alpr_mobile.main.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isd.alpr_mobile.R;
import isd.alpr_mobile.main.model.Car;

public class Adapter extends ListAdapter<Car, Adapter.ViewHolder> {

    public Adapter() {
        super(new CarComparator());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bindItems(getItem(position));
    }

    private void makePhoneCall(View view, String telephone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + telephone));
        view.getContext().startActivity(callIntent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public void bindItems(Car car) {
            view.setId((int) car.id);
            ((TextView)view.findViewById(R.id.license_plate_in)).setText(car.licensePlate);
            view.findViewById(R.id.call_button).setOnClickListener(v -> makePhoneCall(v, car.ownerContact));
        }

    }

    static class CarComparator extends DiffUtil.ItemCallback<Car> {
        @Override
        public boolean areItemsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
            return oldItem.id == newItem.id;
        }
    }
}
