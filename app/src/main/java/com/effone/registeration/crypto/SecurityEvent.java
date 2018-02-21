package com.effone.registeration.crypto;

import android.content.Context;
import android.content.Intent;

/**
 * Created by sumanth.peddinti on 2/21/2018.
 */

public class SecurityEvent {
    public  static final String KEY_PERMISSION           = "org.thoughtcrime.securesms.ACCESS_SECRETS";
    public static final String SECURITY_UPDATE_EVENT = "org.thoughtcrime.securesms.KEY_EXCHANGE_UPDATE";

    public static void broadcastSecurityUpdateEvent(Context context) {
        Intent intent = new Intent(SECURITY_UPDATE_EVENT);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent, KEY_PERMISSION);
    }
}
