package com.detroitlabs.kyleofori.teachertools.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.detroitlabs.kyleofori.teachertools.R;

/**
 * Created by kyleofori on 12/11/14.
 */
public class ClearFavoritesDialogFragment extends DialogFragment {

    public ClearFavoritesDialogFragment() {
    }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dialog_clear_favorites_title)
                    .setMessage(R.string.dialog_clear_favorites_message)
                    .setPositiveButton(R.string.clear, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //let Favorites Fragment know that we're good to clear
                                dialog.dismiss();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.cancel();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
