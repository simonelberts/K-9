package com.fsck.k9.ui.messageview;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsck.k9.R;
import com.fsck.k9.view.MessageCryptoDisplayStatus;


public class CryptoInfoDialog extends DialogFragment {

    public static final String ARG_DISPLAY_STATUS = "display_status";

    public static CryptoInfoDialog newInstance(MessageCryptoDisplayStatus displayStatus) {
        CryptoInfoDialog frag = new CryptoInfoDialog();

        Bundle args = new Bundle();
        args.putString(ARG_DISPLAY_STATUS, displayStatus.toString());
        frag.setArguments(args);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder b = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.message_crypto_info_dialog, null);

        final View icon1 = view.findViewById(R.id.crypto_info_icon1);
        final View icon2 = view.findViewById(R.id.crypto_info_icon2);
        final TextView text1 = (TextView) view.findViewById(R.id.crypto_info_text1);
        final TextView text2 = (TextView) view.findViewById(R.id.crypto_info_text2);

        b.setView(view);
        b.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        text1.setAlpha(0.0f);
        text2.setAlpha(0.0f);

        view.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                float halfVerticalPixelDifference = (icon2.getY() - icon1.getY()) / 2.0f;
                icon1.setTranslationY(halfVerticalPixelDifference);
                icon2.setTranslationY(-halfVerticalPixelDifference);

                icon1.animate().translationY(0)
                        .setStartDelay(400)
                        .setDuration(350)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .start();
                icon2.animate().translationY(0)
                        .setStartDelay(400)
                        .setDuration(350)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .start();
                text1.animate().alpha(1.0f).setStartDelay(750).start();
                text2.animate().alpha(1.0f).setStartDelay(750).start();

                view.removeOnLayoutChangeListener(this);
            }
        });

        return b.create();
    }
}
