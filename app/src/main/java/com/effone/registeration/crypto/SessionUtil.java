package com.effone.registeration.crypto;

import android.content.Context;

import com.effone.registeration.crypto.storage.TextSecureSessionStore;

/**
 * Created by sumanth.peddinti on 2/21/2018.
 */

public class SessionUtil {
    public static void archiveAllSessions(Context context) {
        new TextSecureSessionStore(context).archiveAllSessions();
    }
}
