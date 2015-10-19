package com.adstoreapp.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.adstoreapp.stormy.R;

/**
 * Created by sunita on 10/15/2015.
 */
public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Oops! Sorry").setMessage(context.getString(R.string.Error_message)).setPositiveButton("Okay!", null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
