package com.fsck.k9.activity.compose;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.fsck.k9.R;
import com.fsck.k9.view.HighlightDialogFragment;


public class OpenPgpInlineDialog extends HighlightDialogFragment {
    public static final String ARG_FIRST_TIME = "first_time";


    public static OpenPgpInlineDialog newInstance(boolean firstTime, Integer showcaseView) {
        OpenPgpInlineDialog dialog = new OpenPgpInlineDialog();

        Bundle args = new Bundle();
        args.putInt(ARG_FIRST_TIME, firstTime ? 1 : 0);
        if (showcaseView != null) {
            args.putInt(ARG_HIGHLIGHT_VIEW, showcaseView);
        }
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(activity).inflate(R.layout.openpgp_inline_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle("Compatibility Mode");
        builder.setView(view);

        if (getArguments().getInt(ARG_FIRST_TIME) != 0) {
            builder.setPositiveButton("Got it!", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            builder.setPositiveButton("Disable", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Activity activity = getActivity();
                    if (activity == null) {
                        return;
                    }

                    ((OnOpenPgpInlineChangeListener) activity).onOpenPgpInlineChange(false);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Keep Enabled", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        return builder.create();
    }

    public interface OnOpenPgpInlineChangeListener {
        void onOpenPgpInlineChange(boolean enabled);
    }

}
