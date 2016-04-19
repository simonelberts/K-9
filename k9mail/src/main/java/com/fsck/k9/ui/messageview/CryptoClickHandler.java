package com.fsck.k9.ui.messageview;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.media.MediaCodec.CryptoInfo;
import android.util.Log;

import com.fsck.k9.K9;
import com.fsck.k9.mailstore.MessageViewInfo;
import com.fsck.k9.view.MessageCryptoDisplayStatus;


public class CryptoClickHandler {

    public static void handleCryptoClick(MessageViewInfo messageViewInfo, Activity activity) {
        MessageCryptoDisplayStatus displayStatus =
                MessageCryptoDisplayStatus.fromResultAnnotation(messageViewInfo.cryptoResultAnnotation);
        switch (displayStatus) {
            case DISABLED:
                displayNoCryptoDialog();
                break;
            case UNENCRYPTED_SIGN_UNKNOWN:
                launchPendingIntent(messageViewInfo, activity);
                break;
            default:
                displaySignatureInfoDialog(displayStatus, messageViewInfo, activity);
                break;
        }
    }

    private static void displaySignatureInfoDialog(MessageCryptoDisplayStatus displayStatus,
            MessageViewInfo messageViewInfo, Activity activity) {
        CryptoInfoDialog dialog = CryptoInfoDialog.newInstance(displayStatus);
        dialog.show(activity.getFragmentManager(), "crypto_info_dialog");
    }

    private static void launchPendingIntent(MessageViewInfo messageViewInfo, Activity activity) {
        try {
            PendingIntent pendingIntent = messageViewInfo.cryptoResultAnnotation.getOpenPgpPendingIntent();
            if (pendingIntent != null) {
                activity.startIntentSenderForResult(pendingIntent.getIntentSender(), 0, null, 0, 0, 0);
            }
        } catch (IntentSender.SendIntentException e) {
            Log.e(K9.LOG_TAG, "SendIntentException", e);
        }
    }

    private static void displayNoCryptoDialog() {

    }
}
