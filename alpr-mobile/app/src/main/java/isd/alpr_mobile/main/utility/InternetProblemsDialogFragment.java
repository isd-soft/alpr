package isd.alpr_mobile.main.utility;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import isd.alpr_mobile.R;
import isd.alpr_mobile.main.MainActivity;

public class InternetProblemsDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setCancelable(false);
        ProgressDialog.Builder progressBuilder = new ProgressDialog.Builder(getActivity());


        builder.setMessage(R.string.dialog_connection_lost)
                .setPositiveButton(R.string.retry, (dialog, id) -> {

                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                });
        return builder.create();
    }

    @Override
    public void onStart(){
        super.onStart();
        AlertDialog d = (AlertDialog)getDialog();
        if(d != null){
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                Boolean wantToCloseDialog = false;
                if(wantToCloseDialog)
                    dismiss();
            });
        }
    }
}


